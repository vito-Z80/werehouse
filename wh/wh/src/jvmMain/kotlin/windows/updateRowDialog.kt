@file:OptIn(ExperimentalComposeUiApi::class)

package windows

import AMOUNT
import CANCEL
import CONFIRM
import DB
import DBField
import DETAILS
import DIAMETER
import LEFT
import NUMBER
import ON_VH
import OTPR
import SEGMENT
import STANDARD
import STATE
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.rememberDialogState
import divider
import gson.G
import gson.Seg
import input.inputName
import kotlinx.coroutines.withContext
import selectedDB
import selectedDBTable
import stringToFloat
import tableResult


@Composable
fun updateRowDialog(rowIndex: MutableState<Int>) {

    val state = rememberDialogState(size = DpSize(512f.dp, 512f.dp))



    if (rowIndex.value >= 0) {
        val detailIndex = DBField.list.indexOfFirst { it.second.lowercase() == DETAILS.lowercase() }
        val tableDetail = remember { mutableStateOf(tableResult[rowIndex.value][detailIndex]) }
        val segments: MutableState<Seg?> =
            remember { mutableStateOf(G.gson.fromJson(tableDetail.value, Seg::class.java)) }
        val segmentValue = remember { mutableStateOf("0") }
        val confirmEnabling = remember { mutableStateOf(false) }
        val isTableUpdate = remember { mutableStateOf(false) }
        val leftValue = remember { mutableStateOf(0f) }
        val animationVisible = remember { mutableStateOf(false) }
        // for mutable content
        val amountIndex = DBField.list.indexOfFirst { it.second.lowercase() == AMOUNT.lowercase() }
        val amount = tableResult[rowIndex.value][amountIndex].stringToFloat().second
        val diameterIndex = DBField.list.indexOfFirst { it.second.lowercase() == DIAMETER.lowercase() }
        val diameterEdit = remember { mutableStateOf(tableResult[rowIndex.value][diameterIndex]) }

        val numberIndex = DBField.list.indexOfFirst { it.second.lowercase() == NUMBER.lowercase() }
        val numberEdit = remember { mutableStateOf(tableResult[rowIndex.value][numberIndex]) }

        val standardIndex = DBField.list.indexOfFirst { it.second.lowercase() == STANDARD.lowercase() }
        val standardEdit = remember { mutableStateOf(tableResult[rowIndex.value][standardIndex]) }

        val amountEdit = remember { mutableStateOf(tableResult[rowIndex.value][amountIndex]) }

        Dialog(
            onKeyEvent = {
                if (it.key == Key.Escape) {
                    rowIndex.value = -1
                }
                false   // if FASLE >  key working, else:  key not working
            },
            onCloseRequest = { rowIndex.value = -1 },
            resizable = true,
            undecorated = true, state = state
        ) {
            Scaffold(
                topBar = {
//                    top(rowIndex, segments)
                },
                bottomBar = {
                    bottom(rowIndex, segmentValue, confirmEnabling, isTableUpdate, leftValue, segments)
                },
                content = {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.border(width = 1f.dp, color = Color.Black).fillMaxWidth()
                                .height(state.size.height - it.calculateBottomPadding()),
                            horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center
                        ) {
                            iconBar(animationVisible, rowIndex)
                            if (animationVisible.value) {
                                divider(width = 1f, color = Color.Black)
                            }
                            // mutable data (diameter, number, standard, amount)
                            AnimatedVisibility(visible = animationVisible.value) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Spacer(Modifier.height(8f.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = DIAMETER)
                                        inputName(text = diameterEdit, { diameterEdit.value.stringToFloat().first })
                                    }


                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = NUMBER)
                                        inputName(text = numberEdit, { true })
                                    }

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = STANDARD)
                                        inputName(text = standardEdit, { true })
                                    }


                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = AMOUNT)
                                        inputName(text = amountEdit, { amountEdit.value.stringToFloat().first })
                                    }
                                    Spacer(Modifier.height(8f.dp))
                                }
