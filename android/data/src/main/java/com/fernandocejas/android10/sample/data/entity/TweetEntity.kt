package com.fernandocejas.android10.sample.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Tweet Entity used in the data layer.
 *
 * Created on 8/9/17.
 */
data class TweetEntity(
    @SerializedName("id") val tweetId: String,
    @SerializedName("media_urls") val mediaUrls: List<String>,
    @SerializedName("favorite_count") val favoriteCount: Int,
    @SerializedName("retweet_count") val retweetCount: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("retweeted") val favorited: Boolean,
    @SerializedName("favorited") val retweeted: Boolean,
    @SerializedName("text") val text: String,
    @SerializedName("face_change_urls") val faceChangeUrls: List<String>?
)