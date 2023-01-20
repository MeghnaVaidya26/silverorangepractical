package com.silverorange.videoplayer

import MyViewModelFactory
import NetworkUtil
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.silverorange.videoplayer.model.VideoList
import com.silverorange.videoplayer.viewmodel.VideoListViewModel


class MainActivity : AppCompatActivity() {
    var player: ExoPlayer? = null
    var index: Int = 0

    lateinit var videolist: VideoList
    lateinit var playerView: PlayerView;
    var videoListViewModel: VideoListViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        playerView = findViewById(R.id.simpleexoplayer)
        onCLickListeners()
    }

    override fun onStart() {
        super.onStart()
        addObserver()
        if (NetworkUtil.isInternetAvailable(this)) {
            videoListViewModel!!.getAllVideo()
        }
    }

    fun initializePlayer() {
        try {
            player = ExoPlayer.Builder(this).build()
// Bind the player to the view.
// Bind the player to the view.
            playerView.setPlayer(player)
            playerView.setShowNextButton(false)
            playerView.setShowPreviousButton(false)
// Create and add media item
// Create and add media item
            //  val mediaItem: MediaItem = MediaItem.fromUri(video_url)
            for (i in videolist.indices) {
                var songPath: String = videolist.get(i).fullURL
                val item: MediaItem = MediaItem.fromUri(songPath)
                player!!.addMediaItem(item)
            }

// Prepare exoplayer
// Prepare exoplayer
            player!!.prepare()
// Play media when it is ready
// Play media when it is ready
            player!!.playWhenReady = true


        } catch (e: Exception) {
            // below line is used for
            // handling our errors.
            Log.e("TAG", "Error : $e")
        }

    }

    fun onCLickListeners() {

        findViewById<ImageView>(R.id.btpause).setOnClickListener {
            player!!.stop()
            Toast.makeText(this, "play clicked", Toast.LENGTH_SHORT).show()
            findViewById<ImageView>(R.id.btplay).visibility = View.VISIBLE
            findViewById<ImageView>(R.id.btpause).visibility = View.GONE
            Log.v("===play clicked==", "-")
        }

        findViewById<ImageView>(R.id.btplay).setOnClickListener {
            player!!.prepare()
            Toast.makeText(this, "pause clicked", Toast.LENGTH_SHORT).show()
            findViewById<ImageView>(R.id.btplay).visibility = View.GONE
            findViewById<ImageView>(R.id.btpause).visibility = View.VISIBLE
            Log.v("===pause clicked==", "-")

        }

        findViewById<ImageView>(R.id.btnext).setOnClickListener {
            player!!.seekToNextMediaItem()

            player!!.prepare()
            Toast.makeText(this, "next clicked", Toast.LENGTH_SHORT).show()

            Log.v(
                "===next clicked==",
                "-" + player!!.currentPeriodIndex + "-" + player!!.currentMediaItemIndex.toString()
            )
            index = player!!.currentPeriodIndex

            findViewById<TextView>(R.id.tvDescription).setText(videolist.get(index).description)

        }

        findViewById<ImageView>(R.id.btprevious).setOnClickListener {
            player!!.seekToPreviousMediaItem()

            player!!.prepare()
            Toast.makeText(this, "next clicked", Toast.LENGTH_SHORT).show()

            Log.v(
                "===previous clicked==",
                "-" + player!!.currentPeriodIndex + "-" + player!!.currentMediaItemIndex.toString()
            )
            index = player!!.currentPeriodIndex

            index = player!!.currentPeriodIndex
        }
    }

    private fun init() {

        videoListViewModel =
            ViewModelProvider(this, MyViewModelFactory(VideoListViewModel(this))).get(
                VideoListViewModel::class.java
            )
    }

    private fun addObserver() {
        videoListViewModel!!.isLoading!!.observe(this, Observer {
            if (it) {
            } else {
            }
        })

        videoListViewModel!!.responseError!!.observe(this, Observer {
            Log.e("error", "-");

        })

        videoListViewModel!!.videoResponse!!.observe(this, Observer {
            Log.v("===response==", "---" + it)
            videolist = it
            findViewById<TextView>(R.id.tvDescription).setText(videolist.get(index).description)
            initializePlayer()
        })

    }
}