//                                mutableContent(
//                                    rowIndex,
//                                    confirmEnabling,
//                                    isTableUpdate,
//                                    segments,
//                                    segmentValue,
//                                    leftValue
//                                )
                            }
                            confirmEnabling.value =
                                segmentValue.value.stringToFloat().first && segmentValue.value.stringToFloat().second > 0f &&
                                        amountEdit.value.stringToFloat().first && amountEdit.value.stringToFloat().second > 0f &&
                                        diameterEdit.value.stringToFloat().first && diameterEdit.value.stringToFloat().second > 0f
                            divider(width = 1f)
                            detailsList(segments)
                            divider(width = 1f)
                        }
                    }
                },
            )
        }
    } else {
        Edit.reset()
    }
}

@Composable
private fun mutableContent(
    rowIndex: MutableState<Int>,
    confirmEnabling: MutableState<Boolean>,
    isTableUpdate: MutableState<Boolean>,
    segments: MutableState<Seg?>,
    segmentValue: MutableState<String>,
    leftValue: MutableState<Float>,
) {

    if (rowIndex.value < 0) return

    val amountIndex = DBField.list.indexOfFirst { it.second.lowercase() == AMOUNT.lowercase() }
    val amount = tableResult[rowIndex.value][amountIndex].stringToFloat().second

    LaunchedEffect(isTableUpdate.value) {
        if (isTableUpdate.value) {
            isTableUpdate.value = withContext(this.coroutineContext) {
                val detailsIndex = DBField.list.indexOfFirst { it.second == DETAILS }
                var segmentList =
                    G.gson.fromJson(tableResult[rowIndex.value][detailsIndex], Seg::class.java)?.segments ?: listOf()
                val s = Seg.Segment(
                    "12/10/22", segmentValue.value.stringToFloat().second, false
                )
                segmentList = segmentList.plus(s)
                val newSeg = Seg(segmentList)
                val result = G.gson.toJson(newSeg)
                println("UPDATE RESULT: $result")
                tableResult[rowIndex.value][detailsIndex] = result
                DB.updateDetails(selectedDB, selectedDBTable, result, rowIndex.value)
                rowIndex.value = -1
                segments.value = newSeg
                left(segments, amount, leftValue)
                false
            }
        }
    }


    Column(modifier = Modifier.fillMaxWidth()) {

        val diameterIndex = DBField.list.indexOfFirst { it.second.lowercase() == DIAMETER.lowercase() }
        val diameter = remember { mutableStateOf(tableResult[rowIndex.value][diameterIndex]) }

        val numberIndex = DBField.list.indexOfFirst { it.second.lowercase() == NUMBER.lowercase() }
        val number = remember { mutableStateOf(tableResult[rowIndex.value][numberIndex]) }

        val standardIndex = DBField.list.indexOfFirst { it.second.lowercase() == STANDARD.lowercase() }
        val standard = remember { mutableStateOf(tableResult[rowIndex.value][standardIndex]) }

        val amountValue = remember { mutableStateOf(tableResult[rowIndex.value][amountIndex]) }


        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(8f.dp))

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
                inputName(text = amountValue, { amountValue.value.stringToFloat().first })
            }
            Spacer(Modifier.height(8f.dp))
        }

        confirmEnabling.value =
            segmentValue.value.stringToFloat().first && segmentValue.value.stringToFloat().second > 0f &&
                    amountValue.value.stringToFloat().first && amountValue.value.stringToFloat().second > 0f &&
                    diameter.value.stringToFloat().first && diameter.value.stringToFloat().second > 0f

    }
}


@Composable
private fun iconBar(animationVisible: MutableState<Boolean>, rowIndex: MutableState<Int>) {
    Row(modifier = Modifier.fillMaxWidth().padding(4f.dp)) {

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Start
        ) {
            Image(painter = painterResource("icon_edit.png"), contentDescription = "Редактировать",
                modifier = Modifier
                    .width(16f.dp)
                    .height(16f.dp)
                    .clickable {
                        animationVisible.value = !animationVisible.value
                    }
            )

            Image(
                painter = painterResource("icon_close.png"), contentDescription = "Exit",
                modifier = Modifier
                    .padding(end = 0f.dp)
                    .width(16f.dp)
                    .height(16f.dp)
                    .clickable {
                        rowIndex.value = -1
                    }
            )
        }
    }
}

