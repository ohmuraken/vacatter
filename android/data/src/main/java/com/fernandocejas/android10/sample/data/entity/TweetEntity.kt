package com.fernandocejas.android10.sample.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Tweet Entity used in the data layer.
 *
 * Created on 8/9/17
 */
class TweetEntity {

  @SerializedName("tweet_id")
  var tweetId: Long = 0

  @SerializedName("media_urls")
  var mediaUrls: List<String> = listOf<String>()

  @SerializedName("favorite_count")
  var favoriteCount: Int = 0

  @SerializedName("retweet_count")
  var retweetCount: Int = 0

  @SerializedName("user_id")
  var userId: Long = 0

  @SerializedName("screen_name")
  var screenName: String = ""

  @SerializedName("name")
  var name: String = ""

  @SerializedName("favorited")
  var favorited: Int = 0

  @SerializedName("retweeted")
  var retweeted: Int = 0

  @SerializedName("text")
  var text: String = ""

  @SerializedName("face_change_urls")
  var faceChangeUrls: List<String> = listOf<String>()
}