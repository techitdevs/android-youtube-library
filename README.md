# TechIT YouTube Player Library

[![Maven Central](https://img.shields.io/maven-central/v/com.service.techityoutube/youtube-player.svg)](https://search.maven.org/artifact/com.service.techityoutube/youtube-player)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)

A lightweight, efficient YouTube player library for Android. Built with WebView and YouTube IFrame Player API for optimal performance and compatibility.

## ✨ Features

- 🎥 **Full YouTube Player Support** - Complete implementation of YouTube IFrame Player API
- 📱 **Lightweight** - Minimal dependencies and small footprint
- 🎮 **Full Player Control** - Play, pause, seek, volume control, and more
- 🎯 **Lifecycle Aware** - Automatic pause/resume with Android lifecycle
- ⚡ **High Performance** - Optimized WebView-based implementation
- 🛡️ **Production Ready** - Comprehensive error handling and edge cases
- 📖 **Simple Integration** - Easy to integrate with any Android project

## 🚀 Quick Start

### Installation

Add the dependency to your `build.gradle.kts` file:

```kotlin
dependencies {
    implementation("com.service.techityoutube:youtube-player:1.0.0")
}
```

### Basic Usage

```kotlin
import com.service.techityoutube.player.views.YouTubePlayerView
import com.service.techityoutube.player.listeners.AbstractYouTubePlayerListener
import com.service.techityoutube.player.options.IFramePlayerOptions

// In your Activity or Fragment
val youTubePlayerView = YouTubePlayerView(this)
youTubePlayerView.initialize(object : AbstractYouTubePlayerListener() {
    override fun onReady(youTubePlayer: YouTubePlayer) {
        youTubePlayer.loadVideo("dQw4w9WgXcQ", 0f) // Rick Astley - Never Gonna Give You Up
    }
})

// Add to your layout
container.addView(youTubePlayerView)
```

### Compose Integration

```kotlin
@Composable
fun YouTubePlayerComposable(videoId: String) {
    AndroidView(
        factory = { context ->
            YouTubePlayerView(context).apply {
                initialize(object : AbstractYouTubePlayerListener() {
                    override fun onReady(player: YouTubePlayer) {
                        player.loadVideo(videoId, 0f)
                    }
                })
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}
```

## 📚 API Reference

### YouTubePlayerView

The main WebView-based YouTube player component.

```kotlin
class YouTubePlayerView(context: Context) : WebView(context), YouTubePlayer {
    fun initialize(listener: YouTubePlayerListener, options: IFramePlayerOptions = IFramePlayerOptions())
    fun getYouTubePlayerWhenReady(callback: (YouTubePlayer) -> Unit)
    fun release()
}
```

**Methods:**
- `initialize()`: Initialize the player with a listener and options
- `getYouTubePlayerWhenReady()`: Get player instance when ready
- `release()`: Clean up resources

### YouTubePlayer Interface

Main interface for controlling video playback:

```kotlin
interface YouTubePlayer {
    fun loadVideo(videoId: String, startSeconds: Float)
    fun cueVideo(videoId: String, startSeconds: Float)
    fun play()
    fun pause()
    fun stop()
    fun seekTo(seconds: Float)
    fun setVolume(volume: Float) // 0.0 to 1.0
    fun addListener(listener: YouTubePlayerListener)
    fun removeListener(listener: YouTubePlayerListener)
}
```

### Player Configuration

Configure the player using `IFramePlayerOptions`:

```kotlin
val options = IFramePlayerOptions.Builder()
    .controls(1)        // Show player controls
    .autoplay(0)        // Don't autoplay
    .fullscreen(1)      // Allow fullscreen
    .rel(0)             // Don't show related videos
    .modestbranding(1)  // Modest YouTube branding
    .build()

youTubePlayerView.initialize(listener, options)
```

### Event Handling

Listen to player events using `YouTubePlayerListener`:

```kotlin
player.addListener(object : AbstractYouTubePlayerListener() {
    override fun onReady(youTubePlayer: YouTubePlayer) {
        // Player is ready
    }
    
    override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerState) {
        when (state) {
            PlayerState.PLAYING -> { /* Video is playing */ }
            PlayerState.PAUSED -> { /* Video is paused */ }
            PlayerState.ENDED -> { /* Video ended */ }
            PlayerState.BUFFERING -> { /* Video is buffering */ }
            else -> { /* Other states */ }
        }
    }
    
    override fun onError(youTubePlayer: YouTubePlayer, error: String) {
        // Handle errors
    }
})
```

## 🛠️ Configuration

### Required Permissions

Add these permissions to your `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

### ProGuard/R8

If you're using ProGuard or R8, add these rules:

```proguard
# Keep YouTube player classes
-keep class com.service.techityoutube.** { *; }

# Keep WebView JavaScript interface methods
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
```

## 🏗️ Architecture

The library is built with modern Android architecture principles:

- **WebView + JavaScript Bridge** - Reliable YouTube IFrame API communication
- **Lifecycle Awareness** - Automatic resource management
- **Minimal Dependencies** - Lightweight and efficient
- **Event-Driven** - Comprehensive player event handling

## 🧪 Building the Library

Build and test the library:

```bash
git clone https://github.com/yourusername/techit-youtube-lib.git
cd techit-youtube-lib
./gradlew :youtube-player:assembleRelease
```

## 🧪 Testing

Run the test suite:

```bash
./gradlew test
./gradlew connectedAndroidTest
```

## 📋 Requirements

- **Minimum SDK**: API 21 (Android 5.0)
- **Target SDK**: API 34 (Android 14)
- **Kotlin**: 1.9.22+

## 📄 License

```
Copyright 2024 TechIT

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## 🤝 Contributing

Contributions are welcome! Please read our [Contributing Guidelines](CONTRIBUTING.md) for details.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📞 Support

- 📧 **Email**: support@techit.com
- 🐛 **Issues**: [GitHub Issues](https://github.com/yourusername/techit-youtube-lib/issues)
- 📖 **Documentation**: [Full Documentation](https://techit.com/youtube-player-docs)
- 💬 **Discussions**: [GitHub Discussions](https://github.com/yourusername/techit-youtube-lib/discussions)

## 🗺️ Roadmap

- [ ] ExoPlayer integration option
- [ ] Picture-in-Picture support
- [ ] Analytics integration
- [ ] Live streaming support

---

<div align="center">
  <b>Built with ❤️ by TechIT</b>
</div> 