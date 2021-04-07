package com.ramadan.islami.data.model

import androidx.annotation.Keep

class AllahNames : ArrayList<AllahNames.AllahNamesItem>() {
    @Keep
    data class AllahNamesItem(
        val name: String,
        val english: String,
    )
}
