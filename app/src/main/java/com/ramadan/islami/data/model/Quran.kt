package com.ramadan.islami.data.model

import java.io.Serializable

class Quran(
    val code: Int,
    val `data`: Data,
    val status: String,
) : Serializable {
    class Data(
        val edition: Edition,
        val surahs: List<Surah>,
    )

    class Edition(
        val englishName: String,
        val format: String,
        val identifier: String,
        val language: String,
        val name: String,
        val type: String,
    )

    class Ayah(
        val hizbQuarter: Int,
        val juz: Int,
        val manzil: Int,
        val number: Int,
        val numberInSurah: Int,
        val page: Int,
        val ruku: Int,
        val sajda: Any,
        val text: String,
    )

}

class Surah(
    val ayahs: List<Quran.Ayah>,
    val englishName: String,
    val englishNameTranslation: String,
    val name: String,
    val number: Int,
    val revelationType: String,
) : Serializable

