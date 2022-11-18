import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tables.insertRowDialog
import tables.steelRope

@Composable
fun tables() {

    Column(modifier = Modifier.fillMaxWidth()) {
        steelRope()
    }

    insertRowDialog("steelRope")

}