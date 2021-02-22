package com.ramadan.islami.data.model

data class Hadith(
    val bookNumber: String,
    val chapterId: String,
    val collection: String,
    val hadith: List<HadithX>,
    val hadithNumber: String,
) {
    data class HadithX(
        val body: String,
        val chapterNumber: String,
        val chapterTitle: String,
        val grades: List<Any>,
        val lang: String,
        val urn: Int,
    )
}

