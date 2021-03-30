package com.ramadan.islami.data.model

import androidx.annotation.Keep

@Keep
class Azkar : ArrayList<Azkar.AzkarItem>() {
    data class AzkarItem(
        val category: String,
        val description: String,
        val zekr: String,
    )
}
