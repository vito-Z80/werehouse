import androidx.compose.runtime.*
import java.sql.ResultSet


var selectedDBTable by mutableStateOf(STEEL_ROPE_TABLE)
var selectedDB by mutableStateOf(DB_NAME)

var insertRowDialogSate by mutableStateOf(false)

// index, row fields
var tableResult: List<List<String>> by mutableStateOf(arrayListOf())

var windowConfirmState by  mutableStateOf(false)
