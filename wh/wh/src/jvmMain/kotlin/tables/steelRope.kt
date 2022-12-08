package tables

import DBField
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import selectedDB
import selectedDBTable
import tableResult

@Composable
fun steelRope() {
    val hScroll by remember { mutableStateOf(ScrollState(0)) }

    LaunchedEffect(selectedDBTable) {
        DB.selectFrom(selectedDB, selectedDBTable)
//        DB.insertTo(selectedDB,selectedDBTable, DBField.values)
    }

    val detailsIndex = remember { mutableStateOf(-1) }

    Column(modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(4f.dp)) {
        Divider(color = Color.DarkGray)
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(state = hScroll)
                .background(color = Color(0.7f, 0.7f, 1f, 1f)),
            horizontalArrangement = Arrangement.Center
        ) {
            DBField.requestFields.forEach { item ->
                if (item.second.value) {
                    Text(
                        text = item.third,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.requiredWidth(96f.dp).padding(4f.dp).align(Alignment.CenterVertically),
                        softWrap = false
                    )
                    vertDivider()
                }
            }
        }
        Divider(color = Color.DarkGray)

        var rowColor = false
        LazyColumn() {

            items(tableResult.size) {

                rowColor = !rowColor
                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(state = hScroll)
                        .background(color = if (rowColor) Color.White else Color.LightGray)
                        .clickable { detailsIndex.value = it },
                    horizontalArrangement = Arrangement.Center
                ) {
                    tableResult[it].forEachIndexed { id, field ->
                        if (DBField.requestFields[id].second.value) {
                            Text(
                                text = field,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.requiredWidth(96f.dp).padding(4f.dp)
                                    .align(Alignment.CenterVertically),
                                softWrap = false
                            )
                            vertDivider()
                        }
                    }
                }
                Divider(color = Color.DarkGray)
            }
        }
        detail(detailsIndex)
    }

}

@Composable
fun vertDivider() {
    Box(
        modifier = Modifier
            .padding(top = 1f.dp, bottom = 1f.dp, end = 4f.dp)
            .height(24f.dp)
            .width(1f.dp)
            .background(color = Color.DarkGray)
    )
}