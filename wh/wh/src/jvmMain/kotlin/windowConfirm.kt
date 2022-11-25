import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun windowConfirm(title: String = "", text: String, wcf: MutableState<Boolean>) {

    Dialog(onCloseRequest = { wcf.value = false }, title = title, visible = wcf.value) {
        Column(modifier = Modifier.padding(8f.dp)) {
            Text(text = text)

            TextButton(onClick = { wcf.value = false }) {
                Text(text = "OK")
            }
        }
    }
}