package com.ramadan.islami.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.ramadan.islami.data.model.*
import com.ramadan.islami.data.model.Collection
import com.ramadan.islami.utils.defaultImg
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.forEach
import kotlin.collections.hashMapOf
import kotlin.collections.mapOf
import kotlin.collections.mutableListOf
import kotlin.collections.toSortedMap

class FirebaseRepository {
    private val tag = "Repository"

    suspend fun fetchSuggestion(language: String): LiveData<MutableList<Story>> {
        val mutableData = MutableLiveData<MutableList<Story>>()
        val dataList: MutableList<Story> = mutableListOf()
        rootCollection.document(language).collection("suggestion").get()
            .addOnSuccessListener { result ->
                result.forEach { document ->
                    val id: String = document.id
                    val title = document.getString("title") ?: document.id
                    val image = document.getString("image") ?: defaultImg
                    val brief = document.getString("brief") ?: "brief"
                    val arrayList = document.get("arrayList") as ArrayList<String>
                    val story = Story(id, title, image, brief, arrayList)
                    dataList.add(story)
                }
                mutableData.value = dataList
            }.addOnFailureListener { e -> println("Error!! + $e") }.await()
        return mutableData
    }

    suspend fun fetchAllStories(language: String): LiveData<MutableList<Story>> {
        val mutableData = MutableLiveData<MutableList<Story>>()
        val dataList: MutableList<Story> = mutableListOf()
        rootCollection.document(language).collection("stories").get()
            .addOnSuccessListener { result ->
                result.forEach { document ->
                    val id: String = document.id
                    val title = document.getString("name") ?: document.id
                    val imgUrl = document.getString("image") ?: defaultImg
                    val brief = document.getString("brief") ?: "brief"
                    val text = document.get("text") as ArrayList<String>
                    val story = Story(id, title, imgUrl, brief, text)
                    dataList.add(story)
                }
                mutableData.value = dataList
            }.addOnFailureListener { e -> println("Error!! + $e") }.await()
        return mutableData
    }

    suspend fun fetchStory(language: String, storyID: String): Story {
        var story = Story(storyID, storyID, defaultImg, "brief", ArrayList(0))
        rootCollection.document(language).collection("stories").get()
            .addOnSuccessListener { result ->
                result.forEach { document ->
                    if (storyID == document.id) {
                        val id: String = document.id
                        val title: String = document.getString("name") ?: document.id
                        val imgUrl: String = document.getString("image") ?: defaultImg
                        val brief: String = document.getString("brief") ?: "brief"
                        val text: ArrayList<String> = document.get("text") as ArrayList<String>
                        story = Story(id, title, imgUrl, brief, text)
                        return@forEach
                    }
                }
            }.addOnFailureListener { e -> println("Error!! + $e") }.await()
        return story
    }

    suspend fun fetchQuotes(language: String): LiveData<MutableList<Quote>> {
        val mutableLiveData = MutableLiveData<MutableList<Quote>>()
        val dataList: MutableList<Quote> = mutableListOf()
        rootCollection.document(language).collection("quotes").get().await()
            .forEach { snapshot ->
                val id: String = snapshot.id
                val title: String = snapshot.getString("title") ?: snapshot.id
                val image: String = snapshot.getString("image") ?: defaultImg
                val verses = (snapshot?.get("verses") ?: ArrayList<String>(0)) as ArrayList<String>
                val hadiths = snapshot.get("hadiths") as ArrayList<String>
                val category = Quote(id, title.toUpperCase(Locale.ROOT), image, verses, hadiths)
                dataList.add(category)
            }
        mutableLiveData.value = dataList
        return mutableLiveData
    }

    suspend fun fetchHadiths(language: String): Quote {
        val quote: Quote
        val data = rootCollection.document(language).collection("quotes")
            .document("hadiths").get().await()
        val id: String = data.id
        val title: String = data.getString("title") ?: data.id
        val image: String = data.getString("image") ?: defaultImg
        val hadiths = data.get("hadiths") as ArrayList<String>
        quote = Quote(id, title, image, ArrayList(0), hadiths)
        return quote
    }

    suspend fun fetchCollection(language: String): MutableLiveData<MutableList<Collection>> {
        val mutableLiveData = MutableLiveData<MutableList<Collection>>()
        val dataList: MutableList<Collection> = mutableListOf()
        rootCollection.document(language).collection("collection").get().await()
            .forEach {
                val id: String = it.id
                val title: String = it.getString("title") ?: it.id
                val image: String = it.getString("image") ?: defaultImg
                val collection = Collection(id, title, image)
                dataList.add(collection)
            }
        mutableLiveData.value = dataList
        return mutableLiveData
    }

    suspend fun fetchVideos(language: String): MutableLiveData<MutableList<Video>> {
        val mutableLiveData = MutableLiveData<MutableList<Video>>()
        val dataList: MutableList<Video> = mutableListOf()
        rootCollection.document(language).collection("collection").document("videos")
            .collection("videos").get().await()
            .forEach {
                val id: String = it.id
                val title: String = it.getString("title") ?: it.id
                val videosID: ArrayList<String> = it.get("id") as ArrayList<String>
                val video = Video(id, title, videosID)
                dataList.add(video)
            }
        mutableLiveData.value = dataList
        return mutableLiveData
    }

    suspend fun fetchTopics(
        language: String,
        topic: String,
    ): MutableLiveData<MutableList<Topic>> {
        val mutableLiveData = MutableLiveData<MutableList<Topic>>()
        val dataList: MutableList<Topic> = mutableListOf()
        rootCollection.document(language).collection("collection").document(topic)
            .collection(topic).get().await()
            .forEach {
                val id: String = it.id
                val title: String = it.getString("title") ?: it.id
                val brief: String = it.getString("brief") ?: "brief"
                val source: String = it.getString("source") ?: "source"
                val content: Map<String, String> = it.get("content") as Map<String, String>
                val topic1 = Topic(id, title, brief, source, 0.0, content.toSortedMap())
                dataList.add(topic1)
            }
        mutableLiveData.value = dataList
        return mutableLiveData
    }

    suspend fun fetchTopic(
        language: String,
        collectionID: String,
        documentId: String,
    ): Topic {
        val data = rootCollection.document(language).collection("collection").document(collectionID)
            .collection(collectionID).document(documentId).get().await()
        val id: String = data.id
        val title: String = data.getString("title") ?: data.id
        val brief: String = data.getString("brief") ?: "brief"
        val source: String = data.getString("source") ?: "source"
        val content: Map<String, String> =
            (data.get("content") ?: hashMapOf<String, String>()) as Map<String, String>
        return Topic(id, title, brief, source, 0.0, content.toSortedMap())
    }

    suspend fun rateTopic(
        language: String,
        collectionID: String,
        documentID: String,
        isGood: Boolean,
    ) {
        val value: Double = if (isGood) 1.0 else -1.0
        rootCollection.document(language).collection("collection").document(collectionID)
            .collection(collectionID).document(documentID)
            .update("rate", FieldValue.increment(value))
            .await()
    }

    suspend fun sendFeedback(msg: String) {
        rootCollection.document("feedback").collection("messages")
            .add(mapOf("msg" to msg)).await()
    }

    companion object {
        private val rootCollection = FirebaseFirestore.getInstance().collection("root")
    }
}