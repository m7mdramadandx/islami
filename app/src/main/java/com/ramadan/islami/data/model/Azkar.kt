package com.ramadan.islami.data.model

class Azkar : ArrayList<Azkar.AzkarItem>() {
    data class AzkarItem(
        val category: String,
        val count: String,
        val description: String,
        val reference: String,
        val zekr: String,
    )
}
