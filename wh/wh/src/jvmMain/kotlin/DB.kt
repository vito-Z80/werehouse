import tables.Edit
import tables.insertFields
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException


////////    https://www.sqlitetutorial.net/sqlite-java/update/

object DB {

    private fun updateSqlById(tableName: String, fieldIndex: Int, fields: List<String>, data: List<Any>): String {
        val str = ArrayList<String>()
        fields.forEachIndexed { id, s ->
            str.add("'$s' = ${data[id]}")
        }
        return """UPDATE $tableName SET ${str.joinToString()} WHERE $ID = $fieldIndex;""".trimMargin()
    }

    private fun lastRowSql(tableName: String) = "SELECT * FROM $tableName ORDER BY $ID DESC LIMIT 1;"

    private fun insertSql(tableName: String, fieldNames: List<String>, values: List<String>): String {
        val str = ArrayList<String>()
        fieldNames.forEach {
            str.add("`$it`")
        }
        return """INSERT INTO $tableName (${str.joinToString()}) VALUES(${values.joinToString()});""".trimMargin()
    }

    private fun createTableSql(tableName: String) = """CREATE TABLE IF NOT EXISTS $tableName (
	${DBField.id.second} integer PRIMARY KEY,
    `${DBField.diameter.second}` real,
    `${DBField.number.second}` text,
    `${DBField.standard.second}` text,
    `${DBField.coming.second}` real,
    `${DBField.left.second}` real,
    `${DBField.consumer.second}` text,
    `${DBField.arrival.second}` integer,
    `${DBField.expiration.second}` integer,
    `${DBField.details.second}` json
);"""



    private const val driver = "jdbc:sqlite:"


    fun connect(dbName: String, closed: Boolean = false) = create(dbName, closed)


    fun create(dbName: String, closed: Boolean = false): Connection? {
        return try {
            val connection = DriverManager.getConnection("${driver}$dbName.db")
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
            stat?.execute(createTableSql(tableName))
            it?.close()
            selectedDBTable = tableName
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

    /*
        Выбрать таблицу с нужными полями. Если поля не заданы - выбрать все поля.
     */
    fun selectFields(dbName: String, tableName: String, vararg fields: String) {
        connect(dbName).let {
            val statement = it?.createStatement()
            val selectResult = statement?.executeQuery(SQL_SELECT(tableName, fields.toList()))

            val result = ArrayList<ArrayList<String>>()
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
//            println("Select [$dbName | $tableName] ${fields.joinToString()}")
            it?.close()
        }

    }

    fun insertDefaultValues(dbName: String, tableName: String) {
        val def = "INSERT INTO $tableName DEFAULT VALUES;"
        connect(dbName).let {
            it?.prepareStatement(def)?.executeUpdate()
            it?.close()
        }
    }

    /*
        Только для объекта Edit in (insertRowDialog)
     */
    fun insert(dbName: String, tableName: String) {
        connect(dbName).let {
            val values = listOf(
                Edit.diameter.value,
                "\"${Edit.number.value}\"",
                "\"${Edit.standard.value}\"",
                Edit.amount.value
            )
            val sql = insertSql(tableName, insertFields, values)
            println(sql)
            val ps = it?.prepareStatement(sql)
            ps?.executeUpdate()
            it?.close()
        }
    }


    // https://www.sqlite.org/docs.html
    fun updateById(dbName: String, tableName: String, index: Int, fields: List<String>, data: List<Any>) {
        connect(dbName).let {
            val ur = updateSqlById(tableName, index, fields, data)
            println(ur)
            val ps = it?.prepareStatement(ur)
            ps?.executeUpdate()
            ps?.close()
            it?.close()
            println("$dbName be updated succesfuly")
        }

        // TODO https://ru.stackoverflow.com/questions/682604/%D0%BA%D0%B0%D0%BA-%D0%BF%D1%80%D0%B0%D0%B2%D0%B8%D0%BB%D1%8C%D0%BD%D0%BE-%D1%81%D0%BE%D1%81%D1%82%D0%B0%D0%B2%D0%B8%D1%82%D1%8C-%D0%B7%D0%B0%D0%BF%D1%80%D0%BE%D1%81-jdbc-update
    }


}


fun main() {


//    val sql = "UPDATE $selectedDBTable SET `Диаметр` = ? WHERE ID = ?;"
//    DB.connect("wh").let {
//        val ps = it?.prepareStatement(sql)
//        ps?.setFloat(1, 22.5f)
//        ps?.setInt(2, 1)
//        ps?.executeUpdate()
//        it?.close()
//    }
//    return

    val fields = listOf(DIAMETER, NUMBER, AMOUNT)
    val data = listOf(8.8f, "\"7777/23-03И\"", 1000f)
//    DB.create(DB_NAME, closed = true)
//    DB.createTable(selectedDB, selectedDBTable)


    DB.updateById(selectedDB, selectedDBTable, 2, fields, data)

//    val r = DB.updateRequest("afdas", 23, fields, data)
//    println(r)
}