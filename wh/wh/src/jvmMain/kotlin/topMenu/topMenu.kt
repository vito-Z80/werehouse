package topMenu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun topMenu() {
    val expandFileDialog = remember { mutableStateOf(false) }
    val expandCardDialog = remember { mutableStateOf(false) }
    val expandProductPopup = remember { mutableStateOf(false) }
    val expandDBPopup = remember { mutableStateOf(false) }
    val expandTest = remember { mutableStateOf(false) }


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

        Box {
            Text(text = "Test", modifier = Modifier
                .clickable {
                    expandTest.value = true
                }
                .padding(4.dp)
            )
            testPopup(expandTest)
        }
        Box {
            Text(text = "Товар", modifier = Modifier
                .clickable {
                    expandProductPopup.value = true
                }
                .padding(4.dp)
            )
            productPopup(expandProductPopup)
        }

        Box {
            Text(text = "База Данных", modifier = Modifier
                .clickable {
                    expandDBPopup.value = true
                }
                .padding(4.dp)
            )
            dbPopup(expandDBPopup)
        }

    }


    //


}