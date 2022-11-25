import androidx.compose.runtime.MutableState
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DB {

    // TODO везде открывать и закрывать соединение с БД.

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
    var connection: Connection? = null


    fun connect(dbName: String) {
        create(dbName)
    }

    fun create(dbName: String) {
        try {
            connection = DriverManager.getConnection("${createStr}$dbName")
            val meta = connection?.metaData
            println("Driver: ${meta?.driverName}")
        } catch (e: SQLException) {
            println(e.message)
        }
    }

    fun close() {
        connection?.close()
    }

    fun createTable(tableName: String) {
        val stat = connection?.createStatement()
        stat?.execute(tableStr(tableName))
    }

    fun showTables(): List<String> {
        val statement = connection?.createStatement()
        val list = statement?.executeQuery(SQL_SHOW_TABLE_NAMES)
        val result = ArrayList<String>()
        while (list?.next() == true) {
            result.add(list.getString(1))
        }
        return result.toList()
    }

    fun selectFrom(tableName: String, vararg fields: String) {

        val statement = connection?.createStatement()
        selectResult = statement?.executeQuery(SQL_SELECT(tableName, fields.toList()))
        tableResult.clear()
        while (selectResult?.next() == true) {
            val row = ArrayList<String>()
            DBField.requestFields.forEachIndexed { id, pair ->
                when (pair.first) {
                    is String -> {
                        row.add(selectResult?.getString(id + 1).toString())
                    }

                    is Double -> {
                        row.add(selectResult?.getDouble(id + 1).toString())
                    }

                    is Float -> {
                        row.add(selectResult?.getFloat(id + 1).toString())
                    }

                    is Int -> {
                        row.add(selectResult?.getInt(id + 1).toString())
                    }

                    else -> {
                        row.add("-----")
                    }
                }

            }
            tableResult.add(row)
        }

    }

    fun insertTo(tableName: String, data: MutableList<MutableState<String>>) {
        val pStat = connection?.prepareStatement(insertStr(tableName))
        data.drop(1).forEachIndexed { id, k ->
            // TODO роверять тип, вызывать предупреждение при неправильном типе введенных данных и не обновлять БД.
            pStat?.setString(id + 1, k.value)
        }
        pStat?.executeUpdate()
//        createTable(STEEL_ROPE_TABLE)
        selectFrom(selectedDBTable)
//        selectFrom(STEEL_ROPE_TABLE, *DBField.requestFields.map { it.second }.toTypedArray())
    }

}
