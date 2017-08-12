package com.fernandocejas.android10.sample.data.repository.datasource

import com.fernandocejas.android10.sample.data.entity.TweetEntity
import com.fernandocejas.android10.sample.data.net.Api
import com.fernandocejas.android10.sample.data.net.ApiImpl
import io.reactivex.Observable
import javax.inject.Inject

/**
 * [TweetDataStore] implementation based on connections to the api (Cloud).
 */
class CloudTweetDataStore @Inject constructor(var api: Api) : TweetDataStore {
  override fun tweetEntityList(): Observable<List<TweetEntity>> {
    return this.api.tweetEntityList()
  }
}