package com.ramadan.islami.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.ramadan.islami.R
import com.ramadan.islami.R.string
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random
import com.ramadan.islami.data.model.Collection as CollectionModel


class Utils(val context: Context) {

    val suggestionMutableList: MutableList<CollectionModel> = mutableListOf(
        CollectionModel(
            "quran",
            context.getString(string.quran),
            "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/collection%2Fquran.jpg?alt=media&token=d57934da-a893-40c0-8927-d69156a914fa"
        ),
        CollectionModel(
            "hadithOfDay",
            context.getString(string.hadithOfDay),
            "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/collection%2Fhadith.jpg?alt=media&token=041848ee-c824-47aa-a33b-1d697d232269"
        ),
        CollectionModel("muhammadStory", context.getString(string.muhammad), defaultImg),
        CollectionModel(
            "hadiths",
            context.getString(string.hadiths),
            "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/collection%2F3a339a32aba6a17b9bfa2585a3c596b7.jpg?alt=media&token=42432ef6-7f5d-4b2b-8448-72a606960af0"
        ),
    )
    val dailyMutableList: MutableList<CollectionModel> = mutableListOf(
        CollectionModel(
            "azkarOfDay",
            context.getString(string.azkarOfDay),
            "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/collection%2Fazkar.jpg?alt=media&token=27870e4e-a359-4a1b-91de-057de5afa828"
        ),
        CollectionModel(
            "prayerTimes",
            context.getString(string.prayerTimes),
            "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/collection%2Fprayer_times.jpg?alt=media&token=860b09fa-e3b5-4ea8-9826-d207d549c704"
        ),
        CollectionModel(
            "verseOfDay",
            context.getString(string.verseOfDay),
            "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/collection%2Fverse.jpg?alt=media&token=6e10a9bc-0e81-4edb-b4e9-8427ab430266"
        ),
        CollectionModel(
            "dateConversion",
            context.getString(string.dateConversion),
            "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/collection%2Fdate.jpg?alt=media&token=e402ccf4-2458-4d80-8043-55a4d8db008b"
        ),
        CollectionModel(
            "qibla",
            context.getString(string.qibla),
            "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/collection%2Fqibla.jpg?alt=media&token=f7a0855d-5e61-47a4-947b-7d26151b5fe1"
        ),
        CollectionModel(
            "allahNames",
            context.getString(string.allahNames),
            "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/collection%2Fallah_names.jpg?alt=media&token=34d864ca-a962-441e-baad-2764f6130423"
        ),
    )
    val familyTreeMutableList: MutableList<CollectionModel> = mutableListOf(
        CollectionModel("muhammadTree", context.getString(string.muhammadFamilyTree), defaultImg),
        CollectionModel("prophetsTree", context.getString(string.prophetsFamilyTree), defaultImg),
        CollectionModel("bigTree", context.getString(string.bigFamilyTree), defaultImg),
    )

    val weekday = mutableListOf(
        context.getString(string.saturday),
        context.getString(string.sunday),
        context.getString(string.monday),
        context.getString(string.tuesday),
        context.getString(string.wednsday),
        context.getString(string.thursday),
        context.getString(string.friday),
    )
    val month = mutableListOf(
        context.getString(string.muharram),
        context.getString(string.safar),
        context.getString(string.rabe_awl),
        context.getString(string.rabe_akhr),
        context.getString(string.jamad_awl),
        context.getString(string.jamad_akhr),
        context.getString(string.rajab),
        context.getString(string.shaban),
        context.getString(string.ramadan),
        context.getString(string.shawal),
        context.getString(string.dhu_keada),
        context.getString(string.dhu_hija),
    )
}

fun showMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

private val dirPath = Environment.getExternalStoragePublicDirectory(
    Environment.DIRECTORY_PICTURES
).path + "/islami"

val input = "abc"
var array = Array(input.length) { input[it].toString() }

fun showImg(imgUrl: String, context: Context) {
    val dialogBuilder = AlertDialog.Builder(context)
    val view = LayoutInflater.from(context).inflate(R.layout.img_dialog, null)
    dialogBuilder.setView(view)
    val alertDialog = dialogBuilder.create()
    val imageView: ImageView = view.findViewById(R.id.quoteImg)
    Picasso.get()
        .load(imgUrl).error(R.drawable.failure_img).placeholder(R.drawable.load_img)
        .into(imageView)
    var bitmap: Bitmap? = null
    try {
        bitmap = (imageView.drawable as BitmapDrawable).bitmap
    } catch (e: Exception) {
        Log.e("ERROR", e.localizedMessage!!)
    }
    imageView.setOnLongClickListener {
        try {
            saveImage(bitmap!!)
            Snackbar.make(it, context.getString(string.saved), Snackbar.LENGTH_LONG).show()
        } catch (e: Exception) {
            Snackbar.make(
                it,
                context.getString(string.failedToDownload),
                Snackbar.LENGTH_LONG
            ).show()
        }
        false
    }
    alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alertDialog.show()
}

