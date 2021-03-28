package com.ramadan.islami.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestoreException
import com.ramadan.islami.data.listener.FirebaseListener
import com.ramadan.islami.data.model.Quote
import com.ramadan.islami.data.model.Story
import com.ramadan.islami.data.model.Topic
import com.ramadan.islami.data.model.Video
import com.ramadan.islami.data.repo.FirebaseRepository
import kotlinx.coroutines.*
import com.ramadan.islami.data.model.Collection as ModelCollection

class FirebaseViewModel : ViewModel() {
    private val repo by lazy { FirebaseRepository() }
    var firebaseListener: FirebaseListener? = null

    fun fetchSuggestion(language: String): LiveData<MutableList<Story>> {
        firebaseListener?.onStarted()
        val mutableData = MutableLiveData<MutableList<Story>>()
        GlobalScope.launch(Dispatchers.IO) {
            delay(300)
            withContext(Dispatchers.Main) {
                repo.fetchSuggestion(language).observeForever { mutableData.value = it }
                if (mutableData.value!!.isNotEmpty()) firebaseListener?.onSuccess()
                else firebaseListener?.onFailure("Try Again ")
            }
        }
        return mutableData
    }

    fun fetchStories(language: String): LiveData<MutableList<Story>> {
        firebaseListener?.onStarted()
        val mutableData = MutableLiveData<MutableList<Story>>()
        GlobalScope.launch(Dispatchers.IO) {
            delay(300)
            withContext(Dispatchers.Main) {
                repo.fetchAllStories(language).observeForever { mutableData.value = it }
                if (mutableData.value!!.isNotEmpty()) firebaseListener?.onSuccess()
                else firebaseListener?.onFailure("Try Again ")
            }
        }
        return mutableData
    }

    suspend fun fetchStory(language: String, storyID: String): Story {
        firebaseListener?.onStarted()
        val story = repo.fetchStory(language, storyID)
        if (story.text.isNotEmpty()) firebaseListener?.onSuccess()
        else firebaseListener?.onFailure("Try Again ")
        return story
    }

    fun fetchQuotes(language: String): LiveData<MutableList<Quote>> {
        firebaseListener?.onStarted()
        val mutableData = MutableLiveData<MutableList<Quote>>()
        GlobalScope.launch(Dispatchers.IO) {
            delay(300)
            withContext(Dispatchers.Main) {
                repo.fetchQuotes(language).observeForever { mutableData.value = it }
                if (mutableData.value!!.isNotEmpty()) firebaseListener?.onSuccess()
                else firebaseListener?.onFailure("Try Again ")
            }
        }
        return mutableData
    }

    suspend fun fetchHadiths(language: String): Quote {
        firebaseListener?.onStarted()
        val quote = repo.fetchHadiths(language)
        if (quote.hadiths.isNotEmpty()) firebaseListener?.onSuccess()
        else firebaseListener?.onFailure("Try Again ")
        return quote
    }

    fun fetchCollection(language: String): LiveData<MutableList<ModelCollection>> {
        firebaseListener?.onStarted()
        val mutableData = MutableLiveData<MutableList<ModelCollection>>()
        GlobalScope.launch(Dispatchers.IO) {
            delay(300)
            withContext(Dispatchers.Main) {
                repo.fetchCollection(language).observeForever { mutableData.value = it }
                if (mutableData.value!!.isNotEmpty()) firebaseListener?.onSuccess()
                else firebaseListener?.onFailure("Try Again ")
            }
        }
        return mutableData
    }

    fun fetchVideos(language: String): LiveData<MutableList<Video>> {
        firebaseListener?.onStarted()
        val mutableData = MutableLiveData<MutableList<Video>>()
        GlobalScope.launch(Dispatchers.IO) {
            delay(300)
            withContext(Dispatchers.Main) {
                repo.fetchVideos(language)
                    .observeForever { videosList -> mutableData.value = videosList }
                if (mutableData.value!!.isNotEmpty()) firebaseListener?.onSuccess()
                else firebaseListener?.onFailure("Try Again ")
            }
        }
        return mutableData
    }

    suspend fun fetchVideo(language: String, collectionID: String, documentId: String): Video {
        firebaseListener?.onStarted()
        val video = repo.fetchVideo(language, collectionID, documentId)
        if (video.id.isNotEmpty()) firebaseListener?.onSuccess()
        else firebaseListener?.onFailure("Try Again ")
        return video
    }

    fun fetchTopics(language: String, topic: String): MutableLiveData<MutableList<Topic>> {
        firebaseListener?.onStarted()
        val mutableData = MutableLiveData<MutableList<Topic>>()
        GlobalScope.launch(Dispatchers.IO) {
            delay(300)
            withContext(Dispatchers.Main) {
                repo.fetchTopics(language, topic)
                    .observeForever { mutableData.value = it }
                if (mutableData.value!!.isNotEmpty()) firebaseListener?.onSuccess()
                else firebaseListener?.onFailure("Try Again ")
            }
        }
        return mutableData
    }

    suspend fun fetchTopic(language: String, collectionID: String, documentId: String): Topic {
        firebaseListener?.onStarted()
        val topic = repo.fetchTopic(language, collectionID, documentId)
        if (topic.id.isNotEmpty()) firebaseListener?.onSuccess()
        else firebaseListener?.onFailure("Try Again ")
        return topic
    }

    fun rateTopic(language: String, collectionID: String, topicID: String, isGood: Boolean) {
        firebaseListener?.onStarted()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                repo.rateTopic(language, collectionID, topicID, isGood)
                firebaseListener?.onSuccess()
            } catch (e: FirebaseFirestoreException) {
                firebaseListener?.onFailure(e.message!!)
            }
        }
    }

    fun sendFeedback(msg: String) {
        firebaseListener?.onStarted()
        GlobalScope.launch(Dispatchers.IO) {
            delay(1000)
            withContext(Dispatchers.Main) {
                try {
                    repo.sendFeedback(msg)
                    firebaseListener?.onSuccess()
                } catch (e: FirebaseFirestoreException) {
                    firebaseListener?.onFailure(e.localizedMessage!!)
                }
            }
        }
    }

}