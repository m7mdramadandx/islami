package com.ramadan.islami.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestoreException
import com.ramadan.islami.data.model.Quote
import com.ramadan.islami.data.model.Story
import com.ramadan.islami.data.model.Topic
import com.ramadan.islami.data.model.Video
import com.ramadan.islami.data.repo.Repository
import kotlinx.coroutines.*
import com.ramadan.islami.data.model.Collection as ModelCollection

class ViewModel : ViewModel() {

    fun fetchSuggestion(isEnglish: Boolean): LiveData<MutableList<Story>> {
        listener?.onStarted()
        val mutableData = MutableLiveData<MutableList<Story>>()
        GlobalScope.launch(Dispatchers.IO) {
            delay(300)
            withContext(Dispatchers.Main) {
                repo.fetchSuggestion(isEnglish).observeForever { mutableData.value = it }
                if (mutableData.value!!.isNotEmpty()) listener?.onSuccess()
                else listener?.onFailure("Try Again ")
            }
        }
        return mutableData
    }

    fun fetchStories(isEnglish: Boolean): LiveData<MutableList<Story>> {
        listener?.onStarted()
        val mutableData = MutableLiveData<MutableList<Story>>()
        GlobalScope.launch(Dispatchers.IO) {
            delay(300)
            withContext(Dispatchers.Main) {
                repo.fetchAllStories(isEnglish).observeForever { mutableData.value = it }
                if (mutableData.value!!.isNotEmpty()) listener?.onSuccess()
                else listener?.onFailure("Try Again ")
            }
        }
        return mutableData
    }

    suspend fun fetchStory(isEnglish: Boolean, storyName: String): Story {
        listener?.onStarted()
        val story = repo.fetchStory(isEnglish, storyName)
        if (story.text.isNotEmpty()) listener?.onSuccess()
        else listener?.onFailure("Try Again ")
        return story
    }

    fun fetchQuotes(isEnglish: Boolean): LiveData<MutableList<Quote>> {
        listener?.onStarted()
        val mutableData = MutableLiveData<MutableList<Quote>>()
        GlobalScope.launch(Dispatchers.IO) {
            delay(300)
            withContext(Dispatchers.Main) {
                repo.fetchQuotes(isEnglish).observeForever { mutableData.value = it }
                if (mutableData.value!!.isNotEmpty()) listener?.onSuccess()
                else listener?.onFailure("Try Again ")
            }
        }
        return mutableData
    }

    suspend fun fetchHadiths(isEnglish: Boolean): Quote {
        listener?.onStarted()
        val quote = repo.fetchHadiths(isEnglish)
        if (quote.hadiths.isNotEmpty()) listener?.onSuccess()
        else listener?.onFailure("Try Again ")
        return quote
    }

    fun fetchCollection(isEnglish: Boolean): LiveData<MutableList<ModelCollection>> {
        listener?.onStarted()
        val mutableData = MutableLiveData<MutableList<ModelCollection>>()
        GlobalScope.launch(Dispatchers.IO) {
            delay(300)
            withContext(Dispatchers.Main) {
                repo.fetchCollection(isEnglish).observeForever { mutableData.value = it }
                if (mutableData.value!!.isNotEmpty()) listener?.onSuccess()
                else listener?.onFailure("Try Again ")
            }
        }
        return mutableData
    }

    fun fetchVideos(isEnglish: Boolean): LiveData<MutableList<Video>> {
        listener?.onStarted()
        val mutableData = MutableLiveData<MutableList<Video>>()
        GlobalScope.launch(Dispatchers.IO) {
            delay(300)
            withContext(Dispatchers.Main) {
                repo.fetchVideos(isEnglish)
                    .observeForever { videosList -> mutableData.value = videosList }
                if (mutableData.value!!.isNotEmpty()) listener?.onSuccess()
                else listener?.onFailure("Try Again ")
            }
        }
        return mutableData
    }

    fun fetchTopics(isEnglish: Boolean, topic: String): MutableLiveData<MutableList<Topic>> {
        listener?.onStarted()
        val mutableData = MutableLiveData<MutableList<Topic>>()
        GlobalScope.launch(Dispatchers.IO) {
            delay(300)
            withContext(Dispatchers.Main) {
                repo.fetchTopics(isEnglish, topic)
                    .observeForever { mutableData.value = it }
                if (mutableData.value!!.isNotEmpty()) listener?.onSuccess()
                else listener?.onFailure("Try Again ")
            }
        }
        return mutableData
    }

    suspend fun fetchTopic(isEnglish: Boolean, collectionID: String, documentId: String): Topic {
        listener?.onStarted()
        val topic = repo.fetchTopic(isEnglish, collectionID, documentId)
        if (topic.id.isNotEmpty()) listener?.onSuccess()
        else listener?.onFailure("Try Again ")
        return topic
    }

    fun rateTopic(isEnglish: Boolean, collectionID: String, topicID: String, isGood: Boolean) {
        listener?.onStarted()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                repo.rateTopic(isEnglish, collectionID, topicID, isGood)
                listener?.onSuccess()
            } catch (e: FirebaseFirestoreException) {
                listener?.onFailure(e.message!!)
            }
        }
    }

    fun sendFeedback(msg: String) {
        listener?.onStarted()
        GlobalScope.launch(Dispatchers.IO) {
            delay(1000)
            withContext(Dispatchers.Main) {
                try {
                    repo.sendFeedback(msg)
                    listener?.onSuccess()
                } catch (e: FirebaseFirestoreException) {
                    listener?.onFailure(e.localizedMessage!!)
                }
            }
        }
    }

    companion object {
        private val repo = Repository()
        var listener: Listener? = null
    }

}