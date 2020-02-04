package com.tiagolira.inthegamebc

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brightcove.player.view.BrightcovePlayer
import kotlinx.android.synthetic.main.activity_example.*

class ExampleActivity : BrightcovePlayer() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onResume() {
        super.onResume()

        //load the interactive video view
        playerView.load(
            getString(R.string.videoId),
            getString(R.string.account),
            getString(R.string.policy),
            "demos")

        //example of a custom configuration
//        playerView.loadManualConfig(getString(R.string.videoId),"demos")
//
//        val emitter = playerView.bcVideoView.eventEmitter
//        val catalog = Catalog(emitter, getString(R.string.account), getString(R.string.policy))
//
//        catalog.findVideoByID(getString(R.string.videoId), (object : VideoListener() {
//            override fun onVideo(video: Video?) {
//                if (video != null) {
//                    playerView.bcVideoView.add(video)
//                    playerView.bcVideoView.start()
//                }
//            }
//
//            override fun onError(error: String?) {
//                throw RuntimeException(error)
//            }
//        }))
    }

}