package com.service.techityoutube.sample.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.service.techityoutube.YouTubePlayer
import com.service.techityoutube.listeners.AbstractYouTubePlayerListener
import com.service.techityoutube.options.IFramePlayerOptions
import com.service.techityoutube.views.YouTubePlayerView
import com.service.techityoutube.sample.view.theme.TechITYouTubeSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TechITYouTubeSampleTheme {
                Surface(
                    modifier = Modifier.Companion.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val opts = IFramePlayerOptions.Builder()
                        .controls(1)
                        .fullscreen(1) // Enable fullscreen for better live stream experience
                        .autoplay(1)
                        .rel(0)
                        //.playsinline(0) // Important for some live streams
                        .build()
                    YouTubePlayerView(this).apply {
                        enableAutomaticInitialization = false // Changed to true for better reliability
                       // enableLogs = true // Enable debugging
                        keepScreenOn = true

                        initialize(object : AbstractYouTubePlayerListener() {
                            override fun onReady(player: YouTubePlayer) {
                                player.loadVideo("uHq9km2E6rk", 0f)
                            }

                            override fun onError(youTubePlayer: YouTubePlayer, error: String) {
                                Log.e("YouTubeError", "Player error: ${error}")
                            }

                        }, opts)
                    }
                }
            }
        }
    }
}