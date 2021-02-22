package com.ramadan.islami.data.model

import com.google.gson.annotations.SerializedName

data class Calender(
    val code: Int,
    @SerializedName("data")
    val `data`: CalenderData,
    val status: String,
)

data class CalenderData(
    val gregorian: Gregorian,
    val hijri: Hijri,
)

data class Gregorian(
    val date: String,
    val day: String,
    val month: Month,
    val weekday: Weekday,
    val year: String,
)

data class Hijri(
    val date: String,
    val day: String,
    val holidays: List<Any>,
    val month: MonthX,
    val weekday: WeekdayX,
    val year: String,
)

data class Month(
    val en: String,
    val number: Int,
)

data class Weekday(
    val en: String,
)

data class MonthX(
    val ar: String,
    val en: String,
    val number: Int,
)

data class WeekdayX(
    val ar: String,
    val en: String,
)