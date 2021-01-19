package com.ramadan.islamicAwareness.data.model


class Prophet(name: String, imgUrl: String, text: ArrayList<String>) {
    var name: String? = name
    var imgUrl: String? = imgUrl
    var text: ArrayList<String>? = text

    fun name(): String? {
        return name
    }

    fun imgUrl(): String? {
        return imgUrl
    }
}
