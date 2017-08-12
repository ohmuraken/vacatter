package com.fernandocejas.android10.sample.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Tweet Entity used in the data layer.
 *
 * Created on 8/9/17
 */
class TweetEntity {

  @SerializedName("id")
  var tweetId: String = ""

  @SerializedName("media_urls")
  var mediaUrls: List<String> = listOf<String>()

  @SerializedName("favorite_count")
  var favoriteCount: Int = 0

  @SerializedName("retweet_count")
  var retweetCount: Int = 0

  @SerializedName("user_id")
  var userId: String = ""

  @SerializedName("name")
  var name: String = ""

  @SerializedName("retweeted")
  var favorited: Boolean = false

  @SerializedName("favorited")
  var retweeted: Boolean = false

  @SerializedName("text")
  var text: String = ""

  @SerializedName("face_change_urls")
  var faceChangeUrls: List<String> = listOf<String>()
}