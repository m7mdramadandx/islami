package com.ramadan.islami.data.api


class ApiHelper(private val apiService: ApiService) {
    suspend fun allahNames() = apiService.allahNames()
    suspend fun gregorianCalender(dateOfDay: String) = apiService.gregorianCalender(dateOfDay)
    suspend fun hijriCalender(dateOfDay: String) = apiService.hijriCalender(dateOfDay)
    suspend fun fetchPrayers(lat: Double, lon: Double) = apiService.fetchPrayers(lat, lon)
    suspend fun hadithOfDay() = apiService.hadithOfDay()
}