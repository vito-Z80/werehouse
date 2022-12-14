@file:OptIn(ExperimentalComposeUiApi::class)

package input

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


@Composable
fun input(
    iText: MutableState<String>, keyboardOptions: KeyboardOptions = KeyboardOptions(),
) {
    BasicTextField(
        value = iText.value,
        onValueChange = { iText.value = it },
        maxLines = 1,
        singleLine = true,
        modifier = Modifier.requiredWidth(96f.dp).requiredHeight(20f.dp).border(width = 1f.dp, color = Color.Blue)
            .padding(2f.dp),
        keyboardOptions = keyboardOptions
    )
}


@Composable
fun inputSegmentConfirm(
    iText: MutableState<String>,
    isSegmentAdded: MutableState<Boolean>,
) {

    val (focusRequester) = FocusRequester.createRefs()
    BasicTextField(
        value = iText.value,
        onValueChange = { iText.value = it },
        maxLines = 1,
        singleLine = true,
        modifier = Modifier.requiredWidth(96f.dp).requiredHeight(20f.dp).border(width = 1f.dp, color = Color.Blue)
            .padding(2f.dp)
            .onKeyEvent {
                isSegmentAdded.value = it.key == Key.Enter
                isSegmentAdded.value
//                if (it.key == Key.Enter) {
//                    isSegmentAdded.value = true
//                    true
//                } else {
//
//                    false
//                }

            },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        keyboardActions = KeyboardActions(onDone = { focusRequester.requestFocus() })

    )
}