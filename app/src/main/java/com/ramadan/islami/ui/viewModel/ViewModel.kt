package com.ramadan.islami.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestoreException
import com.ramadan.islami.data.model.Category
import com.ramadan.islami.data.model.Prophet
import com.ramadan.islami.data.model.Quote
import com.ramadan.islami.data.model.Video
import com.ramadan.islami.data.repo.Repository
import com.ramadan.islami.utils.defaultImg
import kotlinx.coroutines.*

class ViewModel : ViewModel() {
    private val repo = Repository()
    var listener: Listener? = null

    fun fetchAllStories(isEnglish: Boolean): LiveData<MutableList<Prophet>> {
        listener?.onStarted()
        val mutableData = MutableLiveData<MutableList<Prophet>>()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                repo.fetchAllStories(isEnglish)
                    .observeForever { prophetList -> mutableData.value = prophetList }
                if (mutableData.value!!.isNotEmpty()) listener?.onSuccess()
                else listener?.onFailure("Failure")
            }
        }
        return mutableData
    }

    fun fetchVideos(isEnglish: Boolean): LiveData<MutableList<Video>> {
        listener?.onStarted()
        val mutableData = MutableLiveData<MutableList<Video>>()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                repo.fetchVideos(isEnglish)
                    .observeForever { videosList -> mutableData.value = videosList }
                if (mutableData.value!!.isNotEmpty()) listener?.onSuccess()
                else listener?.onFailure("Failure")
            }
        }
        return mutableData
    }

    fun fetchStory(isEnglish: Boolean, prophetName: String): Prophet {
        listener?.onStarted()
        val prophet = Prophet("", "", defaultImg, ArrayList(0))
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                repo.fetchStory(isEnglish, prophetName)
                if (prophet.text.isNotEmpty()) listener?.onSuccess()
                else listener?.onFailure("Failure")
            }
        }
        return prophet
    }

    fun fetchCategory(isEnglish: Boolean): LiveData<MutableList<Category>> {
        listener?.onStarted()
        val mutableData = MutableLiveData<MutableList<Category>>()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                repo.fetchCategories(isEnglish)
                    .observeForever { categoryList -> mutableData.value = categoryList }
                if (mutableData.value!!.isNotEmpty()) listener?.onSuccess()
                else listener?.onFailure("Failure")
            }
        }
        return mutableData
    }

    suspend fun fetchQuote(isEnglish: Boolean, category: String): Quote {
        listener?.onStarted()
        val quote = repo.fetchQuote(isEnglish, category)
        if (quote.verses.isNotEmpty()) listener?.onSuccess()
        else listener?.onFailure("Failure")
        return quote
    }

    suspend fun fetchHadiths(isEnglish: Boolean): Quote {
        listener?.onStarted()
        val quote = repo.fetchHadiths(isEnglish)
        if (quote.hadiths.isNotEmpty()) listener?.onSuccess()
        else listener?.onFailure("Failure")
        return quote
    }

    fun sendFeedback(msg: String) {
        listener?.onStarted()
        try {
            repo.sendFeedback(msg)
            listener?.onSuccess()
        } catch (e: FirebaseFirestoreException) {
            listener?.onFailure(e.localizedMessage!!)
        }
    }

}