fun showBrief(title: String, content: String, context: Context) {
    val dialogBuilder = AlertDialog.Builder(context)
    val view = LayoutInflater.from(context).inflate(R.layout.story_marker, null)
    dialogBuilder.setView(view)
    val alertDialog = dialogBuilder.create()
    view.findViewById<LinearLayout>(R.id.actionBar).visibility = View.GONE
    view.findViewById<TextView>(R.id.alertTitle).text = title
    val alertContent = view.findViewById<TextView>(R.id.alertContent)
    alertContent.visibility = View.VISIBLE
    alertContent.text = content
    alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alertDialog.show()
}


private fun saveImage(bitmap: Bitmap) {
    try {
        val dir = File(dirPath)
        if (!dir.exists()) dir.mkdirs()
        val file = File("$dirPath/${Random.nextDouble()}.jpg")
        file.createNewFile()
        val outStream: OutputStream?
        outStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
        outStream.flush()
        outStream.close()
    } catch (e: Exception) {
        println(e.message)
    }
}


const val defaultImg: String =
    "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/islami_background_256.png?alt=media&token=72e1403c-2e25-4c8c-b1c0-2cf383153c01"

const val debug_tag = "TOTO"
const val lat = 31.107364
const val lon = 29.783520

fun dateOfDay(): String {
    val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
    return simpleDateFormat.format(Date())
}

val nf: NumberFormat = NumberFormat.getInstance(Locale.forLanguageTag("AR"))

fun coloredJson(text: String): String {
    return "<font color='#E1B34F'>$text</font>"
}

const val MUSLIM_SALAT_URL = "http://muslimsalat.com/"
const val GITHUB_URL = "https://api.github.com/"

fun ImageView.loadImage(url: String?) {
//    val option = RequestOptions()
//        .circleCrop()

    Picasso.get()
        .load(url)
        .into(this)
}

//@BindingAdapter("android:imageCircleUrl")
fun loadImageUrl(view: ImageView, url: String?) {
    view.loadImage(url)
}

fun View.changeNavigation(direction: NavDirections) {
    Navigation.findNavController(this).navigate(direction)
}

// Date Format
@SuppressLint("SimpleDateFormat")
private val m24HourSDF = SimpleDateFormat("HH:mm")

@SuppressLint("SimpleDateFormat")
private val m12HourSDF = SimpleDateFormat("hh:mm aa")

@SuppressLint("SimpleDateFormat")
fun convertTo24HrFormat(date: String): String = m24HourSDF.format(m12HourSDF.parse(date)!!)

fun convertToDateFormat(date: String) = m24HourSDF.parse(date)!!

@SuppressLint("SimpleDateFormat")
val getCurrentTimeFormat: String = SimpleDateFormat("hh:mm aa").format(Date())

@SuppressLint("SimpleDateFormat")
val getCurrentDayFormat: String = SimpleDateFormat("EEEE").format(Date())

@SuppressLint("SimpleDateFormat")
val getCurrentDateNormalFormat: String = SimpleDateFormat("dd MMMM yyyy").format(Date())

@SuppressLint("SimpleDateFormat")
fun getDayFormat(date: Date): String = SimpleDateFormat("EEEE").format(date)

@SuppressLint("SimpleDateFormat")
fun getDateNormalFormat(date: Date): String = SimpleDateFormat("dd MMMM yyyy").format(date)

@SuppressLint("SimpleDateFormat")
fun getDateFormat(date: Date): String = SimpleDateFormat("yyyy-MM-dd").format(date)

fun removeColon(string: String) = string.filterNot { it == ':' }.toInt()

fun convertFromLongToTime(mills: Long): String {
    val hours = (mills / (1000 * 60 * 60)).toString()
    val minutes = ((mills / (1000 * 60)) % 60).toString()
    val seconds = ((mills / 1000) % 60 % 60).toString()

    return "${(if (hours.length == 1) "0$hours" else hours)}:${(if (minutes.length == 1) "0$minutes" else minutes)}:${(if (seconds.length == 1) "0$seconds" else seconds)}"
}

fun getDifferentMillsTime(time: String): Long =
    convertToDateFormat(time).time - convertToDateFormat(convertTo24HrFormat(getCurrentTimeFormat)).time

fun getDifferentMillsTimeBA(before: String, after: String): Long =
    convertToDateFormat(after).time - convertToDateFormat(before).time