package com.ramadan.islami.data.model

import androidx.annotation.Keep

@Keep
class AllahNames : ArrayList<AllahNames.AllahNamesItem>() {
    data class AllahNamesItem(
        val name: String,
        val english: String,
    )
}
