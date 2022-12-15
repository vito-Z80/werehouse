package tables

import CONSUMER
import DBField
import DETAILS
import DIAMETER
import STANDARD
import NUMBER
import SEGMENT
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import gson.G
import gson.GSegment
import gson.Seg
import input.input
import input.inputSegmentConfirm
import tableResult
import verticalDivider


@Composable
fun detail(rowIndex: MutableState<Int>) {

    val detailsGson = remember { mutableStateOf("") }
    val segments: MutableState<Seg?> = remember { mutableStateOf(null) }
    val isSegmentAdded = remember { mutableStateOf(false) }
    val segmentValue = remember { mutableStateOf("0") }

    LaunchedEffect(rowIndex.value) {
        if (rowIndex.value >= 0) {
            val detailsIndex = DBField.list.indexOfFirst { it.second.lowercase() == DETAILS.lowercase() }
            val data = tableResult[rowIndex.value][detailsIndex]
            if (data.isEmpty()) {
                segments.value = Seg()
            } else {
                segments.value = G.gson.fromJson(data,Seg::class.java)
            }
            detailsGson.value = G.gson.toJson(segments)
            println("details Gson data: ${detailsGson.value}")
        } else {
            detailsGson.value = ""
        }
    }

    LaunchedEffect(isSegmentAdded.value) {
        if (isSegmentAdded.value && rowIndex.value >= 0 && segments.value != null) {
            isSegmentAdded.value = false
            val s = GSegment(
                segment = segmentValue.value.toFloat(),
                date = "02/02/22",
                consumer = "huy s gory",
                details = "всякая чушь"
            )
            segments.value?.segments?.add(s)
            val detailsIndex = DBField.list.indexOfFirst { it.second.lowercase() == DETAILS.lowercase() }
            val gsonResult = G.gson.toJson(segments.value)
            tableResult[rowIndex.value][detailsIndex] = gsonResult

            println("need update db for:\n${gsonResult}")
            // TODO UPDATE IN DB
        }
    }


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
                        DBField.list.indexOfFirst { it.second.lowercase() == STANDARD.lowercase() },
                        String.toString()
                    )
                }
                Divider()
                segmentsTable(rowIndex, isSegmentAdded,segmentValue)
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


@Composable
private fun segmentsTable(
    rowIndex: MutableState<Int>,
    isSegmentAdded: MutableState<Boolean>,
    segmentValue: MutableState<String>
) {
    val scrollState by remember { mutableStateOf(ScrollState(0)) }
    Column(modifier = Modifier.padding(8f.dp)) {
        segmentNames()
        Divider()
        inputSegment(segmentValue, rowIndex, isSegmentAdded)
    }
    Column(modifier = Modifier.horizontalScroll(scrollState)) {


    }


}

@Composable
private fun inputSegment(
    segmentValue: MutableState<String>,
    rowIndex: MutableState<Int>,
    isSegmentAdded: MutableState<Boolean>,
) {

    Row(modifier = Modifier.fillMaxWidth().padding(end = 0f.dp)) {
        Text(text = "${SEGMENT}:", modifier = Modifier.padding(end = 8f.dp))
        inputSegmentConfirm(segmentValue, isSegmentAdded)
    }
}

@Composable
private fun segmentNames() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        verticalDivider()
        Text(text = SEGMENT)
        verticalDivider()
        Text(text = "Дата")
        verticalDivider()
        Text(text = CONSUMER)
        verticalDivider()
        Text(text = DETAILS)
        verticalDivider()
    }
}