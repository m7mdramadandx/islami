package com.ramadan.islami.data.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
class Quran(
    val code: Int,
    val `data`: Data,
    val status: String,
) : Serializable {
    @Keep
    class Data(
        val surahs: List<Surah>,
    )

    class Ayah(
        val hizbQuarter: Int,
        val juz: String,
        val manzil: Int,
        val number: Int,
        val numberInSurah: Int,
        val page: Int,
        val ruku: Int,
        val sajda: Any,
        val text: String,
        val tafseer: String,
    )
}

