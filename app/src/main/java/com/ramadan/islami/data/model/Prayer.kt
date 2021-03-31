package com.ramadan.islami.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class Prayer(
    val code: Int,
    val `data`: List<PrayerData>,
    val status: String,
) : Serializable {
    @Keep
    data class PrayerData(
        val date: PrayerDate,
        val meta: Meta,
        val timings: Timings,
    ) : Serializable

    @Keep
    data class PrayerDate(
        val gregorian: Gregorian,
        val hijri: Hijri,
        val readable: String,
        val timestamp: String,
    ) : Serializable

    @Keep
    data class Meta(
        val latitude: Double,
        val latitudeAdjustmentMethod: String,
        val longitude: Double,
        val method: Method,
        val midnightMode: String,
        val school: String,
        val timezone: String,
    ) : Serializable

    @Keep
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
    ) : Serializable

    @Keep
    data class Method(
        val id: Int,
        val name: String,
    ) : Serializable
}


