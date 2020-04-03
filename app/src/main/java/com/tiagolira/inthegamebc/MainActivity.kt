package com.tiagolira.inthegamebc

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import com.tiagolira.itgbcframework.ITGBCPlayerActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val videoURL = "https://media.inthegame.io/uploads/videos/4engagementvideo.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlayer.setOnClickListener { openPlayerActivity() }
        btnExample.setOnClickListener { openExampleActivity() }
        btnOverlay.setOnClickListener { openOverlayActivity() }
    }

    fun openPlayerActivity() {
        if (!checkIfOnline()) return

        //show the full screen video player
        val intent = Intent(this, ITGBCPlayerActivity::class.java)
        var bundle = Bundle()
        bundle.putString(ITGBCPlayerActivity.VIDEO_PARAM, getString(R.string.videoId))
        bundle.putString(ITGBCPlayerActivity.ACCOUNT_PARAM, getString(R.string.account))
        bundle.putString(ITGBCPlayerActivity.POLICY_KEY_PARAM, getString(R.string.policy))
        bundle.putString(ITGBCPlayerActivity.BROADCASTER_PARAM, "demos")
        intent.putExtras(bundle)
        startActivity(intent)
    }

    fun openExampleActivity() {
        if (!checkIfOnline()) return

        //example activity with a custom implementation
        val intent = Intent(this, ExampleActivity::class.java)
        startActivity(intent)
    }

    fun openOverlayActivity() {
        if (!isOnline(this)) {
            Toast.makeText(this@MainActivity, "No internet connection available", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(this, OverlayActivity::class.java)
        startActivity(intent)
    }

    private fun checkIfOnline() : Boolean {
        val online = isOnline(this)
        if (!online) {
            Toast.makeText(this@MainActivity, "No internet connection available", Toast.LENGTH_SHORT).show()
        }
        return online
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
