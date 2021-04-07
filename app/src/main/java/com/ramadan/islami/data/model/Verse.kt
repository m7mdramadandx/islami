package com.ramadan.islami.data.model

import androidx.annotation.Keep


class Verse : ArrayList<Verse.VerseItem>() {
    @Keep
    data class VerseItem(
        val surah: String,
        val ayah: String,
        val translation: String,
    )
}