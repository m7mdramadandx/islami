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
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Category
import com.ramadan.islami.data.model.Story
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_item.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import kotlin.random.Random
import com.ramadan.islami.ui.activity.Quote as QuoteActivity
import com.ramadan.islami.ui.activity.Story as ActivityStory


class RecycleViewAdapter(val context: Context, val isDashboard: Boolean) :
    RecyclerView.Adapter<RecycleViewAdapter.CustomView>() {
    private var storiesList = mutableListOf<Story>()
    private var categoryList = mutableListOf<Category>()
    private var quotesList = ArrayList<String>()

    fun setStoriesDataList(data: MutableList<Story>) {
        storiesList = data
        notifyDataSetChanged()
    }

    fun setCategoryDataList(data: MutableList<Category>) {
        data.removeAt(1)
        categoryList = data
        notifyDataSetChanged()
    }

    fun setQuotesDataList(data: ArrayList<String>) {
        quotesList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomView {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return CustomView(view)
    }

    override fun getItemCount(): Int {
        return when {
            storiesList.size > 0 -> storiesList.size
            categoryList.size > 0 -> categoryList.size
            else -> 0
        }
    }

    override fun onBindViewHolder(holder: CustomView, position: Int) {
        when {
            storiesList.size > 0 -> holder.storyView(storiesList[position])
            categoryList.size > 0 -> holder.categoryView(categoryList[position])
            else -> holder.quoteView(quotesList[position])
        }
    }

    inner class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dirPath = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES).path + "/" + itemView.context.getString(R.string.app_name)

        fun storyView(story: Story) {
            Picasso.get().load(story.imgUrl).error(R.drawable.failure_img)
                .placeholder(R.drawable.load_img).into(itemView.cardImg)
            if (isDashboard) itemView.cardImg.maxHeight =
                itemView.resources.getDimension(R.dimen.max_card_height).toInt()
            itemView.cardName.text = story.displayName
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ActivityStory::class.java)
                val bundle = Bundle()
                bundle.putString("prophetName", story.displayName)
                bundle.putStringArrayList("text", story.text)
                intent.putExtras(bundle)
                itemView.context.startActivity(intent)
            }
        }

        fun categoryView(category: Category) {
            Picasso.get().load(category.imgUrl).error(R.drawable.error_img)
                .placeholder(R.drawable.failure_img).into(itemView.cardImg)
            if (isDashboard) itemView.cardImg.maxHeight =
                itemView.resources.getDimension(R.dimen.max_card_height_1).toInt()
            itemView.cardName.text = category.displayName
            itemView.setOnClickListener {
                Intent(itemView.context, QuoteActivity::class.java).apply {
                    putExtra("title", category.displayName)
                    putExtra("category", category.name)
                    itemView.context.startActivity(this)
                }
            }
        }

        fun quoteView(quotes: String) {
            Picasso.get().load(quotes).error(R.drawable.error_img)
                .placeholder(R.drawable.failure_img).into(itemView.cardImg)
            itemView.setOnClickListener { showImg(quotes, itemView.context) }
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