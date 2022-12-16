import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import windows.insertRowDialog
import tables.steelRope


@Composable
fun tables() {

    Column(modifier = Modifier.fillMaxWidth()) {
        if (selectedDB.isNotEmpty() && selectedDBTable.isNotEmpty()) {
            steelRope()
        }
//        when (selectedDBTable) {
//            STEEL_ROPE_TABLE -> {
//                steelRope()
//                println("rope table")
//            }
//
//            LIST_TABLE -> {
//                println("lists table")
//            }
//        }
    }

    insertRowDialog()

}