@Composable
private fun detailsList(segments: MutableState<Seg?>) {
    val scrollValue by remember { mutableStateOf(0) }
    var columnHeight by remember { mutableStateOf(0) }

    @Composable
    fun vertDivider() {
        Box(modifier = Modifier.width(1f.dp).height(columnHeight.dp).background(color = Color.Black))
    }

    Row(
        modifier = Modifier.fillMaxWidth().verticalScroll(ScrollState(scrollValue)),
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        Column(modifier = Modifier.defaultMinSize(minWidth = 64f.dp).onSizeChanged {
            columnHeight = it.height
        }) {
            Text(text = SEGMENT)
            repeat(segments.value?.segments?.size ?: 0) { id ->
                Text(text = "${segments.value?.segments?.get(id)?.segment}")
            }
        }
        vertDivider()
        Column(modifier = Modifier.defaultMinSize(minWidth = 72f.dp)) {
            Text(text = STATE)
            repeat(segments.value?.segments?.size ?: 0) { id ->
                val state = segments.value?.segments?.get(id)?.state ?: false
                Text(text = if (state) OTPR else ON_VH)
            }
        }


        vertDivider()
        Column(modifier = Modifier.defaultMinSize(minWidth = 72f.dp)) {
            Text(text = DETAILS)
            repeat(segments.value?.segments?.size ?: 0) { id ->
                Text(text = "${segments.value?.segments?.get(id)?.details}")
            }
        }

    }
}

@Composable
private fun bottom(
    rowIndex: MutableState<Int>,
    segmentValue: MutableState<String>,
    confirmEnabling: MutableState<Boolean>,
    isTableUpdate: MutableState<Boolean>,
    leftValue: MutableState<Float>,
    segments: MutableState<Seg?>,
) {
    if (rowIndex.value < 0) return

    val leftValueColor = if (leftValue.value < 0) Color.Red else Color.White
    val amountIndex = DBField.list.indexOfFirst { it.second.lowercase() == AMOUNT.lowercase() }
    val amount = tableResult[rowIndex.value][amountIndex].stringToFloat().second
    var leftOnce by remember { mutableStateOf(true) }

    LaunchedEffect(leftOnce) {
        if (leftOnce) {
            leftOnce = false
            left(segments, amount, leftValue)
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth().background(color = Color.LightGray)
            .border(width = 1f.dp, color = Color.DarkGray)
            .requiredHeight(72f.dp),
    ) {
        Column() {
            // отображение начального кол-ва и остатка
            Row(
                modifier = Modifier.fillMaxWidth().background(color = Color.Blue),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = "$LEFT:", color = Color.White, modifier = Modifier.weight(1f).padding(horizontal = 4f.dp))
                Text(
                    text = "$amount / ${leftValue.value}",
                    color = leftValueColor,
                    modifier = Modifier.padding(end = 4f.dp)
                )
            }
            Spacer(modifier = Modifier.height(2f.dp))
            //  ввод отрезка
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Text(text = "$SEGMENT:")
                inputName(text = segmentValue, { segmentValue.value.stringToFloat().first })
            }
            //  конпки отвены/подтверждение
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {

                TextButton(onClick = {
                    rowIndex.value = -1
                }) {
                    Text(text = CANCEL)
                }
                TextButton(
                    enabled = confirmEnabling.value,
                    onClick = {
                        isTableUpdate.value = true
                    }) {
                    Text(text = CONFIRM)
                }
            }
        }
    }
}


/**
 * расчет остатка на барабане
 */
private fun left(segments: MutableState<Seg?>, amount: Float, leftValue: MutableState<Float>) {
    var r = 0f
    segments.value?.segments?.forEach {
        r += it?.segment ?: 0f
    }
    leftValue.value = amount - r
}
