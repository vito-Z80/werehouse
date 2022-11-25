import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import kotlin.reflect.KProperty


val SQL_SHOW_TABLE_NAMES = "SELECT name FROM sqlite_master WHERE type='table';".trimMargin()

val SQL_SELECT = { tableName: String, fields: List<String> ->
    val f = if (fields.isEmpty()) "*" else fields.joinToString()
    "SELECT $f FROM $tableName"
}

const val DB_NAME = "wh.db"
const val STEEL_ROPE_TABLE = "steelRope"
const val LIST_TABLE = "listRope"


object DBField {

    val id = Pair(0, "ID")
    val diameter = Pair(0f, "Диаметр")
    val number = Pair("", "Номер")
    val standard = Pair("", "ГОСТ")
    val coming = Pair(0f, "Приход")
    val left = Pair(0f, "Остаток")
    val consumer = Pair("", "Потребитель")
    val arrival = Pair(0, "Прибытие")
    val expiration = Pair(0, "Завершение")
    val details = Pair("", "Детали")


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

    val size = listOf(
        32f.dp, 64f.dp, 128f.dp, 80f.dp, 48f.dp, 48f.dp, 48f.dp, 48f.dp, 48f.dp, 48f.dp
    )

    val requestFields: List<Pair<Any, String>> = list


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
