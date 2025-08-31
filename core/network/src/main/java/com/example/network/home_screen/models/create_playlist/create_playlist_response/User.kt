package com.example.network.home_screen.models.create_playlist.create_playlist_response


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("avatar_url")
    val avatarUrl: String = "",
    @SerializedName("city")
    val city: String = "",
    @SerializedName("comments_count")
    val commentsCount: Int = 0,
    @SerializedName("country")
    val country: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("description")
    val description: Any? = null,
    @SerializedName("discogs_name")
    val discogsName: Any? = null,
    @SerializedName("first_name")
    val firstName: String = "",
    @SerializedName("followers_count")
    val followersCount: Int = 0,
    @SerializedName("followings_count")
    val followingsCount: Int = 0,
    @SerializedName("full_name")
    val fullName: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("kind")
    val kind: String = "",
    @SerializedName("last_modified")
    val lastModified: String = "",
    @SerializedName("last_name")
    val lastName: String = "",
    @SerializedName("likes_count")
    val likesCount: Int = 0,
    @SerializedName("myspace_name")
    val myspaceName: Any? = null,
    @SerializedName("online")
    val online: Boolean = false,
    @SerializedName("permalink")
    val permalink: String = "",
    @SerializedName("permalink_url")
    val permalinkUrl: String = "",
    @SerializedName("plan")
    val plan: String = "",
    @SerializedName("playlist_count")
    val playlistCount: Int = 0,
    @SerializedName("public_favorites_count")
    val publicFavoritesCount: Int = 0,
    @SerializedName("reposts_count")
    val repostsCount: Int = 0,
    @SerializedName("subscriptions")
    val subscriptions: List<Subscription> = listOf(),
    @SerializedName("track_count")
    val trackCount: Int = 0,
    @SerializedName("uri")
    val uri: String = "",
    @SerializedName("urn")
    val urn: String = "",
    @SerializedName("username")
    val username: String = "",
    @SerializedName("website")
    val website: Any? = null,
    @SerializedName("website_title")
    val websiteTitle: Any? = null
)