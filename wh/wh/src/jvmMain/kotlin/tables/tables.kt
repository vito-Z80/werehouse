import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tables.insertRowDialog
import tables.steelRope
import java.util.TreeSet


@Composable
fun tables() {

    Column(modifier = Modifier.fillMaxWidth()) {
        if (selectedDBTable.isNotEmpty()) {
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


class AppTable {

    val names = ArrayList<String>()
    val fields = ArrayList<TreeSet<String>>()

    fun addTable(tableName: String, vararg fields: String) {

    }

    fun addField(tableName: String, field: String) {

    }

}