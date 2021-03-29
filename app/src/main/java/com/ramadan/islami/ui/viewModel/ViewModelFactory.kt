package com.ramadan.islami.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ramadan.islami.data.api.ApiHelper
import com.ramadan.islami.data.repo.WebServiceRepository

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WebServiceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WebServiceViewModel(WebServiceRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}

