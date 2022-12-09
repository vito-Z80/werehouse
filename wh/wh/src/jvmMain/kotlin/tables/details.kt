package tables

import DBField
import DIAMETER
import GOST
import NUMBER
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
                    inputValue(
                        rowIndex.value,
                        DBField.list.indexOfFirst { it.second.lowercase() == DIAMETER.lowercase() },
                        Float.MAX_VALUE
                    )
                    inputValue(
                        rowIndex.value,
                        DBField.list.indexOfFirst { it.second.lowercase() == NUMBER.lowercase() },
                        String.toString()
                    )
                    inputValue(
                        rowIndex.value,
                        DBField.list.indexOfFirst { it.second.lowercase() == GOST.lowercase() },
                        String.toString()
                    )
                }
            }
        }
    }
}




@Composable
private inline fun <reified T> inputValue(rowIndex: Int, labelIndex: Int, t: T) {

    println("------------------------")
    println(t)
    when (t) {
        is Int -> {
            println("INT: $t")
        }

        is String -> {
            println("STRING: $t")
        }

        is Float -> {
            println("FLOAT: $t")
        }
    }

    val text = remember { mutableStateOf(tableResult[rowIndex][labelIndex]) }
    Row(horizontalArrangement = Arrangement.Center) {
        Text(text = DBField.list[labelIndex].second)
        input(text)
    }
}