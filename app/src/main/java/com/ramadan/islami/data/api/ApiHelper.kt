package com.ramadan.islami.data.api


class ApiHelper(private val apiService: ApiService) {

    fun getPrayers() = apiService.getAllCurrencies("EUR")
}