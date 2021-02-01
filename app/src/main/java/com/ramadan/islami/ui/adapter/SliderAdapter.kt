package com.ramadan.islami.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Category
import com.ramadan.islami.ui.activity.Quote
import com.ramadan.islami.ui.activity.Story
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.slider_img.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import kotlin.random.Random


class SliderAdapter(val context: Context) :
    SliderViewAdapter<SliderAdapter.CustomView>() {
    private var storyList = mutableListOf<com.ramadan.islami.data.model.Story>()
    private var categoryList = mutableListOf<Category>()

    fun setProphetDataList(data: MutableList<com.ramadan.islami.data.model.Story>) {
        storyList = data
        notifyDataSetChanged()
    }

    fun setCategoryDataList(data: MutableList<Category>) {
        data.removeAt(1)
        categoryList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): CustomView {
        val inflate: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.slider_img, parent, false)
        return CustomView(inflate)
    }

    override fun getCount(): Int {
        return when {
            storyList.size > 0 -> 6
            categoryList.size > 0 -> categoryList.size
            else -> 0
        }
    }

    override fun onBindViewHolder(viewHolder: CustomView, position: Int) {
        when {
            storyList.size > 0 -> return viewHolder.storyView(storyList[position])
            categoryList.size > 0 -> return viewHolder.categoryView(categoryList[position])
        }
    }

    class CustomView(itemView: View) : ViewHolder(itemView) {
        private val dirPath = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES).path + "/" + itemView.context.getString(R.string.app_name)

        fun storyView(story: com.ramadan.islami.data.model.Story) {
            Picasso.get().load(story.imgUrl).error(R.drawable.error_img)
                .placeholder(R.drawable.failure_img).into(itemView.sliderImg)
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, Story::class.java)
                val bundle = Bundle()
                bundle.putString("prophetName", story.displayName)
                bundle.putStringArrayList("text", story.text)
                intent.putExtras(bundle)
                itemView.context.startActivity(intent)
            }
        }

        fun categoryView(category: Category) {
            Picasso.get().load(category.imgUrl).error(R.drawable.error_img)
                .placeholder(R.drawable.failure_img).into(itemView.sliderImg)
            itemView.setOnClickListener {
                Intent(itemView.context, Quote::class.java).apply {
                    putExtra("title", category.displayName)
                    putExtra("category", category.name)
                    itemView.context.startActivity(this)
                }
            }
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