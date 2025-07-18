package com.service.techityoutube.player.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.service.techityoutube.player.PlayerState
import com.service.techityoutube.player.YouTubePlayer
import com.service.techityoutube.player.listeners.YouTubePlayerListener
import com.service.techityoutube.player.options.IFramePlayerOptions

/**
 * WebView-based YouTube player that provides YouTube IFrame Player API functionality
 */
class YouTubePlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : WebView(context, attrs, defStyleAttr), YouTubePlayer, LifecycleEventObserver {

    private val listeners = mutableListOf<YouTubePlayerListener>()
    private var isPlayerReady = false
    private var currentVideoId: String? = null
    private var playerOptions: IFramePlayerOptions? = null
    
    var enableAutomaticInitialization = true
    var lifecycle: Lifecycle? = null
        set(value) {
            field?.removeObserver(this)
            value?.addObserver(this)
            field = value
        }

    init {
        setupWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            builtInZoomControls = false
            displayZoomControls = false
            setSupportZoom(false)
            allowFileAccess = false
            allowContentAccess = false
            setGeolocationEnabled(false)
            mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
        }

        webChromeClient = WebChromeClient()
        webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (isPlayerReady) {
                    // Player is ready, notify listeners
                    listeners.forEach { it.onReady(this@YouTubePlayerView) }
                }
            }
        }

        addJavascriptInterface(YouTubePlayerBridge(), "YouTubePlayerBridge")
    }

    /**
     * Initialize the YouTube player with the given listener and options
     */
    fun initialize(listener: YouTubePlayerListener, options: IFramePlayerOptions = IFramePlayerOptions()) {
        addListener(listener)
        playerOptions = options
        loadYouTubePlayer(options)
    }

    private fun loadYouTubePlayer(options: IFramePlayerOptions) {
        val html = createYouTubePlayerHtml(options)
        loadDataWithBaseURL("https://www.youtube.com", html, "text/html", "utf-8", null)
    }

    private fun createYouTubePlayerHtml(options: IFramePlayerOptions): String {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <style>
                    body { margin: 0; padding: 0; background-color: #000; }
                    #player { width: 100%; height: 100vh; }
                </style>
            </head>
            <body>
                <div id="player"></div>
                <script>
                    var tag = document.createElement('script');
                    tag.src = "https://www.youtube.com/iframe_api";
                    var firstScriptTag = document.getElementsByTagName('script')[0];
                    firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

                    var player;
                    function onYouTubeIframeAPIReady() {
                        player = new YT.Player('player', {
                            height: '100%',
                            width: '100%',
                            playerVars: {
                                ${options.toUrlParams()}
                            },
                            events: {
                                'onReady': onPlayerReady,
                                'onStateChange': onPlayerStateChange,
                                'onPlaybackQualityChange': onPlaybackQualityChange,
                                'onPlaybackRateChange': onPlaybackRateChange,
                                'onError': onError
                            }
                        });
                    }

                    function onPlayerReady(event) {
                        YouTubePlayerBridge.onReady();
                        
                        // Start sending time updates
                        setInterval(function() {
                            if (player && player.getCurrentTime) {
                                var currentTime = player.getCurrentTime();
                                var duration = player.getDuration();
                                var loadedFraction = player.getVideoLoadedFraction();
                                
                                YouTubePlayerBridge.onCurrentSecond(currentTime);
                                YouTubePlayerBridge.onVideoDuration(duration);
                                YouTubePlayerBridge.onVideoLoadedFraction(loadedFraction);
                            }
                        }, 100);
                    }

                    function onPlayerStateChange(event) {
                        YouTubePlayerBridge.onStateChange(event.data);
                        
                        if (event.data === YT.PlayerState.PLAYING) {
                            var videoData = player.getVideoData();
                            if (videoData && videoData.video_id) {
                                YouTubePlayerBridge.onVideoId(videoData.video_id);
                            }
                        }
                    }

                    function onPlaybackQualityChange(event) {
                        YouTubePlayerBridge.onPlaybackQualityChange(event.data);
                    }

                    function onPlaybackRateChange(event) {
                        YouTubePlayerBridge.onPlaybackRateChange(event.data);
                    }

                    function onError(event) {
                        YouTubePlayerBridge.onError(event.data.toString());
                    }

                    // Player control functions
                    function loadVideo(videoId, startSeconds) {
                        if (player && player.loadVideoById) {
                            player.loadVideoById(videoId, startSeconds);
                        }
                    }

                    function cueVideo(videoId, startSeconds) {
                        if (player && player.cueVideoById) {
                            player.cueVideoById(videoId, startSeconds);
                        }
                    }

                    function play() {
                        if (player && player.playVideo) {
                            player.playVideo();
                        }
                    }

                    function pause() {
                        if (player && player.pauseVideo) {
                            player.pauseVideo();
                        }
                    }

                    function stop() {
                        if (player && player.stopVideo) {
                            player.stopVideo();
                        }
                    }

                    function seekTo(seconds) {
                        if (player && player.seekTo) {
                            player.seekTo(seconds, true);
                        }
                    }

                    function setVolume(volume) {
                        if (player && player.setVolume) {
                            player.setVolume(volume * 100);
                        }
                    }

                    function getCurrentTime() {
                        if (player && player.getCurrentTime) {
                            return player.getCurrentTime();
                        }
                        return 0;
                    }

                    function getDuration() {
                        if (player && player.getDuration) {
                            return player.getDuration();
                        }
                        return 0;
                    }

                    function getPlayerState() {
                        if (player && player.getPlayerState) {
                            return player.getPlayerState();
                        }
                        return -1;
                    }
                </script>
            </body>
            </html>
        """.trimIndent()
    }

    /**
     * Get the YouTube player when ready
     */
    fun getYouTubePlayerWhenReady(callback: (YouTubePlayer) -> Unit) {
        if (isPlayerReady) {
            callback(this)
        } else {
            addListener(object : YouTubePlayerListener {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    removeListener(this)
                    callback(youTubePlayer)
                }
                override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerState) {}
                override fun onPlaybackQualityChange(youTubePlayer: YouTubePlayer, playbackQuality: String) {}
                override fun onPlaybackRateChange(youTubePlayer: YouTubePlayer, playbackRate: String) {}
                override fun onError(youTubePlayer: YouTubePlayer, error: String) {}
                override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {}
                override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {}
                override fun onVideoLoadedFraction(youTubePlayer: YouTubePlayer, loadedFraction: Float) {}
                override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {}
            })
        }
    }

    // YouTubePlayer interface implementations
    override fun loadVideo(videoId: String, startSeconds: Float) {
        currentVideoId = videoId
        evaluateJavascript("loadVideo('$videoId', $startSeconds)", null)
    }

    override fun cueVideo(videoId: String, startSeconds: Float) {
        currentVideoId = videoId
        evaluateJavascript("cueVideo('$videoId', $startSeconds)", null)
    }

    override fun play() {
        evaluateJavascript("play()", null)
    }

    override fun pause() {
        evaluateJavascript("pause()", null)
    }

    override fun stop() {
        evaluateJavascript("stop()", null)
    }

    override fun seekTo(seconds: Float) {
        evaluateJavascript("seekTo($seconds)", null)
    }

    override fun setVolume(volume: Float) {
        evaluateJavascript("setVolume($volume)", null)
    }

    override fun getCurrentTime(): Float {
        // This is synchronous call, in real implementation you might want to use a callback
        return 0f // Placeholder - should be implemented with callback mechanism
    }

    override fun getDuration(): Float {
        // This is synchronous call, in real implementation you might want to use a callback
        return 0f // Placeholder - should be implemented with callback mechanism
    }

    override fun getPlayerState(): PlayerState {
        // This is synchronous call, in real implementation you might want to use a callback
        return PlayerState.UNKNOWN // Placeholder - should be implemented with callback mechanism
    }

    override fun addListener(listener: YouTubePlayerListener) {
        listeners.add(listener)
    }

    override fun removeListener(listener: YouTubePlayerListener) {
        listeners.remove(listener)
    }

    /**
     * Release the player resources
     */
    fun release() {
        lifecycle?.removeObserver(this)
        listeners.clear()
        destroy()
    }

    // Lifecycle observer
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                evaluateJavascript("play()", null)
            }
            Lifecycle.Event.ON_PAUSE -> {
                evaluateJavascript("pause()", null)
            }
            Lifecycle.Event.ON_DESTROY -> {
                release()
            }
            else -> {}
        }
    }

    /**
     * JavaScript bridge for communication between WebView and Android
     */
    private inner class YouTubePlayerBridge {

        @JavascriptInterface
        fun onReady() {
            post {
                isPlayerReady = true
                listeners.forEach { it.onReady(this@YouTubePlayerView) }
            }
        }

        @JavascriptInterface
        fun onStateChange(state: Int) {
            post {
                val playerState = when (state) {
                    -1 -> PlayerState.UNSTARTED
                    0 -> PlayerState.ENDED
                    1 -> PlayerState.PLAYING
                    2 -> PlayerState.PAUSED
                    3 -> PlayerState.BUFFERING
                    5 -> PlayerState.VIDEO_CUED
                    else -> PlayerState.UNKNOWN
                }
                listeners.forEach { it.onStateChange(this@YouTubePlayerView, playerState) }
            }
        }

        @JavascriptInterface
        fun onPlaybackQualityChange(quality: String) {
            post {
                listeners.forEach { it.onPlaybackQualityChange(this@YouTubePlayerView, quality) }
            }
        }

        @JavascriptInterface
        fun onPlaybackRateChange(rate: String) {
            post {
                listeners.forEach { it.onPlaybackRateChange(this@YouTubePlayerView, rate) }
            }
        }

        @JavascriptInterface
        fun onError(error: String) {
            post {
                listeners.forEach { it.onError(this@YouTubePlayerView, error) }
            }
        }

        @JavascriptInterface
        fun onCurrentSecond(second: Float) {
            post {
                listeners.forEach { it.onCurrentSecond(this@YouTubePlayerView, second) }
            }
        }

        @JavascriptInterface
        fun onVideoDuration(duration: Float) {
            post {
                listeners.forEach { it.onVideoDuration(this@YouTubePlayerView, duration) }
            }
        }

        @JavascriptInterface
        fun onVideoLoadedFraction(fraction: Float) {
            post {
                listeners.forEach { it.onVideoLoadedFraction(this@YouTubePlayerView, fraction) }
            }
        }

        @JavascriptInterface
        fun onVideoId(videoId: String) {
            post {
                currentVideoId = videoId
                listeners.forEach { it.onVideoId(this@YouTubePlayerView, videoId) }
            }
        }
    }
} 