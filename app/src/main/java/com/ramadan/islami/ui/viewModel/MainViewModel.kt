package com.ramadan.islami.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ramadan.islami.data.repo.MainRepository
import com.ramadan.islami.utils.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getPrayers() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getPrayers()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}