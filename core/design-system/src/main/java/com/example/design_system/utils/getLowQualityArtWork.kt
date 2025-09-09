package com.example.design_system.utils

fun getLowQualityArtwork(artworkUrl: String?): String? {
    return artworkUrl?.replace("t67x67", "large")
}