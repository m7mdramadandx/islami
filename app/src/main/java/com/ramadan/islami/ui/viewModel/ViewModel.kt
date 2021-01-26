package com.ramadan.islami.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ramadan.islami.data.model.Category
import com.ramadan.islami.data.model.Prophet
import com.ramadan.islami.data.model.Quote
import com.ramadan.islami.data.model.Video
import com.ramadan.islami.data.repo.Repository
import com.ramadan.islami.utils.defaultImg
import kotlinx.coroutines.*

class ViewModel : ViewModel() {
    private val repo = Repository()

    fun fetchAllStories(isEnglish: Boolean): LiveData<MutableList<Prophet>> {
        val mutableData = MutableLiveData<MutableList<Prophet>>()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                repo.fetchAllStories(isEnglish)
                    .observeForever { prophetList -> mutableData.value = prophetList }
            }
        }
        return mutableData
    }

    fun fetchVideos(isEnglish: Boolean): LiveData<MutableList<Video>> {
        val mutableData = MutableLiveData<MutableList<Video>>()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                repo.fetchVideos(isEnglish)
                    .observeForever { videosList -> mutableData.value = videosList }
            }
        }
        return mutableData
    }

    fun fetchStory(isEnglish: Boolean, prophetName: String): Prophet {
        val prophet = Prophet("", "", defaultImg, ArrayList(0))
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                repo.fetchStory(isEnglish, prophetName)
            }
        }
        return prophet
    }

    fun fetchCategory(isEnglish: Boolean): LiveData<MutableList<Category>> {
        val mutableData = MutableLiveData<MutableList<Category>>()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                repo.fetchCategories(isEnglish)
                    .observeForever { categoryList -> mutableData.value = categoryList }
            }
        }
        return mutableData
    }

    suspend fun fetchQuote(isEnglish: Boolean, category: String): Quote {
        return repo.getQuote(isEnglish, category)
    }

    fun sendFeedback(msg: String) {
        repo.sendFeedback(msg)
    }

}