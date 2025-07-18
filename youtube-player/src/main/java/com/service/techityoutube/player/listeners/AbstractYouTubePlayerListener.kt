package com.service.techityoutube.player.listeners

import com.service.techityoutube.player.PlayerState
import com.service.techityoutube.player.YouTubePlayer

/**
 * Abstract implementation of YouTubePlayerListener with empty default implementations.
 * Subclasses only need to override the methods they're interested in.
 */
abstract class AbstractYouTubePlayerListener : YouTubePlayerListener {
    
    override fun onReady(youTubePlayer: YouTubePlayer) {
        // Default implementation - do nothing
    }
    
    override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerState) {
        // Default implementation - do nothing
    }
    
    override fun onPlaybackQualityChange(youTubePlayer: YouTubePlayer, playbackQuality: String) {
        // Default implementation - do nothing
    }
    
    override fun onPlaybackRateChange(youTubePlayer: YouTubePlayer, playbackRate: String) {
        // Default implementation - do nothing
    }
    
    override fun onError(youTubePlayer: YouTubePlayer, error: String) {
        // Default implementation - do nothing
    }
    
    override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
        // Default implementation - do nothing
    }
    
    override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
        // Default implementation - do nothing
    }
    
    override fun onVideoLoadedFraction(youTubePlayer: YouTubePlayer, loadedFraction: Float) {
        // Default implementation - do nothing
    }
    
    override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
        // Default implementation - do nothing
    }
} 