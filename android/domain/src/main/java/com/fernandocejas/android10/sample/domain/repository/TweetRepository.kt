package com.fernandocejas.android10.sample.domain.repository

import com.fernandocejas.android10.sample.domain.Tweet
import io.reactivex.Observable

/**
 * Interface that represents a Repository for getting [Tweet] related data.
 */
interface TweetRepository {
  /**
   * Get an [Observable] which will emit a List of [Tweet].
   */
  fun tweets(): Observable<List<Tweet>>
}