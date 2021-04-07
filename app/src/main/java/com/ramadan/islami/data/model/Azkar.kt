package com.ramadan.islami.data.model

import androidx.annotation.Keep

class Azkar : ArrayList<Azkar.AzkarItem>() {
    @Keep
    data class AzkarItem(
        val category: String,
        val description: String,
        val zekr: String,
    )
}
