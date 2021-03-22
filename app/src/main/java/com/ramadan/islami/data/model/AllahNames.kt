package com.ramadan.islami.data.model

class AllahNames : ArrayList<AllahNames.AllahNamesItem>() {
    data class AllahNamesItem(
        val name: String,
        val english: String,
    )
}
