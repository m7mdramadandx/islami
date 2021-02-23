package com.ramadan.islami.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ramadan.islami.data.repo.MainRepository
import com.ramadan.islami.utils.Resource
import kotlinx.coroutines.Dispatchers

class ApiViewModel(private val mainRepository: MainRepository) : ViewModel() {

//    fun allahNames() = liveData(Dispatchers.Main) {
//        emit(Resource.loading(null))
//        try {
//            emit(Resource.success(mainRepository.allahNames()))
//        } catch (exception: Exception) {
//            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
//        }
//    }
    fun allahNames() = liveData(Dispatchers.Main) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.allahNames()))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }

    fun gregorianCalender() = liveData(Dispatchers.Main) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.gregorianCalender()))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }

    fun hijriCalender() = liveData(Dispatchers.Main) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.hijriCalender()))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }

    fun fetchPrayers() = liveData(Dispatchers.Main) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.fetchPrayers()))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }

    fun hadithOfDay() = liveData(Dispatchers.Main) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.hadithOfDay()))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }
}