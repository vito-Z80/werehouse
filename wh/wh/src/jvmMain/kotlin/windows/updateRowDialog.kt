@file:OptIn(ExperimentalComposeUiApi::class)

package windows

import AMOUNT
import DB
import DBField
import DETAILS
import DIAMETER
import NUMBER
import STANDARD
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import divider
import gson.G
import gson.GSegment
import gson.Seg
import input.inputName
import selectedDB
import selectedDBTable
import stringToFloat
import tableResult
import verticalDivider


@Composable
fun updateRowDialog(rowIndex: MutableState<Int>) {

    if (rowIndex.value >= 0) {
        Dialog(
            onKeyEvent = {
                if (it.key == Key.Escape) {
                    rowIndex.value = -1
                }
                false   // if FASLE > TAB key working, else: TAB key not working
            },
            onCloseRequest = { rowIndex.value = -1 },
            resizable = false,
            undecorated = true,
        ) {
            Column(
                modifier = Modifier.border(width = 1f.dp, color = Color.Black).fillMaxWidth().fillMaxHeight()
                    .padding(4f.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                dialogTitle()
                Spacer(modifier = Modifier)
                mutableContent(rowIndex)
                Spacer(modifier = Modifier)
//                confirmContent(rowIndex)
            }
        }
    } else {
        Edit.reset()
    }
}


@Composable
private fun mutableContent(rowIndex: MutableState<Int>) {


    val confirmEnabling = remember { mutableStateOf(false) }
    var saving by remember { mutableStateOf(false) }
    val detailIndex = DBField.list.indexOfFirst { it.second.lowercase() == DETAILS.lowercase() }
    val tableDetail = remember { mutableStateOf(tableResult[rowIndex.value][detailIndex]) }
    var segments = G.gson.fromJson(tableDetail.value, Seg::class.java)
    val segmentValue = remember { mutableStateOf("0") }


    // TODO не сохраняет gson в таблицу типом String. 1 раз за жизненный цикл сохраняет но в какой момент хз

    LaunchedEffect(saving) {
        if (saving) {
            saving = false
            if (segments == null) {
                segments = Seg()
            }
            val s = GSegment(
                segmentValue.value.stringToFloat().second,
                "12/10/22", "Google", "ad ejejl ssdas,m ds"
            )
            segments.segments.add(s)
            println("add: ${segments.segments.joinToString()}")
            val result = G.gson.toJson(segments)
            DB.updateById(
                selectedDB, selectedDBTable, rowIndex.value,
                listOf(DETAILS),
                listOf("'$result'")
            )
            rowIndex.value = -1
        }
    }


    println("get: ${segments?.segments?.joinToString()}")

    val diameterIndex = DBField.list.indexOfFirst { it.second.lowercase() == DIAMETER.lowercase() }
    val diameter = remember { mutableStateOf(tableResult[rowIndex.value][diameterIndex]) }

    val numberIndex = DBField.list.indexOfFirst { it.second.lowercase() == NUMBER.lowercase() }
    val number = remember { mutableStateOf(tableResult[rowIndex.value][numberIndex]) }

    val standardIndex = DBField.list.indexOfFirst { it.second.lowercase() == STANDARD.lowercase() }
    val standard = remember { mutableStateOf(tableResult[rowIndex.value][standardIndex]) }

    val amountIndex = DBField.list.indexOfFirst { it.second.lowercase() == AMOUNT.lowercase() }
    val amount = remember { mutableStateOf(tableResult[rowIndex.value][amountIndex]) }


    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = DIAMETER)
        inputName(text = diameter, { diameter.value.stringToFloat().first })
    }


    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = NUMBER)
        inputName(text = number, { true })
    }


    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = STANDARD)
        inputName(text = standard, { true })
    }


    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = AMOUNT)
        inputName(text = amount, { amount.value.stringToFloat().first })
    }

    divider(0.75f, Color.Black)

    segmentTable(segments)

    divider(0.75f, Color.Black)


    Row(modifier = Modifier.fillMaxWidth().padding(4f.dp), horizontalArrangement = Arrangement.SpaceAround) {
        Text(text = "Отрезок:")
        inputName(text = segmentValue, { segmentValue.value.stringToFloat().first })
    }

    confirmEnabling.value =
        segmentValue.value.stringToFloat().first && segmentValue.value.stringToFloat().second > 0f &&
                amount.value.stringToFloat().first && amount.value.stringToFloat().second > 0f &&
                diameter.value.stringToFloat().first && diameter.value.stringToFloat().second > 0f


    divider(width = 0.75f, color = Color.Black)

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

        TextButton(onClick = {
            rowIndex.value = -1
        }) {
            Text(text = "Отмена")
        }
        TextButton(
            enabled = confirmEnabling.value,
            onClick = {
                saving = true
            }) {
            Text(text = "Подтвердить")
        }
    }

}

@Composable
fun segmentTable(segments: Seg?) {
    if (segments != null) {

        LazyColumn {

            items(items = segments.segments) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    Text(text = "${it.segment}")
                    verticalDivider()
                    Text(text = it.date)
                    verticalDivider()
                    Text(text = it.consumer)
                    verticalDivider()
                    Text(text = it.details)
                }
            }

        }


    }
}


@Composable
private fun dialogTitle() {
    Row {
        Text(text = "Редактирование")
    }
    Divider(modifier = Modifier.fillMaxWidth(0.75f).padding(8f.dp), color = Color.DarkGray)
}
