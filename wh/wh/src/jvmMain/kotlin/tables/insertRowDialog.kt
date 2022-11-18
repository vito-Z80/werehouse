package tables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import insertRowDialogSate

@Composable
fun insertRowDialog(tableName: String) {

    if (insertRowDialogSate) {
        Window(
            onCloseRequest = { insertRowDialogSate = false },
            resizable = false,
            state = WindowState(width = 768f.dp, height = 300f.dp)
        ) {
            insertRowContent()
        }
    }
}

@Composable
private fun insertRowContent() {
    Column() {

        Row {
            insertRowInput("ID", 0)
            insertRowInput("Diameter", 0)
            insertRowInput("Number", 0)
            insertRowInput("Standard", 0)

        }

    }
}

@Composable
private fun insertRowInput(labelName: String, format: Int) {

    var value by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(4f.dp)
    ) {
        Text(text = labelName, modifier = Modifier.padding(start = 4f.dp))
        BasicTextField(
            value = value,
            onValueChange = { value = it },
            maxLines = 1,
            modifier = Modifier.requiredWidth(96f.dp).requiredHeight(20f.dp).border(width = 1f.dp, color = Color.Blue)
                .padding(2f.dp)
                .align(Alignment.CenterHorizontally)
        )
    }

}