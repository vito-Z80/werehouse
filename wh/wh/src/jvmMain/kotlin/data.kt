import androidx.compose.runtime.*
import java.sql.ResultSet


var selectedDBTable by mutableStateOf(STEEL_ROPE_TABLE)

var insertRowDialogSate by mutableStateOf(false)
var selectResult: ResultSet? by mutableStateOf(null)

// index, row fields
var tableResult: ArrayList<List<String>> by mutableStateOf(arrayListOf())

var windowConfirmState by  mutableStateOf(false)
