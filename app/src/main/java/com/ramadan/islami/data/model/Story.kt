package com.ramadan.islami.data.model

import androidx.annotation.Keep
import java.io.Serializable


@Keep
class Story(
    val id: String,
    val title: String,
    val image: String,
    val brief: String,
    val text: ArrayList<String>,
) : Serializable
