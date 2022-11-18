package topMenu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun topMenu() {
    val expandFileDialog = remember { mutableStateOf(false) }
    val expandCardDialog = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(2f.dp)
            .background(color = Color.LightGray)
            .border(width = 1.dp, color = Color.DarkGray)
    ) {
        Text(text = "File", modifier = Modifier
            .clickable {
                expandFileDialog.value = true
            }
            .padding(4.dp)
        )//.apply { popupFileMenu(expandFileDialog) }

        Text(text = "Card", modifier = Modifier
            .clickable {
                expandCardDialog.value = true
            }
            .padding(4.dp)
        )//.apply { popupCardMenu(expandCardDialog) }
    }
}