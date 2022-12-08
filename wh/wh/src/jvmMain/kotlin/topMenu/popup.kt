package topMenu

import DB
import DBField
import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import selectedDB
import selectedDBTable
import windows.createDbWindow
import windows.newDBWindow
import windows.newTableDBWindow
import java.io.File

@Composable
fun productPopup(expand: MutableState<Boolean>) {

    DropdownMenu(
        expanded = expand.value,
        onDismissRequest = { expand.value = false }
    ) {
        DB.showTables(selectedDB).forEach {
            DropdownMenuItem(onClick = {
                expand.value = false
                selectedDBTable = it
            }) {
                Text(text = it)
            }
        }
    }
}

@Composable
fun dbPopup(expand: MutableState<Boolean>) {

    val dbState = remember { mutableStateOf(false) }
    val tableState = remember { mutableStateOf(false) }

    DropdownMenu(
        expanded = expand.value,
        onDismissRequest = { expand.value = false }
    ) {
        DropdownMenuItem(onClick = {
            expand.value = false
            dbState.value = true
        }) {
            Text(text = "Создать Базу Даных")
        }
        DropdownMenuItem(onClick = {
            expand.value = false
            tableState.value = true
        }) {
            Text(text = "Создать Таблицу")
        }
        DropdownMenuItem(onClick = {
            expand.value = false
        }) {
            Text(text = "Добавить поле")
        }
    }

    newDBWindow("Create new DB", dbState)
    newTableDBWindow(selectedDB, "Create table", tableState)
//    windowConfirm(title = "Fine", text = "База данных успешно создана.", wcf)

}


@Composable
fun testPopup(expand: MutableState<Boolean>) {

//    var it1 by remember { mutableStateOf(false) }
    val showDBNamesState = remember { mutableStateOf(false) }
    val showFieldsState = remember { mutableStateOf(false) }
//    val showTableNamesState = remember { mutableStateOf(false) }

    var position = remember { Pair(0f, 0f) }

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
//    val selectedDBName = remember { mutableStateOf("") }

    val newTable = remember { mutableStateOf(false) }

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
//                selectedDBName.value = dbN
            }) {
                Text(dbN)
                showTableNames(dbN, showTableNamesState)

            }
        }
        Divider()
        DropdownMenuItem(onClick = {
            newTable.value = true
        }) {
            Text(text = "Создать БД")
            createDbWindow(newTable)
        }
    }
}

@Composable
private fun showTableNames(
    dbName: String,
//    selectedDBName: MutableState<String>,
    showTableNamesState: MutableState<Boolean>,
) {

    var dbTables by remember { mutableStateOf(listOf<String>()) }
//    var showTableNamesState by remember { mutableStateOf(false) }

    LaunchedEffect(showTableNamesState.value) {
        if (showTableNamesState.value) {
            dbTables = DB.showTables(dbName)
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
                selectedDBTable = tableName
            }) {
                Text(tableName)
            }
        }
        Divider()
        DropdownMenuItem(onClick = {
            showTableNamesState.value = false
        }) {
            Text(text = "Добавить таблицу")

        }
    }


}
