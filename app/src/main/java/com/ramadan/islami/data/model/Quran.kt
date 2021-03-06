package com.ramadan.islami.data.model

import com.google.gson.annotations.SerializedName

class Quran {
    @SerializedName("surahs")
    var surahs: Array<Surah>? = null
}