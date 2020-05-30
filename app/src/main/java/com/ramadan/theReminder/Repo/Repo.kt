package com.ramadan.theReminder.Repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.ramadan.theReminder.Model.Category
import com.ramadan.theReminder.Model.Hadith
import com.ramadan.theReminder.Model.Prophet

class Repo {
    fun getHadith(category: String): LiveData<MutableList<Hadith>> {
        var string: String = category
        if (category == "Ramadan") {
            string = "prophetMuhammad"
        }
        val mutableData = MutableLiveData<MutableList<Hadith>>()
        FirebaseFirestore.getInstance().collection("hadith").document(string)
            .collection(category).get().addOnSuccessListener {
                val dataList: MutableList<Hadith> = mutableListOf()
                for (document: QueryDocumentSnapshot in it) {
                    val imgUrl: String? = document.getString("imgUrl")
                    val hadith = Hadith(imgUrl!!)
                    dataList.add(hadith)
                }
                mutableData.value = dataList
            }
            .addOnFailureListener { e -> println("Error!!$e") }
        return mutableData
    }

    fun fetchStory(): LiveData<MutableList<Prophet>> {
        val mutableData = MutableLiveData<MutableList<Prophet>>()
        FirebaseFirestore.getInstance().collection("prophet").get()
            .addOnSuccessListener { result ->
                val dataList: MutableList<Prophet> = mutableListOf<Prophet>()
                for (document: QueryDocumentSnapshot in result) {
                    val name: String? = document.getString("name")
                    val story: String? = document.getString("story")
                    val imgUrl: String? = document.getString("imgUrl")
                    val prophet = Prophet(name!!, story!!, imgUrl!!)
                    dataList.add(prophet)
                }
                mutableData.value = dataList
            }.addOnFailureListener { e -> println("Error!! + $e") }

        return mutableData
    }

    fun fetchCategory(): LiveData<MutableList<Category>> {
        val mutableData = MutableLiveData<MutableList<Category>>()
        FirebaseFirestore.getInstance().collection("hadith").get()
            .addOnSuccessListener { result ->
                val dataList: MutableList<Category> = mutableListOf()
                for (document: QueryDocumentSnapshot in result) {
                    val name: String? = document.getString("category")
                    val imgUrl: String? = document.getString("imgUrl")
                    val category = Category(name!!, imgUrl!!)
                    dataList.add(category)
                }
                mutableData.value = dataList
            }.addOnFailureListener { e -> println("Error!! + $e") }

        return mutableData
    }
}