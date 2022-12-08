package windows

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import input.input
import kotlin.jvm.internal.Intrinsics.Kotlin
import kotlin.reflect.KType
import kotlin.reflect.typeOf

@Composable
fun createDbWindow(visible: MutableState<Boolean>) {

    if (visible.value) {
        Window(onCloseRequest = { visible.value = false }) {

            newField()

        }
    }

}

@Composable
private fun newField() {

    val fieldName = remember { mutableStateOf("") }
    val fieldType = remember { mutableStateOf(typeOf<Any>()) }
    val fieldTypeVisible = remember { mutableStateOf(false) }

    Row() {
        Column {
            Text(text = "Имя")
            input(fieldName)
        }
        Column {
            Text(text = "Тип", modifier = Modifier.clickable { fieldTypeVisible.value = true })
            fieldType(fieldType, fieldTypeVisible)
        }
        Column {
            Text(text = "Изменяемость")
            Checkbox(checked = false, onCheckedChange = null)
        }
    }
}

@Composable
private fun fieldType(fieldType: MutableState<KType>, fieldTypeVisible: MutableState<Boolean>) {


    typeOf<FieldType>().arguments.forEach {
        println(it.type)
    }

    DropdownMenu(expanded = fieldTypeVisible.value, onDismissRequest = {
        fieldTypeVisible.value = false
    }) {

        DropdownMenuItem(onClick = {
            println(FieldType.INT.second)
        }) {
            Text(text = "${FieldType.INT.first} : ")
            // TODO сменить версию котллина если не будет работать !!!!
        }

        DropdownMenuItem(onClick = {
            println(FieldType.FLOAT.second)
        }) {
            Text(text = FieldType.FLOAT.first)
        }

        DropdownMenuItem(onClick = {
            println(FieldType.STRING.second)
        }) {
            Text(text = FieldType.STRING.first)
        }

    }

}


class FieldType {
    companion object {
        val INT = Pair("Integer", typeOf<Int>())
        val FLOAT = Pair("Float", typeOf<Float>())
        val STRING = Pair("String", typeOf<String>())
    }
}
