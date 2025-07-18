package com.service.techityoutube.sample.model


data class YouTubeVideo(
    val id: String,
    val title: String,
    val thumbnailUrl: String,
    val publishedAt: String,
    val description: String = "",
    val duration: String?=""
)