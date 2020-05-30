package com.ramadan.theReminder.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.ramadan.theReminder.Model.Category
import com.ramadan.theReminder.Model.Hadith
import com.ramadan.theReminder.Model.Prophet
import com.ramadan.theReminder.Repo.Repo

class ViewModel : ViewModel() {
    private val repo = Repo()
    fun fetchStory(): LiveData<MutableList<Prophet>> {
        val mutableData = MutableLiveData<MutableList<Prophet>>()
        repo.fetchStory().observeForever { prophetList -> mutableData.value = prophetList }
        return mutableData
    }

    fun fetchCategory(): LiveData<MutableList<Category>> {
        val mutableData = MutableLiveData<MutableList<Category>>()
        repo.fetchCategory().observeForever { categoryList -> mutableData.value = categoryList }
        return mutableData
    }

    fun fetchHadith(category: String): LiveData<MutableList<Hadith>> {
        val mutableData = MutableLiveData<MutableList<Hadith>>()
        repo.getHadith(category).observeForever { hadithList -> mutableData.value = hadithList }
        return mutableData
    }


    fun insert() {
        val data = hashMapOf(
            "imgUrl" to "https://i.pinimg.com/originals/89/0c/a6/890ca60c310272165c34a3374179273b.jpg"
        )
        val string: String? = "00"
        FirebaseFirestore.getInstance().collection("hadith")
            .document("Death").collection("Death")
            .document(string!!).set(data).addOnSuccessListener { print("frrf") }
            .addOnFailureListener { e -> println("Error!! + $e") }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    fun insert1() {
        val data = hashMapOf(
            "imgUrl" to "https://i.pinimg.com/originals/7a/7a/24/7a7a240418e31b792846449ca1e44671.jpg"
        )
        val string: String? = "01"
        FirebaseFirestore.getInstance().collection("hadith")
            .document("Death").collection("Death")
            .document(string!!).set(data).addOnSuccessListener { print("frrf") }
            .addOnFailureListener { e -> println("Error!! + $e") }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    fun insert2() {
        val data = hashMapOf(
            "imgUrl" to "https://i.pinimg.com/originals/5f/f4/bb/5ff4bb92d344409f444ebf4b47d8d954.jpg"
        )
        val string: String? = "02"
        FirebaseFirestore.getInstance().collection("hadith")
            .document("Death").collection("Death")
            .document(string!!).set(data).addOnSuccessListener { print("frrf") }
            .addOnFailureListener { e -> println("Error!! + $e") }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    fun insert3() {
        val data = hashMapOf(
            "imgUrl" to "https://media.islamicity.org/wp-content/uploads/2017/02/prophet-muhammad-quote-god-love.jpg"
        )
        val string: String? = "03"
        FirebaseFirestore.getInstance().collection("hadith")
            .document("Death").collection("Death")
            .document(string!!).set(data).addOnSuccessListener { print("frrf") }
            .addOnFailureListener { e -> println("Error!! + $e") }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    fun insert4() {
        val data = hashMapOf(
            "imgUrl" to "https://i.pinimg.com/originals/f1/a6/e3/f1a6e326d51229344d39e07cca3d8d06.jpg"
        )
        val string: String? = "04"
        FirebaseFirestore.getInstance().collection("hadith")
            .document("Death").collection("Death")
            .document(string!!).set(data).addOnSuccessListener { print("frrf") }
            .addOnFailureListener { e -> println("Error!! + $e") }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    fun insert5() {
        val data = hashMapOf(
            "imgUrl" to "https://i.pinimg.com/originals/08/a9/98/08a9981b9ab5549a39eeed8be6a8efbd.jpg"
        )
        val string: String? = "05"
        FirebaseFirestore.getInstance().collection("hadith")
            .document("Death").collection("Death")
            .document(string!!).set(data).addOnSuccessListener { print("frrf") }
            .addOnFailureListener { e -> println("Error!! + $e") }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    fun insert6() {
        val data = hashMapOf(
            "imgUrl" to "https://lh3.googleusercontent.com/proxy/IWJrdBELEWxP10_tj0cq_jbW3AGDOVIJShErX0h5gPdTdPObB-IWmi7i3gLLmkgSXYnnDZWOMe5b3D0tQCVm1x_wHNA__2aDRkMHnFviSDGulU82P_4"
        )
        val string: String? = "06"
        FirebaseFirestore.getInstance().collection("hadith")
            .document("Death").collection("Death")
            .document(string!!).set(data).addOnSuccessListener { print("frrf") }
            .addOnFailureListener { e -> println("Error!! + $e") }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    fun insert7() {
        val data = hashMapOf(
            "imgUrl" to "https://i.pinimg.com/originals/a7/ec/b6/a7ecb66202b85e7a80e37ab1af5bf8cf.jpg"
        )
        val string: String? = "07"
        FirebaseFirestore.getInstance().collection("hadith")
            .document("Death").collection("Death")
            .document(string!!).set(data).addOnSuccessListener { print("frrf") }
            .addOnFailureListener { e -> println("Error!! + $e") }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    fun insert8() {
        val data = hashMapOf(
            "imgUrl" to "https://pbs.twimg.com/media/EUoDGpDWsAMCmx_.jpg"
        )
        val string: String? = "08"
        FirebaseFirestore.getInstance().collection("hadith")
            .document("Death").collection("Death")
            .document(string!!).set(data).addOnSuccessListener { print("frrf") }
            .addOnFailureListener { e -> println("Error!! + $e") }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
    fun insert9() {
        val data = hashMapOf(
            "imgUrl" to "https://fa.iuvmarchive.com/storage/2019-09-11/pictures/nQahVfXL6WD035DsSkOBTCv1FUyQbIegqI82PAwB.jpeg"
        )
        val string: String? = "09"
        FirebaseFirestore.getInstance().collection("hadith")
            .document("Death").collection("Death")
            .document(string!!).set(data).addOnSuccessListener { print("frrf") }
            .addOnFailureListener { e -> println("Error!! + $e") }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
    fun insert10() {
        val data = hashMapOf(
            "imgUrl" to "https://fa.iuvmarchive.com/storage/2019-09-11/pictures/SsHpNa6aW9kJii8qrSI1Qb0eG1qVFHVYoRZmk9bH.jpeg"
        )
        val string: String? = "10"
        FirebaseFirestore.getInstance().collection("hadith")
            .document("Death").collection("Death")
            .document(string!!).set(data).addOnSuccessListener { print("frrf") }
            .addOnFailureListener { e -> println("Error!! + $e") }

    }
///////////////////////////////////////////////////////////////////////////////////////////


}