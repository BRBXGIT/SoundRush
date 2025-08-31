package com.example.network.home_screen.models.create_playlist.create_playlist_response

import com.google.gson.annotations.SerializedName

data class CreatePlaylistResponse(
    @SerializedName("artwork_url")
    val artworkUrl: Any? = null,
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("downloadable")
    val downloadable: Boolean = false,
    @SerializedName("duration")
    val duration: Int = 0,
    @SerializedName("ean")
    val ean: Any? = null,
    @SerializedName("embeddable_by")
    val embeddableBy: String = "",
    @SerializedName("genre")
    val genre: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("kind")
    val kind: String = "",
    @SerializedName("label")
    val label: Any? = null,
    @SerializedName("label_id")
    val labelId: Any? = null,
    @SerializedName("label_name")
    val labelName: Any? = null,
    @SerializedName("last_modified")
    val lastModified: String = "",
    @SerializedName("license")
    val license: String = "",
    @SerializedName("likes_count")
    val likesCount: Int = 0,
    @SerializedName("permalink")
    val permalink: String = "",
    @SerializedName("permalink_url")
    val permalinkUrl: String = "",
    @SerializedName("playlist_type")
    val playlistType: String = "",
    @SerializedName("purchase_title")
    val purchaseTitle: Any? = null,
    @SerializedName("purchase_url")
    val purchaseUrl: Any? = null,
    @SerializedName("release")
    val release: Any? = null,
    @SerializedName("release_day")
    val releaseDay: Any? = null,
    @SerializedName("release_month")
    val releaseMonth: Any? = null,
    @SerializedName("release_year")
    val releaseYear: Any? = null,
    @SerializedName("repost_count")
    val repostCount: Int = 0,
    @SerializedName("secret_token")
    val secretToken: String = "",
    @SerializedName("secret_uri")
    val secretUri: String = "",
    @SerializedName("sharing")
    val sharing: String = "",
    @SerializedName("streamable")
    val streamable: Boolean = false,
    @SerializedName("tag_list")
    val tagList: String = "",
    @SerializedName("tags")
    val tags: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("track_count")
    val trackCount: Int = 0,
    @SerializedName("tracks")
    val tracks: List<Any?> = listOf(),
    @SerializedName("tracks_uri")
    val tracksUri: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("uri")
    val uri: String = "",
    @SerializedName("urn")
    val urn: String = "",
    @SerializedName("user")
    val user: User = User(),
    @SerializedName("user_id")
    val userId: Int = 0,
    @SerializedName("user_urn")
    val userUrn: String = ""
)