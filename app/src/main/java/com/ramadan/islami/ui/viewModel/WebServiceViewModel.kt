package com.ramadan.islami.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ramadan.islami.data.repo.WebServiceRepository
import com.ramadan.islami.utils.Resource
import kotlinx.coroutines.Dispatchers

class WebServiceViewModel(private val webServiceRepository: WebServiceRepository) : ViewModel() {

//    fun allahNames() = liveData(Dispatchers.Main) {
//        emit(Resource.loading(null))
//        try {
//            emit(Resource.success(webServiceRepository.allahNames()))
//        } catch (exception: Exception) {
//            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
//        }
//    }

    fun gregorianCalender(dateOfDay: String) = liveData(Dispatchers.Main) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(webServiceRepository.gregorianCalender(dateOfDay)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }

    fun hijriCalender(dateOfDay: String) = liveData(Dispatchers.Main) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(webServiceRepository.hijriCalender(dateOfDay)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }

    fun fetchPrayers(lat: Double, lon: Double) = liveData(Dispatchers.Main) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(webServiceRepository.fetchPrayers(lat, lon)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }

    fun hadithOfDay() = liveData(Dispatchers.Main) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(webServiceRepository.hadithOfDay()))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }
}