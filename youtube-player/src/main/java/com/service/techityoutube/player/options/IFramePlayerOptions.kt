package com.service.techityoutube.player.options

/**
 * Configuration options for the YouTube IFrame player
 */
data class IFramePlayerOptions(
    val autoplay: Int = 0,           // 1 = auto-play, 0 = don't auto-play
    val controls: Int = 1,           // 1 = show controls, 0 = hide controls
    val enablejsapi: Int = 1,        // Enable JavaScript API
    val fullscreen: Int = 1,         // 1 = allow fullscreen, 0 = disable fullscreen
    val modestbranding: Int = 1,     // 1 = modest YouTube logo, 0 = normal logo
    val rel: Int = 0,                // 1 = show related videos, 0 = don't show related videos
    val showinfo: Int = 0,           // 1 = show video info, 0 = hide video info
    val fs: Int = 1,                 // 1 = show fullscreen button, 0 = hide fullscreen button
    val cc_load_policy: Int = 0,     // 1 = show captions by default, 0 = don't show captions
    val iv_load_policy: Int = 3,     // 1 = show video annotations, 3 = hide video annotations
    val origin: String = "",         // Origin domain for security
    val playsinline: Int = 1         // 1 = play inline on iOS, 0 = fullscreen on iOS
) {
    
    companion object {
        /**
         * Creates a Builder for IFramePlayerOptions
         */
        fun Builder() = IFramePlayerOptionsBuilder()
    }
    
    /**
     * Convert options to URL parameters string
     */
    fun toUrlParams(): String {
        val params = mutableListOf<String>()
        
        params.add("autoplay=$autoplay")
        params.add("controls=$controls")
        params.add("enablejsapi=$enablejsapi")
        params.add("fullscreen=$fullscreen")
        params.add("modestbranding=$modestbranding")
        params.add("rel=$rel")
        params.add("showinfo=$showinfo")
        params.add("fs=$fs")
        params.add("cc_load_policy=$cc_load_policy")
        params.add("iv_load_policy=$iv_load_policy")
        params.add("playsinline=$playsinline")
        
        if (origin.isNotEmpty()) {
            params.add("origin=$origin")
        }
        
        return params.joinToString("&")
    }
}

/**
 * Builder class for IFramePlayerOptions
 */
class IFramePlayerOptionsBuilder {
    private var autoplay: Int = 0
    private var controls: Int = 1
    private var enablejsapi: Int = 1
    private var fullscreen: Int = 1
    private var modestbranding: Int = 1
    private var rel: Int = 0
    private var showinfo: Int = 0
    private var fs: Int = 1
    private var cc_load_policy: Int = 0
    private var iv_load_policy: Int = 3
    private var origin: String = ""
    private var playsinline: Int = 1
    
    fun autoplay(autoplay: Int) = apply { this.autoplay = autoplay }
    fun controls(controls: Int) = apply { this.controls = controls }
    fun enablejsapi(enablejsapi: Int) = apply { this.enablejsapi = enablejsapi }
    fun fullscreen(fullscreen: Int) = apply { this.fullscreen = fullscreen }
    fun modestbranding(modestbranding: Int) = apply { this.modestbranding = modestbranding }
    fun rel(rel: Int) = apply { this.rel = rel }
    fun showinfo(showinfo: Int) = apply { this.showinfo = showinfo }
    fun fs(fs: Int) = apply { this.fs = fs }
    fun cc_load_policy(cc_load_policy: Int) = apply { this.cc_load_policy = cc_load_policy }
    fun iv_load_policy(iv_load_policy: Int) = apply { this.iv_load_policy = iv_load_policy }
    fun origin(origin: String) = apply { this.origin = origin }
    fun playsinline(playsinline: Int) = apply { this.playsinline = playsinline }
    
    fun build() = IFramePlayerOptions(
        autoplay = autoplay,
        controls = controls,
        enablejsapi = enablejsapi,
        fullscreen = fullscreen,
        modestbranding = modestbranding,
        rel = rel,
        showinfo = showinfo,
        fs = fs,
        cc_load_policy = cc_load_policy,
        iv_load_policy = iv_load_policy,
        origin = origin,
        playsinline = playsinline
    )
} 