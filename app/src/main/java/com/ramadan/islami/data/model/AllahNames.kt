package com.ramadan.islami.data.model

data class AllahNames(
    val code: Int,
    val `data`: List<Data>,
    val status: String,
) {
    data class Data(
        val en: En,
        val name: String,
        val number: Int,
        val transliteration: String,
    ) {
        data class En(
            val meaning: String,
        )

    }

}

