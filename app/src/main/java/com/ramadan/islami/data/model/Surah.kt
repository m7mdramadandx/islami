package com.ramadan.islami.data.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Surah(
    val ayahs: List<Quran.Ayah>,
    val englishName: String,
    val englishNameTranslation: String,
    val name: String,
    val number: Int,
    val revelationType: String,
) : Serializable