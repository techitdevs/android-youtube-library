package com.service.techityoutube.player.listeners

import com.service.techityoutube.player.PlayerState
import com.service.techityoutube.player.YouTubePlayer

/**
 * Interface for listening to YouTube player events
 */
interface YouTubePlayerListener {
    
    /**
     * Called when the player is ready to accept function calls
     */
    fun onReady(youTubePlayer: YouTubePlayer)
    
    /**
     * Called when the player state changes
     */
    fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerState)
    
    /**
     * Called when the video quality changes
     */
    fun onPlaybackQualityChange(youTubePlayer: YouTubePlayer, playbackQuality: String)
    
    /**
     * Called when the playback rate changes
     */
    fun onPlaybackRateChange(youTubePlayer: YouTubePlayer, playbackRate: String)
    
    /**
     * Called when an error occurs
     */
    fun onError(youTubePlayer: YouTubePlayer, error: String)
    
    /**
     * Called when the current time of the video changes
     */
    fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float)
    
    /**
     * Called when the video duration is available
     */
    fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float)
    
    /**
     * Called when the video is loaded
     */
    fun onVideoLoadedFraction(youTubePlayer: YouTubePlayer, loadedFraction: Float)
    
    /**
     * Called when the video ID changes
     */
    fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String)
} 