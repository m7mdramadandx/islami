package com.ramadan.islami.data.model

data class Quran(
    val code: Int,
    val `data`: Data,
    val status: String,

    ) {

    data class Data(
        val edition: Edition,
        val surahs: List<Surah>,
    )

    data class Edition(
        val englishName: String,
        val format: String,
        val identifier: String,
        val language: String,
        val name: String,
        val type: String,
    )

    data class Surah(
        val ayahs: List<Ayah>,
        val englishName: String,
        val englishNameTranslation: String,
        val name: String,
        val number: Int,
        val revelationType: String,
    )

    data class Ayah(
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
