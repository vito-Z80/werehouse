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
import input.inputName
import kotlinx.coroutines.withContext
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
    var isTableUpdate by remember { mutableStateOf(false) }
    val detailIndex = DBField.list.indexOfFirst { it.second.lowercase() == DETAILS.lowercase() }
    val tableDetail = remember { mutableStateOf(tableResult[rowIndex.value][detailIndex]) }
    var segments: MutableState<Seg?> = remember { mutableStateOf(G.gson.fromJson(tableDetail.value, Seg::class.java)) }
    val segmentValue = remember { mutableStateOf("0") }


    // TODO не сохраняет gson в таблицу типом String. ваще нихуя не сохраняет, но ексепшена нет, че за нах ????
//    G.gson.fromJson(tableDetail.value, Seg::class.java)
    LaunchedEffect(isTableUpdate) {
        if (isTableUpdate) {
            isTableUpdate = withContext(this.coroutineContext) {
                val detailsIndex = DBField.list.indexOfFirst { it.second == DETAILS }
                var segmentList =
                    G.gson.fromJson(tableResult[rowIndex.value][detailsIndex], Seg::class.java)?.segments ?: listOf()
                val s = Seg.Segment(
                    "12/10/22", "Google", "ad ejejl ssdas,m ds", segmentValue.value.stringToFloat().second
                )
                segmentList = segmentList.plus(s)
                val newSeg = Seg(segmentList)
                val result = G.gson.toJson(newSeg)
                println("UPDATE RESULT: $result")
                tableResult[rowIndex.value][detailsIndex] = result
                DB.updateDetails(selectedDB, selectedDBTable, result, rowIndex.value)
//                DB.updateById(
//                    selectedDB, selectedDBTable, rowIndex.value,
//                    listOf(DETAILS),
//                    listOf(result.trimMargin())
//                )
                rowIndex.value = -1
                segments.value = newSeg
                false
            }
        }
    }


    println("get: ${segments.value?.segments?.joinToString()}")

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
                isTableUpdate = true
            }) {
            Text(text = "Подтвердить")
        }
    }

}

@Composable
fun segmentTable(segments: MutableState<Seg?>) {

    LazyColumn {

        items(items = segments.value?.segments ?: listOf()) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Text(text = "${it?.segment}")
                verticalDivider()
                Text(text = it?.date.toString())
                verticalDivider()
                Text(text = it?.consumer.toString())
                verticalDivider()
                Text(text = it?.details.toString())
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


data class Seg(
    var segments: List<Segment?>,
) {
    data class Segment(
        var consumer: String?, // Google
        var date: String?, // 12/10/22
        var details: String?, // ad ejejl ssdas,m ds
        var segment: Float?, // 230.0
    )
}