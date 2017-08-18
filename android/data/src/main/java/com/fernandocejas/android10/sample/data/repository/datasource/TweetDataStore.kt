package com.fernandocejas.android10.sample.data.repository.datasource

import com.fernandocejas.android10.sample.data.entity.TweetEntity
import io.reactivex.Completable
import io.reactivex.Observable

/**
 *
 *
 * Created on 8/10/17.
 */
interface TweetDataStore {
  fun postToken(): Completable
  fun getTweetList(): Observable<List<TweetEntity>>
  fun likeTweet(tweetId: String): Completable
}