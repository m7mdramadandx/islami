package com.ramadan.islami.data.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
class Quote(
    val id: String,
    val title: String,
    val image: String,
    val verses: ArrayList<String>,
    val hadiths: ArrayList<String>,
) : Serializable
