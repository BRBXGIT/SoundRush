package com.example.network.home_screen.models.user_playlists_response


import com.google.gson.annotations.SerializedName

data class Collection(
    @SerializedName("artwork_url")
    val artworkUrl: String? = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("downloadable")
    val downloadable: Boolean? = false,
    @SerializedName("duration")
    val duration: Int = 0,
    @SerializedName("ean")
    val ean: Any? = Any(),
    @SerializedName("embeddable_by")
    val embeddableBy: String = "",
    @SerializedName("genre")
    val genre: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("kind")
    val kind: String = "",
    @SerializedName("label")
    val label: Any? = Any(),
    @SerializedName("label_id")
    val labelId: Any? = Any(),
    @SerializedName("label_name")
    val labelName: Any? = Any(),
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
    val purchaseTitle: Any? = Any(),
    @SerializedName("purchase_url")
    val purchaseUrl: Any? = Any(),
    @SerializedName("release")
    val release: Any? = Any(),
    @SerializedName("release_day")
    val releaseDay: Any? = Any(),
    @SerializedName("release_month")
    val releaseMonth: Any? = Any(),
    @SerializedName("release_year")
    val releaseYear: Any? = Any(),
    @SerializedName("repost_count")
    val repostCount: Int = 0,
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
    val tracks: List<Track> = listOf(),
    @SerializedName("tracks_uri")
    val tracksUri: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("uri")
    val uri: String = "",
    @SerializedName("urn")
    val urn: String = "",
    @SerializedName("user")
    val user: UserX = UserX(),
    @SerializedName("user_id")
    val userId: Int = 0,
    @SerializedName("user_urn")
    val userUrn: String = ""
)