package com.example.design_system.utils

fun getLowQualityArtwork(artworkUrl: String?): String? {
    return artworkUrl?.replace("large", "t67x67")
}