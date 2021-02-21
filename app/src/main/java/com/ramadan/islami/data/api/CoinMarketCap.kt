package com.ramadan.islami.data.api

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Data class to handle the response from the server.
 */
data class CoinMarketCap<Type>(
    val status: Status?,
    val data: Type?,
    val statusCode: Int?,
    val error: String?,
    val message: String?,
) {

    data class Status(
        val timestamp: Date,
        @SerializedName("error_code")
        val errorCode: Int,
        @SerializedName("error_message")
        val errorMessage: String,
        val elapsed: Int,
        @SerializedName("credit_count")
        val creditCount: Int,
    )
}
