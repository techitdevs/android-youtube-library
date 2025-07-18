package com.service.techityoutube.player

import com.service.techityoutube.player.listeners.YouTubePlayerListener

/**
 * Main interface for controlling YouTube video playback
 */
interface YouTubePlayer {
    
    /**
     * Load and play a YouTube video
     * @param videoId The YouTube video ID
     * @param startSeconds Starting position in seconds
     */
    fun loadVideo(videoId: String, startSeconds: Float)
    
    /**
     * Cue a YouTube video (load but don't play)
     * @param videoId The YouTube video ID
     * @param startSeconds Starting position in seconds
     */
    fun cueVideo(videoId: String, startSeconds: Float)
    
    /**
     * Play the current video
     */
    fun play()
    
    /**
     * Pause the current video
     */
    fun pause()
    
    /**
     * Stop the current video
     */
    fun stop()
    
    /**
     * Seek to a specific time in the video
     * @param seconds Time in seconds
     */
    fun seekTo(seconds: Float)
    
    /**
     * Set the volume (0.0 to 1.0)
     * @param volume Volume level
     */
    fun setVolume(volume: Float)
    
    /**
     * Get the current playback time
     */
    fun getCurrentTime(): Float
    
    /**
     * Get the total duration of the video
     */
    fun getDuration(): Float
    
    /**
     * Get the current player state
     */
    fun getPlayerState(): PlayerState
    
    /**
     * Add a listener for player events
     */
    fun addListener(listener: YouTubePlayerListener)
    
    /**
     * Remove a listener
     */
    fun removeListener(listener: YouTubePlayerListener)
}

/**
 * Represents the current state of the YouTube player
 */
enum class PlayerState {
    UNKNOWN,
    UNSTARTED,
    ENDED,
    PLAYING,
    PAUSED,
    BUFFERING,
    VIDEO_CUED
} 