package com.fernandocejas.android10.sample.domain.repository

import com.fernandocejas.android10.sample.domain.Tweet
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Interface that represents a Repository for getting [Tweet] related data.
 */
interface TweetRepository {

  fun token(): Completable

  /**
   * Get an [Observable] which will emit a List of [Tweet].
   */
  fun tweets(): Observable<List<Tweet>>

  fun like(tweet_id: String): Completable
}