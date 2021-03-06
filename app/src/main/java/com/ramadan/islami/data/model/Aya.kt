package com.ramadan.islami.data.model

import com.google.gson.annotations.SerializedName

class Aya {
    @SerializedName("num")
    var num: String? = null

    @SerializedName("text")
    var text: String? = null
}