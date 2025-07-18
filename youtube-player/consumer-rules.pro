# Consumer rules for YouTube Player Library

# Keep YouTube player classes
-keep class com.service.techityoutube.** { *; }

# Keep WebView JavaScript interface methods
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
} 