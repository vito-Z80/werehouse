package input

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun input(iText: MutableState<String>) {
    BasicTextField(
        value = iText.value,
        onValueChange = { iText.value = it },
        maxLines = 1,
        singleLine = true,
        modifier = Modifier.requiredWidth(96f.dp).requiredHeight(20f.dp).border(width = 1f.dp, color = Color.Blue)
            .padding(2f.dp)
    )
}
