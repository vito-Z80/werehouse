package gson

import com.google.gson.Gson


object G {

    val gson = Gson()

    fun deserialize(obj:Any?) {

    }

    fun test() {
        val t = Seg()
        val gs = GSegment(100f,"23/12/23\n12:23","afklsdfksd", "other")
        t.segments.add(gs)


        val result = gson.toJson(t)
        println(result)

        val obj = gson.fromJson(result,Seg::class.java)
        println(obj.segments[0].segment)
    }

}

fun main() {
    G.test()
}