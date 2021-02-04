package com.ramadan.islami.data.model

import java.io.Serializable


class Quote(
    val id: String,
    val title: String,
    val image: String,
    val verses: ArrayList<String>,
    val hadiths: ArrayList<String>,
) : Serializable
