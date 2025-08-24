package com.example.network.home_screen.models.user_playlists_response


import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("access")
    val access: String = "",
    @SerializedName("artwork_url")
    val artworkUrl: String = "",
    @SerializedName("available_country_codes")
    val availableCountryCodes: Any? = null,
    @SerializedName("bpm")
    val bpm: Any? = null,
    @SerializedName("comment_count")
    val commentCount: Int = 0,
    @SerializedName("commentable")
    val commentable: Boolean = false,
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("download_count")
    val downloadCount: Int = 0,
    @SerializedName("download_url")
    val downloadUrl: Any? = null,
    @SerializedName("downloadable")
    val downloadable: Boolean = false,
    @SerializedName("duration")
    val duration: Int = 0,
    @SerializedName("embeddable_by")
    val embeddableBy: String = "",
    @SerializedName("favoritings_count")
    val favoritingsCount: Int = 0,
    @SerializedName("genre")
    val genre: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("isrc")
    val isrc: Any? = null,
    @SerializedName("key_signature")
    val keySignature: Any? = null,
    @SerializedName("kind")
    val kind: String = "",
    @SerializedName("label_name")
    val labelName: Any? = null,
    @SerializedName("license")
    val license: String = "",
    @SerializedName("metadata_artist")
    val metadataArtist: Any? = null,
    @SerializedName("monetization_model")
    val monetizationModel: Any? = null,
    @SerializedName("permalink_url")
    val permalinkUrl: String = "",
    @SerializedName("playback_count")
    val playbackCount: Int = 0,
    @SerializedName("policy")
    val policy: Any? = null,
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
    @SerializedName("reposts_count")
    val repostsCount: Int = 0,
    @SerializedName("secret_uri")
    val secretUri: Any? = null,
    @SerializedName("sharing")
    val sharing: String = "",
    @SerializedName("stream_url")
    val streamUrl: String = "",
    @SerializedName("streamable")
    val streamable: Boolean = false,
    @SerializedName("tag_list")
    val tagList: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("uri")
    val uri: String = "",
    @SerializedName("urn")
    val urn: String = "",
    @SerializedName("user")
    val user: User = User(),
    @SerializedName("user_favorite")
    val userFavorite: Boolean = false,
    @SerializedName("user_playback_count")
    val userPlaybackCount: Int = 0,
    @SerializedName("waveform_url")
    val waveformUrl: String = ""
)