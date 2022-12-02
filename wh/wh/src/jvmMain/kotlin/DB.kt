import androidx.compose.runtime.MutableState
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import kotlin.concurrent.thread

object DB {


    private fun tableStr(tableName: String) = """CREATE TABLE IF NOT EXISTS $tableName (
	${DBField.id.second} integer PRIMARY KEY,
    ${DBField.diameter.second} real,
    ${DBField.number.second} text,
    ${DBField.standard.second} text,
    ${DBField.coming.second} real,
    ${DBField.left.second} real,
    ${DBField.consumer.second} text,
    ${DBField.arrival.second} integer,
    ${DBField.expiration.second} integer,
    ${DBField.details.second} blob
);"""

    private fun insertStr(tableName: String) =
        """INSERT INTO 
            $tableName(${DBField.diameter.second},
            ${DBField.number.second},
            ${DBField.standard.second},
            ${DBField.coming.second},
            ${DBField.left.second},
            ${DBField.consumer.second},
            ${DBField.arrival.second},
            ${DBField.expiration.second},
            ${DBField.details.second}) VALUES(?,?,?,?,?,?,?,?,?)""".trimMargin()

    private const val createStr = "jdbc:sqlite:"


    fun connect(dbName: String, closed: Boolean = false) = create(dbName, closed)


    fun create(dbName: String, closed: Boolean = false): Connection? {
        return try {
            val connection = DriverManager.getConnection("${createStr}$dbName.db")
            val meta = connection?.metaData
            println("Driver: ${meta?.driverName}")
            println("БД \"$dbName\" была создана или уже существует.")
            if (closed) {
                connection?.close()
            }
            selectedDB = dbName
            connection
        } catch (e: SQLException) {
            println("ОШИБКА создания БД.")
            println(e.message)
            null
        }
    }

    fun createTable(dbName: String, tableName: String) {
        connect(dbName).let {
            val stat = it?.createStatement()
            stat?.execute(tableStr(tableName))
            it?.close()
        }
    }

    fun showTables(dbName: String): List<String> {
        connect(dbName).let {
            val statement = it?.createStatement()
            val list = statement?.executeQuery(SQL_SHOW_TABLE_NAMES)
            val result = ArrayList<String>()
            while (list?.next() == true) {
                result.add(list.getString(1))
            }
            it?.close()
            return result.toList()
        }
    }

    fun selectFrom(dbName: String, tableName: String, vararg fields: String) {
        connect(dbName).let {
            val statement = it?.createStatement()
            val selectResult = statement?.executeQuery(SQL_SELECT(tableName, fields.toList()))

            val result = ArrayList<List<String>>()
            while (selectResult?.next() == true) {
                val row = ArrayList<String>()
                DBField.requestFields.forEachIndexed { id, pair ->
                    when (pair.first) {
                        is String -> {
                            row.add(selectResult.getString(id + 1) ?: "")
                        }

                        is Double -> {
                            row.add(selectResult.getDouble(id + 1).toString())
                        }

                        is Float -> {
                            row.add(selectResult.getFloat(id + 1).toString())
                        }

                        is Int -> {
                            row.add(selectResult.getInt(id + 1).toString())
                        }

                        else -> {
                            row.add("-----")
                        }
                    }

                }
                result.add(row)
            }
            tableResult = result
            println("Select [$dbName | $tableName] ${fields.joinToString()}")
            it?.close()
        }

    }

    fun insertTo(dbName: String, tableName: String, data: MutableList<MutableState<String>>) {
        connect(dbName).let {
            val pStat = it?.prepareStatement(insertStr(tableName))
            data.drop(1).forEachIndexed { id, k ->
                // TODO роверять тип, вызывать предупреждение при неправильном типе введенных данных и не обновлять БД.
                pStat?.setString(id + 1, k.value)
            }
            pStat?.executeUpdate()
//            selectFrom(dbName, selectedDBTable)
            it?.close()
        }
    }

}
