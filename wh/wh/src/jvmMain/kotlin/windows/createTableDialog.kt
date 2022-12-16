@file:OptIn(ExperimentalComposeUiApi::class)

package windows

import DB
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import divider
import input.inputName
import input.regEngDigOnly
import selectedDB


private val newTableName = mutableStateOf("")

@Composable
fun createTableDialog(dbname: String, isVisible: MutableState<Boolean>) {


    if (isVisible.value) {
        selectedDB = dbname
        Dialog(
            onKeyEvent = {
                isVisible.value = it.key != Key.Escape
                false   // if FASLE > TAB key working, else: TAB key not working
            },
            onCloseRequest = { isVisible.value = false },
            resizable = false,
//            state = WindowState(
//                width = 512f.dp,
//                height = 256.dp,
//                position = WindowPosition(alignment = Alignment.Center)
//            ),
            undecorated = true,
        ) {
            Column(
                modifier = Modifier.border(width = 1f.dp, color = Color.Black).fillMaxWidth().fillMaxHeight()
                    .padding(8f.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                title()
                divider(0.75f)
                content()
                divider(0.95f)
                confirm(isVisible)
            }
        }
    } else {
        newTableName.value = ""
    }
}

@Composable
private fun title() {
    Row {
        Text(text = "Создать новую таблицу для БД `$selectedDB`")
    }

}

@Composable
private fun content() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Имя новой таблицы")
        inputName(newTableName, { !newTableName.value.contains(regEngDigOnly) })
    }

}

@Composable
private fun confirm(isVisible: MutableState<Boolean>) {

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        TextButton(onClick = { isVisible.value = false }) {
            Text(text = "Отмена")
        }
        TextButton(enabled = !newTableName.value.contains(regEngDigOnly) || newTableName.value.isNotEmpty(), onClick = {
            DB.createTable(selectedDB, newTableName.value)
            isVisible.value = false
        }) {
            Text(text = "Создать")
        }
    }

}
