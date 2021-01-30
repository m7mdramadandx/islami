package com.ramadan.islami.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar
import com.ramadan.islami.R
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.quote_img.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import kotlin.random.Random


class SliderAdapter(val context: Context) :
    SliderViewAdapter<SliderAdapter.CustomView>() {

    private var dataList = ArrayList<String>()

    fun setDataList(data: ArrayList<String>) {
        dataList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): CustomView {
        val inflate: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.quote_img, parent, false)
        return CustomView(inflate)
    }

    override fun onBindViewHolder(viewHolder: CustomView, position: Int) {
        val imgUrl: String = dataList[position]
        return viewHolder.customView(imgUrl)
    }

    override fun getCount(): Int = if (dataList.size > 0) dataList.size else 0

    class CustomView(itemView: View) :
        ViewHolder(itemView) {
        private val dirPath = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES).path + "/" + itemView.context.getString(R.string.app_name)

        fun customView(imgUrl: String) {
            Picasso.get()
                .load(imgUrl).error(R.drawable.failure_img).placeholder(R.drawable.load_img)
                .into(itemView.sliderImg)
            itemView.setOnClickListener { showImg(imgUrl, itemView.context) }
        }

        private fun showImg(imgUrl: String, context: Context) {
            val dialogBuilder = AlertDialog.Builder(context)
            val view = LayoutInflater.from(context).inflate(R.layout.img_dialog, null)
            dialogBuilder.setView(view)
            val alertDialog = dialogBuilder.create()
            val imageView: ImageView = view.findViewById(R.id.quoteImg)
            Picasso.get()
                .load(imgUrl).error(R.drawable.failure_img).placeholder(R.drawable.load_img)
                .into(imageView)
            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            imageView.setOnLongClickListener {
                try {
                    saveImage(bitmap, context)
                    Snackbar.make(it, "Saved", Snackbar.LENGTH_LONG).show()
                } catch (e: Exception) {
                    Snackbar.make(it, "Failed to download", Snackbar.LENGTH_LONG).show()
                }
                false
            }
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.show()
        }

        private fun saveImage(bitmap: Bitmap, context: Context) {
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
}