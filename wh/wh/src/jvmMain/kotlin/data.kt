import androidx.compose.runtime.*


var selectedDBTable by mutableStateOf(STEEL_ROPE_TABLE)
var selectedDB by mutableStateOf(DB_NAME)

var insertRowDialogState by mutableStateOf(false)

// index, row fields
var tableResult: List<ArrayList<String>> by mutableStateOf(arrayListOf())

var windowConfirmState by  mutableStateOf(false)
