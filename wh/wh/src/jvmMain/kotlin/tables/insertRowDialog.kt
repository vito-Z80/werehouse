@file:OptIn(ExperimentalComposeUiApi::class)

package tables

import AMOUNT
import DB
import DBField
import DIAMETER
import NUMBER
import STANDARD
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import input.input
import insertRowDialogState
import kotlinx.coroutines.*
import selectedDB
import selectedDBTable
import stringToFloat


private val confirmButtonEnabled = mutableStateOf(false)

val insertFields = listOf(DIAMETER, NUMBER, STANDARD, AMOUNT)

object Edit {
    val diameter = mutableStateOf("")
    val number = mutableStateOf("")
    val standard = mutableStateOf("")
    val amount = mutableStateOf("")

    fun reset() {
        diameter.value = ""
        amount.value = ""
        number.value = ""
        standard.value = ""
    }

    fun isTypeCorrect() =
        diameter.value.stringToFloat().first &&
                amount.value.stringToFloat().first &&
                number.value.isNotEmpty() &&
                standard.value.isNotEmpty()

}

@Composable
fun insertRowDialog() {


    LaunchedEffect(Edit.isTypeCorrect()) {
        confirmButtonEnabled.value = Edit.isTypeCorrect()
    }



    if (insertRowDialogState) {
        Window(
            onKeyEvent = {
                insertRowDialogState = it.key != Key.Escape
                false   // if FASLE > TAB key working, else: TAB key not working
            },
            onCloseRequest = { insertRowDialogState = false },
            resizable = false,
            state = WindowState(
                width = 512f.dp,
                height = 256.dp,
                position = WindowPosition(alignment = Alignment.Center)
            ),
            undecorated = true,
        ) {
            Column(
                modifier = Modifier.border(width = 1f.dp, color = Color.Black).fillMaxHeight().padding(4f.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                dialogTitle()
                Spacer(modifier = Modifier)
                insertRowContent()
                Spacer(modifier = Modifier)
                confirmContent()
            }
        }
    } else {
        Edit.reset()
        println("CLOSE")
    }
}

@Composable
private fun dialogTitle() {
    Row {
        Text(text = "Добавить запись")
    }
    Divider(modifier = Modifier.fillMaxWidth(0.75f).padding(8f.dp), color = Color.DarkGray)
}

@Composable
private fun insertRowContent() {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            insertRowInput(DIAMETER, KeyboardType.Number, Edit.diameter)
            insertRowInput(NUMBER, KeyboardType.Text, Edit.number)
        }
        Row {
            insertRowInput(STANDARD, KeyboardType.Text, Edit.standard)
            insertRowInput(AMOUNT, KeyboardType.Number, Edit.amount)
        }
        Divider(modifier = Modifier.fillMaxWidth(0.95f).padding(8f.dp), color = Color.DarkGray)
    }
}

@Composable
private fun insertRowInput(labelName: String, format: KeyboardType, textValue: MutableState<String>) {


    val ko = KeyboardOptions(keyboardType = format)

//    val valueId = DBField.list.indexOfFirst { it.second == labelName }
//    val value by remember { mutableStateOf(DBField.values[valueId]) }

    Column(
        modifier = Modifier.padding(4f.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = labelName, modifier = Modifier.padding(start = 4f.dp))
        input(textValue, ko)
    }

}

@Composable
private fun confirmContent() {

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

        TextButton(onClick = {
            insertRowDialogState = false
        }) {
            Text(text = "Cancel")
        }
        TextButton(enabled = confirmButtonEnabled.value, onClick = {
            insertRowDialogState = false
            DB.insert(selectedDB, selectedDBTable)
//                DB.insertTo(selectedDB,selectedDBTable, DBField.values)
//            DB.insertDefaultValues(selectedDB, selectedDBTable)
            DB.selectFields(selectedDB, selectedDBTable)
        }) {
            Text(text = "Add")
        }
    }
}