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
    @SerializedName("gregorian")
    val gregorian: Gregorian,
    @SerializedName("hijri")
    val hijri: Hijri,
    @SerializedName("readable")
    val readable: String,
    @SerializedName("timestamp")
    val timestamp: String,
)

data class Meta(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("latitudeAdjustmentMethod")
    val latitudeAdjustmentMethod: String,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("method")
    val method: Method,
    @SerializedName("midnightMode")
    val midnightMode: String,
    @SerializedName("school")
    val school: String,
    @SerializedName("timezone")
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
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("params")
    val params: Params,
)

data class Params(
    @SerializedName("Fajr")
    val fajr: Double,
    @SerializedName("Isha")
    val isha: Double,
)