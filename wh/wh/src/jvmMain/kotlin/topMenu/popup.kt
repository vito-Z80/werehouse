package topMenu

import DB
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import selectedDBTable
import windowConfirm

@Composable
fun productPopup(expand: MutableState<Boolean>) {

    DropdownMenu(
        expanded = expand.value,
        onDismissRequest = { expand.value = false }
    ) {
        DB.showTables().forEach {
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

    val wcf = remember { mutableStateOf(false) }

    DropdownMenu(
        expanded = expand.value,
        onDismissRequest = { expand.value = false }
    ) {
        DropdownMenuItem(onClick = {
            expand.value = false
            wcf.value = true
        }) {
            Text(text = "Создать Базу Даных")

            windowConfirm(title = "Fine", text = "База данных успешно создана.", wcf)

        }
        DropdownMenuItem(onClick = {
            expand.value = false
        }) {
            Text(text = "Создать Таблицу")
        }
        DropdownMenuItem(onClick = {
            expand.value = false
        }) {
            Text(text = "Добавить поле")
        }
    }

}