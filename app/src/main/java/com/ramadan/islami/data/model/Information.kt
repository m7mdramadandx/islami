package com.ramadan.islami.data.model

import java.io.Serializable


class Information(
    val id: String,
    val title: String,
    val brief: String,
    val image: String,
    val content: Map<String, String>,
) : Serializable
