import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import kotlin.reflect.KProperty


val SQL_SHOW_TABLE_NAMES = "SELECT name FROM sqlite_master WHERE type='table';".trimMargin()

val SQL_SELECT = { tableName: String, fields: List<String> ->
    val f = if (fields.isEmpty()) "*" else fields.joinToString()
    "SELECT $f FROM $tableName"
}

const val DB_NAME = "wh"
const val STEEL_ROPE_TABLE = "steelRope"
const val LIST_TABLE = "listRope"

const val ID = "ID"
const val DIAMETER = "Диаметр"
const val NUMBER = "Номер"
const val GOST = "ГОСТ-ТУ"
const val COMING = "Приход"
const val LEFT = "Остаток"
const val CONSUMER = "Потребитель"
const val COMING_DATE = "Дата прихода"
const val OUTGOING_DATE = "Дата отдачи"
const val DETAILS = "Детали"

const val SEGMENT = "Отрезок"
object DBField {

    val id = Pair(0, ID)
    val diameter = Pair(0f, DIAMETER)
    val number = Pair("", NUMBER)
    val standard = Pair("", GOST)
    val coming = Pair(0f, COMING)
    val left = Pair(0f, LEFT)
    val consumer = Pair("", CONSUMER)
    val arrival = Pair(0, COMING_DATE)
    val expiration = Pair(0, OUTGOING_DATE)
    val details = Pair("", DETAILS)


    val list = listOf(
        id,
        diameter,
        number,
        standard,
        coming,
        left,
        consumer,
        arrival,
        expiration,
        details
    )

    val requestFields: List<Triple<Any, MutableState<Boolean>, String>> =
        list.map { Triple(it.first, mutableStateOf(true), it.second) }


    val values = MutableList(list.size) { mutableStateOf("") }

    class Diameter {
        operator fun getValue(thisRef: Any?, prop: KProperty<*>): String {
            return prop.name
        }
    }
}

fun main() {
    val diameter by DBField.Diameter()

    println(diameter)
}
