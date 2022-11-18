import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DB {


    private fun tableStr(tableName: String) = """CREATE TABLE IF NOT EXISTS $tableName (
	id integer PRIMARY KEY,
    diameter real,
    number text,
    standard text,
    coming real,
    left real,
    consumer text,
    arrival_date integer,
    expiration_date integer,
    details blob
);"""

    private fun insertStr(tableName: String) =
        "INSERT INTO $tableName(diameter,number,standard,coming,left,consumer,arrival_date,expiration_date,details) VALUES(?,?,?,?,?,?,?,?,?)"

    private const val createStr = "jdbc:sqlite:"
    private var connection: Connection? = null


    fun create(dbName: String) {
        try {
            connection = DriverManager.getConnection("${createStr}$dbName")

            val meta = connection?.metaData
            println("Driver: ${meta?.driverName}")
        } catch (e: SQLException) {
            println(e.message)
        }
    }

    fun createTable(tableName: String) {
        val stat = connection?.createStatement()
        stat?.execute(tableStr(tableName))
    }

    /**
     * @param tableName - table name
     * @param data Field.Type - field type, String - data
     */
    fun insertTo(tableName: String, vararg data: Pair<Field.Type, Any?>) {
        val pStat = connection?.prepareStatement(insertStr(tableName))
        data.forEachIndexed { id, d ->
            when (d.first) {
                Field.Type.INT -> pStat?.setInt(id + 1, (d.second as Int))
                Field.Type.JSON -> pStat?.setObject(id + 1, d.second)
                Field.Type.REAL -> pStat?.setDouble(id + 1, (d.second as Double))
                Field.Type.STRING -> pStat?.setString(id + 1, (d.second as String))
                else -> {}
            }
        }
        pStat?.executeUpdate()

    }

//    fun insertTo(tableName: String, vararg data: String) {
//        val pStat = connection?.prepareStatement(insertStr(tableName))
//        data.forEachIndexed { id, d ->
//            pStat?.setString(id + 1, d)
//        }
//    }

}

object Field {

    enum class Type {
        INT, REAL, JSON, STRING
    }

    const val ID = "Id"
    const val DIAMETER = "Diameter"
    const val NUMBER = "Number"
    const val STANDARD = "Standard"
    const val BUYER = "Buyer"
    const val ARRIVAL_LENGTH = "Arrival length"
    const val SECTION = "Section"
}