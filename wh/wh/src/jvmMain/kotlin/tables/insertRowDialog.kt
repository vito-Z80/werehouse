package tables

import DBField
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import input.input
import insertRowDialogState
import selectedDB
import selectedDBTable

@Composable
fun insertRowDialog(tableName: String) {

//    insertContent = HashMap<String, String>().let {
//        DBField.list.forEach {field->
//            it[field.second] = ""
//        }
//        it
//    }
    if (insertRowDialogState) {
        Window(
            onCloseRequest = { insertRowDialogState = false },
            resizable = false,
            state = WindowState(width = 512f.dp, height = 300f.dp),
            title = "Add row.",
        ) {
            Column(modifier = Modifier.padding(4f.dp)) {
                insertRowContent()
                Divider()
                confirm()
            }
        }
    }
}

@Composable
private fun insertRowContent() {
    Column(verticalArrangement = Arrangement.Center) {
        var c = 1
        val count = { c++ }
        repeat(3) {
            Row {
                insertRowInput(DBField.list[count()].second, 0)
                insertRowInput(DBField.list[count()].second, 0)
                insertRowInput(DBField.list[count()].second, 0)
            }
        }
    }
}

@Composable
private fun insertRowInput(labelName: String, format: Int) {


    val valueId = DBField.list.indexOfFirst { it.second == labelName }
    val value by remember { mutableStateOf(DBField.values[valueId]) }
    println(value)

    Column(
        modifier = Modifier.padding(4f.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = labelName, modifier = Modifier.padding(start = 4f.dp))
        input(value)
    }

}

@Composable
private fun confirm() {
    Column {

        Row(horizontalArrangement = Arrangement.SpaceAround) {

            TextButton(onClick = {
                insertRowDialogState = false
            }) {
                Text(text = "Cancel")
            }
            TextButton(onClick = {
                insertRowDialogState = false
//                DB.insertTo(selectedDB,selectedDBTable, DBField.values)
                DB.insertDefaultValues(selectedDB, selectedDBTable)
                DB.selectFields(selectedDB, selectedDBTable)
            }) {
                Text(text = "Add")
            }
        }

    }
}