package com.ramadan.islami.data.api

import androidx.lifecycle.LiveData
import com.ramadan.islami.data.model.CurrenciesLatest
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
//    @Headers(
//        "x-rapidapi-key: ff73aa9784msh863eecbf81de47cp17a05fjsned709bd1b164",
//        "x-rapidapi-host: aladhan.p.rapidapi.com"
//    )
//    @GET("{result}/")
//    suspend fun getPrayers(): Prayer


    // baseUrl + v1/cryptocurrency/listings/latest + query parameter.
    // Annotation @Query is used to define query parameter for request. Finally the request url will
    // look like that https://sandbox-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?convert=EUR.
//    @GET("v6/a48e48e01f6aded2534dd59f/latest/EGP")
    @GET("v1/cryptocurrency/listings/latest")
//    fun getAllCurrencies(): Call<Prayer>
    fun getAllCurrencies(@Query("convert") currency: String): LiveData<ApiResponse<CoinMarketCap<List<CurrenciesLatest>>>>


}