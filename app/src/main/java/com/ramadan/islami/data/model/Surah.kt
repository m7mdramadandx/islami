package com.ramadan.islami.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Surah : Serializable {
    @SerializedName("name")
    var name: String? = null

    @SerializedName("ayahs")
    var ayahs: List<Aya>? = null

    @SerializedName("num")
    var num: String? = null

}