package com.inthegame.inthegamebc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.brightcove.player.edge.Catalog
import com.brightcove.player.edge.VideoListener
import com.brightcove.player.model.Video
import com.brightcove.player.view.BrightcovePlayer
import com.inthegame.inthegamebc.R
import com.tiagolira.itgbcframework.ITGOverlayView
import com.tiagolira.itgbcframework.ITGVideoTapListener
import kotlinx.android.synthetic.main.activity_overlay.*

class OverlayActivity : BrightcovePlayer() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overlay)
        brightcoveVideoView = videoView

        btnPlay.setOnClickListener { play() }
        btnPause.setOnClickListener { pause() }
        btnForward.setOnClickListener { forward() }
    }

    override fun onResume() {
        super.onResume()

        //create a listener to detect when the user taps the empty video area
        //(to show any custom controls, for example)
        val listener = object: ITGVideoTapListener {
            override fun didTapVideo() {
                Log.d("ITG", "did tap video area")
            }

        }

        //load the overlay
        overlayView.load(getString(R.string.videoId), "demos", "en", listener)


        //load the video player
        val emitter = videoView.eventEmitter
        val catalog = Catalog(emitter, getString(R.string.account), getString(
            R.string.policy
        ))

        catalog.findVideoByID(getString(R.string.videoId), (object : VideoListener() {
            override fun onVideo(video: Video?) {
                if (video != null) {
                    videoView.add(video)
                    videoView.start()
                    //tell the overlay that playback has started
                    overlayView.sendCommand(ITGOverlayView.COMMAND_PLAY)
                }
            }

            override fun onError(error: String?) {
                throw RuntimeException(error)
            }
        }))
    }

    //when playback commands are sent to the video player,
    //we need to inform the overlay
    //this includes actions such as
    //play, pause, stop and update video time
    fun play() {
        if (!videoView.isPlaying) {
            videoView.start()
            overlayView.sendCommand(ITGOverlayView.COMMAND_PLAY)
        }
    }

    fun pause() {
        if (videoView.isPlaying) {
            videoView.pause()
            overlayView.sendCommand(ITGOverlayView.COMMAND_PAUSE)
        }
    }

    fun forward() {
        val timeMilli = videoView.currentPosition
        val newTime = timeMilli + 10000
        videoView.seekTo(newTime)
        overlayView.updateTime(newTime)
    }
}
