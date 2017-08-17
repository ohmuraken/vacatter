package com.fernandocejas.android10.sample.domain

import java.math.BigInteger

/**
 * Class that represents a Tweet in the domain layer.
 */
class Tweet(val tweetId: Long?) {
  var favoriteCount: Int? = 0
  var retweetCount: Int? = 0
  var userId: Long? = null
  var name: String? = null
  var favorited: Int? = 0
  var retweeted: Int? = 0
  var text: String? = null
  var mediaUrls: List<String>? = null
  var faceChangeUrls: List<String>? = null
}
