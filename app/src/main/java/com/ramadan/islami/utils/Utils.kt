package com.ramadan.islami.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.KeyguardManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
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
import java.io.IOException
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
            "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/collection%2FhadithOfDay.jpg?alt=media&token=543ece34-faa2-4c27-b77d-77f3ffb9d945"
        ),
        CollectionModel("muhammadStory",
            context.getString(string.muhammad),
            "https://i.pinimg.com/564x/c1/6a/fe/c16afea2f025b7ae1dfddbafe8e15f02.jpg"),
        CollectionModel(
            "hadiths",
            context.getString(string.hadiths),
            "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/collection%2Fhadiths.jpg?alt=media&token=6464925e-1712-463d-82a3-fbc34a3f211f"
        ),
    )
    val dailyMutableList: MutableList<CollectionModel> = mutableListOf(
        CollectionModel(
            "prayerTimes",
            context.getString(string.prayerTimes),
            "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/collection%2Fprayer_times.jpg?alt=media&token=860b09fa-e3b5-4ea8-9826-d207d549c704"
        ),
        CollectionModel(
            "verseOfDay",
            context.getString(string.verseOfDay),
            "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/collection%2Fverse.jpg?alt=media&token=de42048d-28c5-408f-a9fe-19ad5c19ec50"
        ),
        CollectionModel(
            "azkarOfDay",
            context.getString(string.azkar),
            "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/collection%2Fazkar.jpg?alt=media&token=27870e4e-a359-4a1b-91de-057de5afa828"
        ),
        CollectionModel(
            "eveningAzkar",
            context.getString(string.eveningAzkar),
            "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/collection%2Fevining.jpg?alt=media&token=4e649ded-b982-4fc3-905f-798e49a9633c"
        ),
        CollectionModel(
            "morningAzkar",
            context.getString(string.morningAzkar),
            "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/collection%2Fmorning.jpg?alt=media&token=a3693803-5e76-4938-bc3d-dd951a5bc207"
        ),
        CollectionModel(
            "hadithOfDay",
            context.getString(string.hadithOfDay),
            "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/collection%2Fhadith.jpg?alt=media&token=041848ee-c824-47aa-a33b-1d697d232269"
        ),
        CollectionModel(
            "dateConversion",
            context.getString(string.dateConversion),
            "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/collection%2Fdate.jpg?alt=media&token=7071acd5-fe38-4ef5-9aa9-b0ffc0bd5aec"
        ),
        CollectionModel(
            "qibla",
            context.getString(string.qibla),
            "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/collection%2Fqibla.jpg?alt=media&token=ba255776-f242-49ab-9dd8-bbf7e18f2f49"
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

    val prayers = mutableListOf(
        context.getString(string.fajr),
        context.getString(string.dhuhr),
        context.getString(string.asr),
        context.getString(string.maghrib),
        context.getString(string.isha),
    )
    val weekday = mutableListOf(
        context.getString(string.saturday),
        context.getString(string.sunday),
        context.getString(string.monday),
        context.getString(string.tuesday),
        context.getString(string.wednsday),
        context.getString(string.thursday),
        context.getString(string.friday),
        context.getString(string.saturday),
    )
    val month = mutableListOf(
        context.getString(string.muharram),
        context.getString(string.safar),
        context.getString(string.rabeAwl),
        context.getString(string.rabeAkhr),
        context.getString(string.jamadAwl),
        context.getString(string.jamadAkhr),
        context.getString(string.rajab),
        context.getString(string.shaban),
        context.getString(string.ramadan),
        context.getString(string.shawal),
        context.getString(string.dhuKeada),
        context.getString(string.dhuHija),
    )

}


const val debug_tag = "TOTO"
const val lat = 31.107364
const val lon = 29.783520
const val ACCESS_FINE_LOCATION_REQ_CODE = 35
const val QIBLA_LATITUDE = 21.3891
const val QIBLA_LONGITUDE = 39.8579
const val ONESIGNAL_APP_ID = "84b5b5b5-1be2-49c4-b7cc-dc033da3bf84"
private val dirPath = Environment.getExternalStoragePublicDirectory(
    Environment.DIRECTORY_PICTURES
).path + "/islami"

fun showMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun showImg(imgUrl: String, context: Context) {
    val dialogBuilder = AlertDialog.Builder(context)
    val view = LayoutInflater.from(context).inflate(R.layout.img_dialog, null)
    dialogBuilder.setView(view)
    val alertDialog = dialogBuilder.create()
    alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alertDialog.show()
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
            it.snackBar(context.getString(string.saved))
        } catch (e: Exception) {
            it.snackBar(context.getString(string.failedToDownload))
        }
        false
    }
}


fun showBrief(title: String, content: String, context: Context) {
    val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
    val view = View.inflate(context, R.layout.story_marker, null)
    dialogBuilder.setView(view)
    val alertDialog = dialogBuilder.create()
    alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alertDialog.show()
    alertDialog.setCancelable(true)
    view.findViewById<LinearLayout>(R.id.actionBar).visibility = View.GONE
    view.findViewById<TextView>(R.id.alertTitle).text = title
    val alertContent = view.findViewById<TextView>(R.id.alertContent)
    alertContent.visibility = View.VISIBLE
    alertContent.text = content
}


fun saveImage(bitmap: Bitmap) {
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
        Log.e(debug_tag, e.message.toString())
    }
}

fun Context.isNetworkConnected(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
}

fun Activity.turnScreenOnAndKeyguardOff() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
        setShowWhenLocked(true)
        setTurnScreenOn(true)
    } else {
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
        )
    }

    with(getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requestDismissKeyguard(this@turnScreenOnAndKeyguardOff, null)
        }
    }
}

const val defaultImg: String =
    "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/islami_background_256.png?alt=media&token=72e1403c-2e25-4c8c-b1c0-2cf383153c01"

const val muhammadFamilyAR =
    "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/familyTree%2Fmuhammad_tree_ar.png?alt=media&token=ab45bdf5-652f-43ce-8eb2-1a4f313c82a8"
const val muhammadFamilyEN =
    "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/familyTree%2Fmuhammad_tree_en.png?alt=media&token=6afa4dbc-68e0-4e66-a6ac-041acb58bffd"
const val prophetsFamilyAR =
    "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/familyTree%2Fprophets_tree_ar.png?alt=media&token=a4d715c2-fc87-4658-acfe-11cfcac6190c"
const val prophetsFamilyEN =
    "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/familyTree%2Fprophets_tree_en.png?alt=media&token=a2b61e24-1833-4873-8ef7-efa0fd9afee8"

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

fun View.changeNavigation(direction: NavDirections) {
    Navigation.findNavController(this).navigate(direction)
}

fun View.snackBar(message: String) {
    Snackbar.make(
        this,
        message,
        Snackbar.LENGTH_LONG
    ).apply {
        setTextColor(resources.getColor(R.color.white))
        show()
    }
}

fun getLocalBitmapUri(imageView: ImageView): Uri? {
    val drawable = imageView.drawable
    val bmp: Bitmap? = if (drawable is BitmapDrawable) {
        (imageView.drawable as BitmapDrawable).bitmap
    } else return null

    var bmpUri: Uri? = null
    try {
        val file = File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS), "family_tree_" + System.currentTimeMillis() + ".png")
        file.parentFile.mkdirs()
        val out = FileOutputStream(file)
        bmp?.compress(Bitmap.CompressFormat.PNG, 90, out)
        out.close()
        bmpUri = Uri.fromFile(file)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return bmpUri
}
