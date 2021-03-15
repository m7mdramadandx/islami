package com.ramadan.islami.data.model

class Verse : ArrayList<Verse.VerseItem>() {
    data class VerseItem(
        val surah: String,
        val ayah: String,
        val translation: String,
    )
}