package com.ramadan.islami.data.model

data class Qibla(
    val code: Int,
    val `data`: Data,
    val status: String,
) {
    data class Data(
        val direction: Double,
        val latitude: Double,
        val longitude: Double,
    )
}


