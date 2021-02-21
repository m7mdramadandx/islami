package com.ramadan.islami.data.model

import com.google.gson.annotations.SerializedName

data class CurrenciesLatest(
    val status: Status,
    val data: List<Data>,
) {
    data class Data(
        val id: Int,
        val name: String,
        val symbol: String,
        val slug: String,
        // The annotation to a model property lets you pass the serialized and deserialized
        // name as a string. This is useful if you don't want your model class and the JSON
        // to have identical naming.
        @SerializedName("circulating_supply")
        val circulatingSupply: Double,
        @SerializedName("total_supply")
        val totalSupply: Double,
        @SerializedName("max_supply")
        val maxSupply: Double,
        @SerializedName("date_added")
        val dateAdded: String,
        @SerializedName("num_market_pairs")
        val numMarketPairs: Int,
        @SerializedName("cmc_rank")
        val cmcRank: Int,
        @SerializedName("last_updated")
        val lastUpdated: String,
        val quote: Quote,
    ) {

        data class Quote(
            // For additional option during deserialization you can specify value or alternative
            // values. Gson will check the JSON for all names we specify and try to find one to
            // map it to the annotated property.
            @SerializedName(value = "USD", alternate = ["AUD", "BRL", "CAD", "CHF", "CLP",
                "CNY", "CZK", "DKK", "EUR", "GBP", "HKD", "HUF", "IDR", "ILS", "INR", "JPY",
                "KRW", "MXN", "MYR", "NOK", "NZD", "PHP", "PKR", "PLN", "RUB", "SEK", "SGD",
                "THB", "TRY", "TWD", "ZAR"])
            val currency: Currency,
        ) {

            data class Currency(
                val price: Double,
                @SerializedName("volume_24h")
                val volume24h: Double,
                @SerializedName("percent_change_1h")
                val percentChange1h: Double,
                @SerializedName("percent_change_24h")
                val percentChange24h: Double,
                @SerializedName("percent_change_7d")
                val percentChange7d: Double,
                @SerializedName("market_cap")
                val marketCap: Double,
                @SerializedName("last_updated")
                val lastUpdated: String,
            )
        }
    }

    data class Status(
        val timestamp: String,
        @SerializedName("error_code")
        val errorCode: Int,
        @SerializedName("error_message")
        val errorMessage: String,
        val elapsed: Int,
        @SerializedName("credit_count")
        val creditCount: Int,
    )
}