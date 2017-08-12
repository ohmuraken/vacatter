package com.fernandocejas.android10.sample.data.repository.datasource

import com.fernandocejas.android10.sample.data.entity.TweetEntity
import com.fernandocejas.android10.sample.data.net.Api
import io.reactivex.Observable

/**
 *
 *
 * Created on 8/10/17.
 */
interface TweetDataStore {
  fun tweetEntityList(): Observable<List<TweetEntity>>
}