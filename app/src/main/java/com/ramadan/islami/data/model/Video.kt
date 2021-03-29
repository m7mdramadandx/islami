package com.ramadan.islami.data.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
class Video(
    val id: String,
    val title: String,
    val videosID: ArrayList<String>,
) : Serializable