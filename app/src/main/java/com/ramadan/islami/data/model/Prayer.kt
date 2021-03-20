package com.ramadan.islami.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Prayer(
    val code: Int,
    val `data`: List<PrayerData>,
    val status: String,
) : Serializable {
    data class PrayerData(
        val date: PrayerDate,
        val meta: Meta,
        val timings: Timings,
    ) : Serializable

    data class PrayerDate(
        val gregorian: Gregorian,
        val hijri: Hijri,
        val readable: String,
        val timestamp: String,
    ) : Serializable

    data class Meta(
        val latitude: Double,
        val latitudeAdjustmentMethod: String,
        val longitude: Double,
        val method: Method,
        val midnightMode: String,
        val school: String,
        val timezone: String,
    ) : Serializable

    data class Timings(
        @SerializedName("Asr")
        val asr: String,

        @SerializedName("Dhuhr")
        val dhuhr: String = String().plus("-----"),

        @SerializedName("Fajr")
        val fajr: String = String().removeSuffix("(EET)"),

        @SerializedName("Imsak")
        val imsak: String = String().removeSuffix("(EET)"),

        @SerializedName("Isha")
        val isha: String = String().removeSuffix("(EET)"),

        @SerializedName("Maghrib")
        val maghrib: String = String().removeSuffix("(EET)"),

        @SerializedName("Midnight")
        val midnight: String = String().removeSuffix("(EET)"),

        @SerializedName("Sunrise")
        val sunrise: String = String().removeSuffix("(EET)"),

        @SerializedName("Sunset")
        val sunset: String = String().removeSuffix("(EET)"),
    ) : Serializable

    data class Method(
        val id: Int,
        val name: String,
    ) : Serializable
}


