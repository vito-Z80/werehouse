package gson

import com.google.gson.Gson


object G {

    val gson = Gson()

    fun deserialize(obj: Any?) {

    }
}

data class Seg(
    var segments: List<Segment?>,
) {
    data class Segment(
//        var consumer: String, // Google
//        var date: String, // 12/10/22
        var details: String, // ad ejejl ssdas,m ds
        var segment: Float, // 230.0
        var state: Boolean,
    )
}