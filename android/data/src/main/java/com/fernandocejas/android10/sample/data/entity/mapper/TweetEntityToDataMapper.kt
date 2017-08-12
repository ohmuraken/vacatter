package com.fernandocejas.android10.sample.data.entity.mapper

import com.fernandocejas.android10.sample.data.entity.TweetEntity
import com.fernandocejas.android10.sample.domain.Tweet
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Mapper class used to transform {@link TweetEntity} (in the data layer) to
 * {@link Tweet} in the domain layer
 *
 * Created on 8/11/17.
 */
@Singleton
class TweetEntityToDataMapper @Inject constructor() {
  fun transform(tweetEntity: TweetEntity): Tweet {
    val tweet = Tweet(tweetEntity.tweetId)
    tweet.mediaUrls = tweetEntity.mediaUrls
    tweet.favoriteCount = tweetEntity.favoriteCount
    tweet.retweetCount = tweetEntity.retweetCount
    tweet.userId = tweetEntity.userId
    tweet.name = tweetEntity.name
    tweet.favorited = tweetEntity.favorited
    tweet.retweeted = tweetEntity.retweeted
    tweet.text = tweetEntity.text
    tweet.faceChangeUrls = tweetEntity.faceChangeUrls
    return tweet
  }

  fun transform(tweetEntityCollection: Collection<TweetEntity>): List<Tweet> {
    val tweetList: List<Tweet> = mutableListOf()
    tweetEntityCollection.forEach {
      val tweet: Tweet = transform(it)
      tweetList.plus(tweet)
    }
    return tweetList
  }
}