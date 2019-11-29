package cosmic.com.pkwprj.model

import android.graphics.drawable.Drawable
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class Office : Serializable {

    @SerializedName("name")
    var name: String? = null
    @SerializedName("location")
    var location: String? = null
    @SerializedName("reservations")
    var reservations: ArrayList<Reservations>? = null
    @SerializedName("drawable")
    var drawable: Drawable? = null

    constructor(name: String, location: String) {
        this.name = name
        this.location = location
    }

    constructor(
        name: String?,
        location: String?,
        reservations: ArrayList<Reservations>?,
        drawable: Drawable
    ) {
        this.name = name
        this.location = location
        this.reservations = reservations
        this.drawable = drawable
    }


    inner class Reservations(
        @field:SerializedName("startTime")
        var startTime: String?, @field:SerializedName("endTime")
        var endTime: String?
    ) : Serializable
}
