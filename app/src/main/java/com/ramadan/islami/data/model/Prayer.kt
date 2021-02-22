package com.ramadan.islami.data.model

import com.google.gson.annotations.SerializedName


data class Prayer(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<PrayerData>,
    @SerializedName("status")
    val status: String,
)

data class PrayerData(
    @SerializedName("date")
    val date: PrayerDate,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("timings")
    val timings: Timings,
)

data class PrayerDate(
    val gregorian: Gregorian,
    val hijri: Hijri,
    val readable: String,
    val timestamp: String,
)

data class Meta(
    val latitude: Double,
    val latitudeAdjustmentMethod: String,
    val longitude: Double,
    val method: Method,
    val midnightMode: String,
    val school: String,
    val timezone: String,
)

data class Timings(
    @SerializedName("Asr")
    val asr: String,
    @SerializedName("Dhuhr")
    val dhuhr: String,
    @SerializedName("Fajr")
    val fajr: String,
    @SerializedName("Imsak")
    val imsak: String,
    @SerializedName("Isha")
    val isha: String,
    @SerializedName("Maghrib")
    val maghrib: String,
    @SerializedName("Midnight")
    val midnight: String,
    @SerializedName("Sunrise")
    val sunrise: String,
    @SerializedName("Sunset")
    val sunset: String,
)


data class Method(
    val id: Int,
    val name: String,
    val params: Params,
)

data class Params(
    val fajr: Double,
    val isha: Double,
)