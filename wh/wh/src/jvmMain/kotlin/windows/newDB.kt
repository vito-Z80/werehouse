package windows

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import input.input
import windowConfirm

@Composable
fun newDBWindow(title: String = "", show: MutableState<Boolean>) {
    val dbName = remember { mutableStateOf("") }
    if (show.value) {
        Dialog(onCloseRequest = { show.value = false }, title = title) {

            Column(
                modifier = Modifier.fillMaxHeight().padding(8f.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Введите имя новой базы данных.")
                Spacer(modifier = Modifier.height(20f.dp))
                input(dbName)
                Spacer(modifier = Modifier.height(20f.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 0f.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    TextButton(onClick = {
                        show.value = false
                        dbName.value = ""
                    }) {
                        Text(text = "Cancel")
                    }
                    TextButton(onClick = {
                        if (dbName.value.isNotEmpty()) {
                            show.value = false
                            DB.create(dbName.value, closed = true)
                            dbName.value = ""
                        }
                    }) {
                        Text(text = "OK")
                    }
                }
            }

        }
    }
}