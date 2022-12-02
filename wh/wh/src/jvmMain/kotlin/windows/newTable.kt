package windows

import DB
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import input.input
import selectedDB

@Composable
fun newTableDBWindow(dbName: String, title: String = "", show: MutableState<Boolean>) {

    var isCreated by remember { mutableStateOf(false) }


    val tableName = remember { mutableStateOf("") }
    LaunchedEffect(isCreated) {
        if (isCreated) {
            isCreated = false
            DB.createTable(selectedDB, tableName.value)
        }
    }

    if (show.value) {
        Dialog(onCloseRequest = { show.value = false }, title = title) {

            Column(
                modifier = Modifier.fillMaxHeight().padding(8f.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Введите имя новой таблицы существующей базы данных ${dbName}.")
                Spacer(modifier = Modifier.height(20f.dp))
                input(tableName)
                Spacer(modifier = Modifier.height(20f.dp))
                Row(modifier = Modifier.padding(bottom = 0f.dp), horizontalArrangement = Arrangement.SpaceEvenly) {

                    TextButton(onClick = { show.value = false }) {
                        Text(text = "Cancel")
                    }
                    TextButton(onClick = {
                        show.value = false
                        isCreated = true
                    }) {
                        Text(text = "OK")
                    }
                }
            }
        }
    }
}