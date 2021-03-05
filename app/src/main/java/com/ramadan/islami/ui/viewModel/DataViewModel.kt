package com.ramadan.islami.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestoreException
import com.ramadan.islami.data.listener.DataListener
import com.ramadan.islami.data.model.Quote
import com.ramadan.islami.data.model.Story
import com.ramadan.islami.data.model.Topic
import com.ramadan.islami.data.model.Video
import com.ramadan.islami.data.repo.Repository
import kotlinx.coroutines.*
import com.ramadan.islami.data.model.Collection as ModelCollection

class DataViewModel : ViewModel() {
    private val repo by lazy { Repository() }
    var dataListener: DataListener? = null

    fun fetchSuggestion(language: String): LiveData<MutableList<Story>> {
        dataListener?.onStarted()
        val mutableData = MutableLiveData<MutableList<Story>>()
        GlobalScope.launch(Dispatchers.IO) {
            delay(300)
            withContext(Dispatchers.Main) {
                repo.fetchSuggestion(language).observeForever { mutableData.value = it }
                if (mutableData.value!!.isNotEmpty()) dataListener?.onSuccess()
                else dataListener?.onFailure("Try Again ")
            }
        }
        return mutableData
    }

    fun fetchStories(language: String): LiveData<MutableList<Story>> {
        dataListener?.onStarted()
        val mutableData = MutableLiveData<MutableList<Story>>()
        GlobalScope.launch(Dispatchers.IO) {
            delay(300)
            withContext(Dispatchers.Main) {
                repo.fetchAllStories(language).observeForever { mutableData.value = it }
                if (mutableData.value!!.isNotEmpty()) dataListener?.onSuccess()
                else dataListener?.onFailure("Try Again ")
            }
        }
        return mutableData
    }

    suspend fun fetchStory(language: String, storyID: String): Story {
        dataListener?.onStarted()
        val story = repo.fetchStory(language, storyID)
        if (story.text.isNotEmpty()) dataListener?.onSuccess()
        else dataListener?.onFailure("Try Again ")
        return story
    }

    fun fetchQuotes(language: String): LiveData<MutableList<Quote>> {
        dataListener?.onStarted()
        val mutableData = MutableLiveData<MutableList<Quote>>()
        GlobalScope.launch(Dispatchers.IO) {
            delay(300)
            withContext(Dispatchers.Main) {
                repo.fetchQuotes(language).observeForever { mutableData.value = it }
                if (mutableData.value!!.isNotEmpty()) dataListener?.onSuccess()
                else dataListener?.onFailure("Try Again ")
            }
        }
        return mutableData
    }

    suspend fun fetchHadiths(language: String): Quote {
        dataListener?.onStarted()
        val quote = repo.fetchHadiths(language)
        if (quote.hadiths.isNotEmpty()) dataListener?.onSuccess()
        else dataListener?.onFailure("Try Again ")
        return quote
    }

    fun fetchCollection(language: String): LiveData<MutableList<ModelCollection>> {
        dataListener?.onStarted()
        val mutableData = MutableLiveData<MutableList<ModelCollection>>()
        GlobalScope.launch(Dispatchers.IO) {
            delay(300)
            withContext(Dispatchers.Main) {
                repo.fetchCollection(language).observeForever { mutableData.value = it }
                if (mutableData.value!!.isNotEmpty()) dataListener?.onSuccess()
                else dataListener?.onFailure("Try Again ")
            }
        }
        return mutableData
    }

    fun fetchVideos(language: String): LiveData<MutableList<Video>> {
        dataListener?.onStarted()
        val mutableData = MutableLiveData<MutableList<Video>>()
        GlobalScope.launch(Dispatchers.IO) {
            delay(300)
            withContext(Dispatchers.Main) {
                repo.fetchVideos(language)
                    .observeForever { videosList -> mutableData.value = videosList }
                if (mutableData.value!!.isNotEmpty()) dataListener?.onSuccess()
                else dataListener?.onFailure("Try Again ")
            }
        }
        return mutableData
    }

    fun fetchTopics(language: String, topic: String): MutableLiveData<MutableList<Topic>> {
        dataListener?.onStarted()
        val mutableData = MutableLiveData<MutableList<Topic>>()
        GlobalScope.launch(Dispatchers.IO) {
            delay(300)
            withContext(Dispatchers.Main) {
                repo.fetchTopics(language, topic)
                    .observeForever { mutableData.value = it }
                if (mutableData.value!!.isNotEmpty()) dataListener?.onSuccess()
                else dataListener?.onFailure("Try Again ")
            }
        }
        return mutableData
    }

    suspend fun fetchTopic(language: String, collectionID: String, documentId: String): Topic {
        dataListener?.onStarted()
        val topic = repo.fetchTopic(language, collectionID, documentId)
        if (topic.id.isNotEmpty()) dataListener?.onSuccess()
        else dataListener?.onFailure("Try Again ")
        return topic
    }

    fun rateTopic(language: String, collectionID: String, topicID: String, isGood: Boolean) {
        dataListener?.onStarted()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                repo.rateTopic(language, collectionID, topicID, isGood)
                dataListener?.onSuccess()
            } catch (e: FirebaseFirestoreException) {
                dataListener?.onFailure(e.message!!)
            }
        }
    }

    fun sendFeedback(msg: String) {
        dataListener?.onStarted()
        GlobalScope.launch(Dispatchers.IO) {
            delay(1000)
            withContext(Dispatchers.Main) {
                try {
                    repo.sendFeedback(msg)
                    dataListener?.onSuccess()
                } catch (e: FirebaseFirestoreException) {
                    dataListener?.onFailure(e.localizedMessage!!)
                }
            }
        }
    }

}