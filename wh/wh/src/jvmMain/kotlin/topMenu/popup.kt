package topMenu

import DB
import DBField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import selectedDB
import selectedDBTable
import windows.createDatabaseDialog
import windows.createTableDialog
import java.io.File


@Composable
fun testPopup(expand: MutableState<Boolean>) {

    val showDBNamesState = remember { mutableStateOf(false) }
    val showFieldsState = remember { mutableStateOf(false) }
    var dbNames by remember { mutableStateOf(listOf<String>()) }

    LaunchedEffect(showDBNamesState.value) {
        if (showDBNamesState.value) {
            val path = File("").absolutePath
            val fileNames = File(path).listFiles { _, name ->
                name?.contains(".db") == true
            }?.map { it.name.replace(".db", "") }
            dbNames = fileNames ?: listOf()
            println(dbNames.joinToString())
        }
    }

    DropdownMenu(
        expanded = expand.value,
        onDismissRequest = { expand.value = false }
    ) {

        DropdownMenuItem(onClick = { showDBNamesState.value = true }) {
            Text("Базы данных")
            showDBNames(showDBNamesState, dbNames)
        }
        DropdownMenuItem(onClick = { showFieldsState.value = true }) {
            Text("Поля")
            showFieldsSelection(showFieldsState)
        }
    }
}

@Composable
private fun showFieldsSelection(showFieldsState: MutableState<Boolean>) {

    DropdownMenu(
        expanded = showFieldsState.value,
        onDismissRequest = {
            showFieldsState.value = false
        }
    ) {
        DBField.requestFields.forEach { field ->
            DropdownMenuItem(onClick = {
                field.second.value = !field.second.value
            }) {
                Checkbox(checked = field.second.value, onCheckedChange = {
                    field.second.value = !field.second.value
                })
                Text(text = field.third)
            }
        }
    }
}


@Composable
private fun showDBNames(
    showDBNamesState: MutableState<Boolean>,
    dbNames: List<String>,
) {

    val newDBDialog = remember { mutableStateOf(false) }
    DropdownMenu(
        expanded = showDBNamesState.value,
        onDismissRequest = {
            showDBNamesState.value = false
        }, modifier = Modifier
    ) {
        dbNames.forEach { dbN ->
            val showTableNamesState = remember { mutableStateOf(false) }
            DropdownMenuItem(onClick = {
                showTableNamesState.value = true
            }) {
                Text(dbN)
                showTableNames(dbN, showTableNamesState)

            }
        }
        Divider()
        DropdownMenuItem(onClick = {
            showDBNamesState.value = false
            newDBDialog.value = true
        }) {
            Text(text = "Создать БД")
        }
    }
    createDatabaseDialog(newDBDialog)
}

@Composable
private fun showTableNames(
    dbName: String,
    showTableNamesState: MutableState<Boolean>,
) {

    var dbTables by remember { mutableStateOf(listOf<String>()) }
    val newTableNameDialog = remember { mutableStateOf(false) }

    LaunchedEffect(showTableNamesState.value) {
        if (showTableNamesState.value) {
            dbTables = DB.tableNames(dbName)
            println("TABLES: ${dbTables.joinToString()}")
        }
    }

    DropdownMenu(
        expanded = showTableNamesState.value,
        onDismissRequest = { showTableNamesState.value = false }, modifier = Modifier
    ) {
        dbTables.forEach { tableName ->
            DropdownMenuItem(onClick = {
                showTableNamesState.value = false
                selectedDB = dbName
                selectedDBTable = tableName
            }) {
                Text(tableName)
            }
        }
        Divider()
        DropdownMenuItem(onClick = {
            showTableNamesState.value = false
            newTableNameDialog.value = true
        }) {
            Text(text = "Создать таблицу")

        }
    }
    createTableDialog(dbName, newTableNameDialog)
}
