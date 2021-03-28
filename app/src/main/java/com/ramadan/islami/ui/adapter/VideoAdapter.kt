package com.ramadan.islami.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.ramadan.islami.R
import com.ramadan.islami.data.model.Video
import com.ramadan.islami.ui.activity.VideoDetails
import kotlinx.android.synthetic.main.item_tile.view.*
import kotlinx.android.synthetic.main.item_video.view.*

internal class VideoAdapter(private val lifecycle: Lifecycle) :
    RecyclerView.Adapter<VideoAdapter.CustomView>() {

    private var videosSectionsList = mutableListOf<Video>()
    private var videosList = mutableListOf<String>()

    fun setVideoSectionsList(data: MutableList<Video>) {
        videosSectionsList = data
        notifyDataSetChanged()
    }

    fun setVideoList(data: MutableList<String>) {
        videosList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomView {
        return if (videosSectionsList.size > 0) {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_tile, parent, false)
            CustomView(view)
        } else {
            val youTubePlayerView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_video, parent, false)
//            lifecycle.addObserver(youTubePlayerView)
            CustomView(youTubePlayerView)
        }
    }

    override fun onBindViewHolder(holder: CustomView, position: Int) {
        when {
            videosSectionsList.size > 0 -> holder.videosSectionsView(videosSectionsList[position])
            videosList.size > 0 -> holder.cueVideo(videosList[position])
        }
    }

    override fun getItemCount(): Int {
        return when {
            videosSectionsList.size > 0 -> videosSectionsList.size
            videosList.size > 0 -> videosList.size
            else -> 0
        }
    }

    internal class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var playerView: YouTubePlayerView? = null
        private var player: YouTubePlayer? = null
        private var currentVideoId: String? = null

        fun videosSectionsView(video: Video) {
            itemView.tileTitle.text = video.title
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, VideoDetails::class.java)
                intent.putExtra("videoObj", video)
                it.context.startActivity(intent)
            }
        }

        fun cueVideo(string: String) {
            playerView = itemView.youtube_player_view as YouTubePlayerView
            initialize()
            currentVideoId = string
            if (player == null) return
            player?.cueVideo(string, 0f)
        }

        private fun initialize() {
            playerView?.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    player = youTubePlayer
                    player!!.cueVideo(currentVideoId!!, 0f)
                }
            })
        }
    }
}