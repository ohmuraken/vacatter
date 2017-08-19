package com.fernandocejas.android10.sample.presentation.mapper

import com.fernandocejas.android10.sample.domain.Tweet
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity
import com.fernandocejas.android10.sample.presentation.model.TweetModel
import javax.inject.Inject

/**
 *
 *
 * Created on 8/14/17.
 */
@PerActivity class TweetModelDataMapper @Inject constructor() {

  /**
   * Transform a {@link Tweet} into an {@link TweetModel}.
   *
   * @param tweet Object to be transformed.
   * @return {@link TweetModel}.
   */
  fun transform(tweet: Tweet): TweetModel {
    val tweetModel = TweetModel(tweet.tweetId)
    tweetModel.mediaUrls = tweet.mediaUrls
    tweetModel.favoriteCount = tweet.favoriteCount
    tweetModel.retweetCount = tweet.retweetCount
    tweetModel.userId = tweet.userId
    tweetModel.name = tweet.name
    tweetModel.screenName = tweet.screenName
    tweetModel.favorited = tweet.favorited
    tweetModel.retweeted = tweet.retweeted
    tweetModel.text = tweet.text
    tweetModel.faceChangeUrls = tweet.faceChangeUrls
    return tweetModel
  }

  /**
   * Transform a Collection of {@link Tweet} into a Collection of {@link TweetModel}.
   *
   * @param tweetCollection Objects to be transformed.
   * @return List of {@link TweetModel}.
   */
  fun transform(tweetCollection: Collection<Tweet>): Collection<TweetModel> {
    val tweetModelCollection: MutableList<TweetModel> = mutableListOf<TweetModel>()
    tweetCollection.forEach {
      val tweetModel: TweetModel = transform(it)
      tweetModelCollection.add(tweetModel)
    }
    return tweetModelCollection
  }
}