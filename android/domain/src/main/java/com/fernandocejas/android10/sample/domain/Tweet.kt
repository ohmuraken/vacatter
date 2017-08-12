package com.fernandocejas.android10.sample.domain

/**
 * Class that represents a Tweet in the domain layer.
 */
class Tweet(val tweetId: String) {
  var favoriteCount: Int? = 0
  var retweetCount: Int? = 0
  var userId: String? = null
  var name: String? = null
  var favorited: Boolean? = null
  var retweeted: Boolean? = null
  var text: String? = null
  var mediaUrls: List<String>? = null
  var faceChangeUrls: List<String>? = null
}
