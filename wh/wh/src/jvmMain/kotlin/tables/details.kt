package tables

import DBField
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import input.input
import tableResult


@Composable
fun detail(rowIndex: MutableState<Int>) {

//    val diameter = tableResult[id.value]

//    val diameterIndex = DBField.list.indexOfFirst { it.second.lowercase() == "диаметр" }
    val numberIndex = DBField.list.indexOfFirst { it.second.lowercase() == "номер" }
    val potrIndex = DBField.list.indexOfFirst { it.second.lowercase() == "потребитель" }

    if (rowIndex.value >= 0) {
        Window(onCloseRequest = { rowIndex.value = -1 }) {

            Column {
                println(rowIndex.value)
                if (rowIndex.value >= 0) {
                    inputValue(rowIndex.value, DBField.list.indexOfFirst { it.second.lowercase() == "диаметр" })
                    inputValue(rowIndex.value, DBField.list.indexOfFirst { it.second.lowercase() == "номер" })
                    inputValue(rowIndex.value, DBField.list.indexOfFirst { it.second.lowercase() == "гост" })
                }
            }
        }
    }
}

@Composable
private fun inputValue(rowIndex: Int, labelIndex: Int) {
    val text = remember { mutableStateOf(tableResult[rowIndex][labelIndex]) }
    Row(horizontalArrangement = Arrangement.Center) {
        Text(text = DBField.list[labelIndex].second)
        input(text)
    }
}