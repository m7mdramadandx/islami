package com.ramadan.islami.data.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
class Topic(
    val id: String,
    val title: String,
    val brief: String,
    val source: String,
    val rate: Double,
    val content: Map<String, String>,
) : Serializable
