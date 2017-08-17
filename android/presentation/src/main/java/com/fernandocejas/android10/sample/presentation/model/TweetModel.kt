package com.fernandocejas.android10.sample.presentation.model

import java.math.BigInteger

/**
 *
 *
 * Created on 8/14/17.
 */
class TweetModel(val tweetId: Long?) {
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