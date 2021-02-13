package com.ramadan.islami.data.model

import java.io.Serializable

class Topic(
    val id: String,
    val title: String,
    val brief: String,
    val image: String,
    val source: String,
    val rate: Double,
    val content: Map<String, String>,
) : Serializable
