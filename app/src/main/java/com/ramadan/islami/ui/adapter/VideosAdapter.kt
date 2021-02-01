package com.ramadan.islami.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Video

internal class VideosAdapter(private val lifecycle: Lifecycle) :
    RecyclerView.Adapter<VideosAdapter.ViewHolder>() {

    private var dataList = mutableListOf<Video>()

    fun setDataList(data: MutableList<Video>) {
        dataList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val youTubePlayerView = LayoutInflater.from(parent.context)
            .inflate(R.layout.video_item, parent, false) as YouTubePlayerView
        lifecycle.addObserver(youTubePlayerView)
        return ViewHolder(youTubePlayerView)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.cueVideo(dataList[position])
    }

    internal class ViewHolder(playerView: YouTubePlayerView) : RecyclerView.ViewHolder(playerView) {
        private var youTubePlayer: YouTubePlayer? = null
        private var currentVideoId: String? = null
        fun cueVideo(video: Video) {
            currentVideoId = video.id
            if (youTubePlayer == null) return
            youTubePlayer?.cueVideo(video.id, 0f)
        }

        init {
            playerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    this@ViewHolder.youTubePlayer = youTubePlayer
                    this@ViewHolder.youTubePlayer?.cueVideo(currentVideoId!!, 0f)
                }
            })
        }
    }
}