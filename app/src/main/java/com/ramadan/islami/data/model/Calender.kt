package com.ramadan.islami.data.model

import androidx.annotation.Keep
import java.io.Serializable

data class Calender(
    val code: Int,
    val `data`: CalenderData,
    val status: String,
) {
    data class CalenderData(
        val gregorian: Gregorian,
        val hijri: Hijri,
    )
}

@Keep
data class Gregorian(
    val date: String,
    val day: String,
    val month: Month,
    val weekday: Weekday,
    val year: String,
) : Serializable {
    @Keep
    data class Month(
        val en: String,
        val number: Int,
    ) : Serializable

    @Keep
    data class Weekday(
        val en: String,
    ) : Serializable

}

@Keep
data class Hijri(
    val date: String,
    val day: String,
    val holidays: List<Any>,
    val month: Month,
    val weekday: Weekday,
    val year: String,
) : Serializable {
    @Keep
    data class Month(
        val ar: String,
        val en: String,
        val number: Int,
    ) : Serializable

    @Keep
    data class Weekday(
        val ar: String,
        val en: String,
    ) : Serializable
}

