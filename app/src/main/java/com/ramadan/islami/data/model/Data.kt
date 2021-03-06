package com.ramadan.islami.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {
    @SerializedName("surahs")
    @Expose
    private var surahs: List<Surah>? = null


    constructor(surahs: List<Surah>?) : super() {
        this.surahs = surahs
    }

    fun getSurahs(): List<Surah>? {
        return surahs
    }

    fun setSurahs(surahs: List<Surah>?) {
        this.surahs = surahs
    }
}