import androidx.compose.runtime.*


var selectedDBTable by mutableStateOf("")
var selectedDB by mutableStateOf("")

var insertRowDialogState by mutableStateOf(false)

// index, row fields
var tableResult: List<ArrayList<String>> by mutableStateOf(arrayListOf())

var windowConfirmState by  mutableStateOf(false)
