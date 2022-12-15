@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalComposeUiApi::class)

package input

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

val regEngDigOnly = Regex("[^a-zA-Z0-9]")

private val regNumbers = Regex("[^-+0-9.,]")    // FIXME +-,. могут быть в любой части значения. нинада так



@Composable
fun input(
    iText: MutableState<String>, keyboardOptions: KeyboardOptions = KeyboardOptions(),
) {
    var borderColor by remember { mutableStateOf(Color.Blue) }

    fun checkType(): Boolean {
        return when (keyboardOptions.keyboardType) {
            KeyboardType.Number -> {
                !iText.value.contains(regNumbers)
            }

            else -> {
                true
            }
        }
    }

    BasicTextField(
        value = iText.value,
        onValueChange = {
            iText.value = it
            borderColor = if (checkType()) Color.Blue else Color.Red
        },
        maxLines = 1,
        singleLine = true,
        modifier = Modifier.requiredWidth(96f.dp).requiredHeight(20f.dp).border(width = 1f.dp, color = borderColor)
            .padding(2f.dp),
        keyboardOptions = keyboardOptions,
    )
}


@Composable
fun inputSegmentConfirm(
    iText: MutableState<String>,
    isSegmentAdded: MutableState<Boolean>,
) {

//    val (focusRequester) = FocusRequester.createRefs()
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
            },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//        keyboardActions = KeyboardActions(onNext = { focusRequester.requestFocus() })

    )
}




@Composable
fun inputNewTableName(
    text: MutableState<String>,
    borderColorState: () -> Boolean = { false },
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
) {

    var borderColor by remember { mutableStateOf(Color.Blue) }

    BasicTextField(
        value = text.value,
        onValueChange = {
            text.value = it
            borderColor =  if (borderColorState.invoke()) Color.Blue else Color.Red
        },
        maxLines = 1,
        singleLine = true,
        modifier = Modifier.requiredWidth(96f.dp).requiredHeight(20f.dp).border(width = 1f.dp, color = borderColor)
            .padding(2f.dp),
        keyboardOptions = keyboardOptions,
    )


}