package com.ramadan.islami.data.model

import androidx.annotation.Keep

@Keep
class Verse : ArrayList<Verse.VerseItem>() {
    data class VerseItem(
        val surah: String,
        val ayah: String,
        val translation: String,
    )
}