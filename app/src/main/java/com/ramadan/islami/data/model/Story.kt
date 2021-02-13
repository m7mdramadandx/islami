package com.ramadan.islami.data.model

import java.io.Serializable


class Story(
    val id: String,
    val title: String,
    val imgUrl: String,
    val brief: String,
    val text: ArrayList<String>,
) : Serializable
