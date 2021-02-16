package com.ramadan.islami.utils

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
import com.google.android.material.snackbar.Snackbar
import com.ramadan.islami.R
import com.ramadan.islami.R.string
import com.squareup.picasso.Picasso
import de.blox.graphview.Node
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import kotlin.random.Random


class Utils(val context: Context) {

    private val dirPath = Environment.getExternalStoragePublicDirectory(
        Environment.DIRECTORY_PICTURES).path + "/" + context.getString(R.string.app_name)

    val input = "abc"
    var array = Array(input.length) { input[it].toString() }


    val adam = Node(context.getString(string.adam))
    val empty = Node("\t\t\t\t\t\t\t\t")
    val arabized_arabs = Node(context.getString(string.arabized_arabs))
    val dashed = Node("--")
    val aron = Node(context.getString(string.aron))
    val abraham = Node(context.getString(string.abraham))
    val david = Node(context.getString(string.david))
    val dhul_kifl = Node(context.getString(string.dhul_kifl))
    val elisha = Node(context.getString(string.elisha))
    val elias = Node(context.getString(string.elias))
    val heber = Node(context.getString(string.heber))
    val idriss = Node(context.getString(string.idriss))
    val isaac = Node(context.getString(string.isaac))
    val ishmael = Node(context.getString(string.ishmael))
    val jacob = Node(context.getString(string.jacob))
    val jonah = Node(context.getString(string.jonah))
    val jesus = Node(context.getString(string.jesus))
    val jethri = Node(context.getString(string.jethri))
    val job = Node(context.getString(string.job))
    val joseph = Node(context.getString(string.joseph))
    val john = Node(context.getString(string.john))
    val lot = Node(context.getString(string.lot))
    val methuselah = Node(context.getString(string.methuselah))
    val moses = Node(context.getString(string.moses))
    val muhammad = Node(context.getString(string.muhammad))
    val noah = Node(context.getString(string.noah))
    val solomon = Node(context.getString(string.solomon))
    val zacharia = Node(context.getString(string.zacharia))


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
                Snackbar.make(it,
                    context.getString(string.failed_to_download),
                    Snackbar.LENGTH_LONG).show()
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


}


val defaultImg: String =
    "https://firebasestorage.googleapis.com/v0/b/islami-ecc03.appspot.com/o/islami_background_256.png?alt=media&token=72e1403c-2e25-4c8c-b1c0-2cf383153c01"

