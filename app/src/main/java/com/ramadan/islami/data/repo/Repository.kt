package com.ramadan.islami.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import kotlin.collections.mapOf
import kotlin.collections.mutableListOf
import kotlin.collections.toSortedMap

class Repository {
    private val tag = "Repository"
    private val rootCollection = FirebaseFirestore.getInstance().collection("root")
    var language = "ar"

    suspend fun fetchAllStories(isEnglish: Boolean): LiveData<MutableList<Story>> {
        val mutableData = MutableLiveData<MutableList<Story>>()
        val dataList: MutableList<Story> = mutableListOf()
        if (isEnglish) language = "en"
        rootCollection.document(language).collection("stories").get()
            .addOnSuccessListener { result ->
                result.forEach { document ->
                    val title = document.getString("name") ?: document.id
                    val imgUrl = document.getString("image") ?: defaultImg
                    val brief = document.getString("brief") ?: "defaultImg"
                    val text = document.get("text") as ArrayList<String>?
                    val story =
                        Story(title.toUpperCase(Locale.ROOT), document.id, imgUrl, brief, text!!)
                    dataList.add(story)
                }
                mutableData.value = dataList
            }.addOnFailureListener { e -> println("Error!! + $e") }.await()
        return mutableData
    }

    suspend fun fetchStory(isEnglish: Boolean, storyName: String): Story {
        var story = Story(storyName, storyName, defaultImg, "brief", ArrayList(0))
        if (isEnglish) language = "en"
        rootCollection.document(language).collection("stories").get()
            .addOnSuccessListener { result ->
                result.forEach { document ->
                    if (storyName == document.id) {
                        val title: String = document.getString("name") ?: document.id
                        val name: String = document.id
                        val imgUrl: String = document.getString("image") ?: defaultImg
                        val brief: String = document.getString("brief") ?: "defaultImg"
                        val text: ArrayList<String>? = document.get("text") as ArrayList<String>?
                        story = Story(title.toUpperCase(Locale.ROOT), name, imgUrl, brief, text!!)
                        return@forEach
                    }
                }
            }.addOnFailureListener { e -> println("Error!! + $e") }.await()
        return story
    }

    suspend fun fetchQuotes(isEnglish: Boolean): LiveData<MutableList<Quote>> {
        val mutableLiveData = MutableLiveData<MutableList<Quote>>()
        val dataList: MutableList<Quote> = mutableListOf()
        if (isEnglish) language = "en"
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

    suspend fun fetchHadiths(isEnglish: Boolean): Quote {
        val quote: Quote
        if (isEnglish) language = "en"
        val data = rootCollection.document(language).collection("quotes")
            .document("hadiths").get().await()
        val id: String = data.id
        val title: String = data.getString("title") ?: data.id
        val image: String = data.getString("image") ?: defaultImg
        val hadiths = data.get("hadiths") as ArrayList<String>
        quote = Quote(id, title, image, ArrayList(0), hadiths)
        return quote
    }

    suspend fun fetchCollection(isEnglish: Boolean): MutableLiveData<MutableList<Collection>> {
        val mutableLiveData = MutableLiveData<MutableList<Collection>>()
        val dataList: MutableList<Collection> = mutableListOf()
        if (isEnglish) language = "en"
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

    suspend fun fetchVideos(isEnglish: Boolean): MutableLiveData<MutableList<Video>> {
        val mutableLiveData = MutableLiveData<MutableList<Video>>()
        val dataList: MutableList<Video> = mutableListOf()
        if (isEnglish) language = "en"
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

    suspend fun fetchInformation(isEnglish: Boolean): MutableLiveData<MutableList<Information>> {
        val mutableLiveData = MutableLiveData<MutableList<Information>>()
        val dataList: MutableList<Information> = mutableListOf()
        if (isEnglish) language = "en"
        rootCollection.document(language).collection("collection").document("information")
            .collection("information").get().await()
            .forEach {
                val id: String = it.id.toUpperCase(Locale.ROOT)
                val title: String = it.getString("title") ?: it.id.toUpperCase(Locale.ROOT)
                val brief: String = it.getString("brief") ?: "brief"
                val image: String = it.getString("image") ?: defaultImg
                val content: Map<String, String> = it.get("content") as Map<String, String>
                val info = Information(id, title, brief, image, content.toSortedMap())
                dataList.add(info)
            }
        mutableLiveData.value = dataList
        return mutableLiveData
    }

    fun sendFeedback(msg: String) {
        rootCollection.document("feedback").collection("messages")
            .add(mapOf("msg" to msg))
            .addOnCompleteListener { println("DONE") }
            .addOnFailureListener { println(it.message) }
    }
}