package com.silverorange.videoplayer.model

data class VideoListItem(
    val author: Author,
    val description: String,
    val fullURL: String,
    val hlsURL: String,
    val id: String,
    val publishedAt: String,
    val title: String
)