package com.ramadan.islami.data.api

import AuthenticationInterceptor
import com.ramadan.islami.utils.lat
import com.ramadan.islami.utils.lon
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    //    private const val BASE_URL = "https://run.mocky.io/v3/6cada7f7-aa0a-40a4-a783-8e2da9027ff1/"
//    private const val BASE_URL = "https://api.mocki.io/v1/82615eca/"
    private const val BASE_URL =
        "https://www.islamicfinder.us/index.php/api/prayer_times?country=EGY&latitude=$lat&longitude=$lon&timezone=EET&method=5?/"
    private const val BASE_URL0 = "https://api.aladhan.com/v1/hToG?date=06-07-1442/"
    private const val BASE_URL1 =
        "https://api.aladhan.com/v1/calendar?latitude=31.107364&longitude=29.783520&method=5/"
    private const val BASE_URL2 =
        "https://api.pray.zone/v2/times/today.json?longitude=29.783520&latitude=31.107364&elevation=333/"
    private const val BASE_URL3 =
        "https://aladhan.p.rapidapi.com/calendarByCity?city=Alexandria%2C%20Egypt&country=Egypt&method=5?/"

//            .baseUrl("https://sandbox-api.coinmarketcap.com/")

    private fun getRetrofit(): Retrofit {
        val builder = OkHttpClient.Builder()
        builder.interceptors().add(AuthenticationInterceptor())
        val client = builder.build()

        return Retrofit.Builder()
            .baseUrl("https://sandbox-api.coinmarketcap.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(client)
            .build()
//            .create(ApiService::class.java)
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)

}