package tables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import insertRowDialogSate

@Composable
fun steelRope() {
    Column() {


        TextButton(onClick = {
            insertRowDialogSate = true
        }, modifier = Modifier.padding(end = 0f.dp, bottom = 0f.dp).align(Alignment.End)) {
            Text("add")
        }

    }
}