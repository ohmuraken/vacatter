package com.fernandocejas.android10.sample.data.repository.datasource

import com.fernandocejas.android10.sample.data.entity.TweetEntity
import com.fernandocejas.android10.sample.data.net.RetroApi
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

/**
 * [TweetDataStore] implementation based on connections to the api (Cloud).
 */
class CloudTweetDataStore @Inject constructor(
    val api: RetroApi,
    val helper: DataStoreHelper
) : TweetDataStore {

  override fun postToken(): Completable {
    return this.api.postToken(
        helper.getUserId(),
        helper.getToken(),
        helper.getTokenSecret()
    )
  }

  override fun getTweetList(): Observable<List<TweetEntity>> {
    val user_id: String = helper.getUserId()
    return this.api.tweetEntityList(user_id)
  }

  override fun likeTweet(tweetId: String): Completable {
    val user_id: String = helper.getUserId()
    return this.api.likeTweet(user_id, tweetId)
  }
}