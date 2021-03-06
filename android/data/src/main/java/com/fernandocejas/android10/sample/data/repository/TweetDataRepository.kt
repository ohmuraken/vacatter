package com.fernandocejas.android10.sample.data.repository

import com.fernandocejas.android10.sample.data.entity.mapper.TweetEntityToDataMapper
import com.fernandocejas.android10.sample.data.repository.datasource.TweetDataStore
import com.fernandocejas.android10.sample.data.repository.datasource.TweetDataStoreFactory
import com.fernandocejas.android10.sample.domain.Tweet
import com.fernandocejas.android10.sample.domain.repository.TweetRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * {@link TweetRepository} for retrieving tweet data
 *
 * Created on 8/11/17.
 */
@Singleton class TweetDataRepository @Inject constructor(
    var tweetDataStoreFactory: TweetDataStoreFactory,
    var tweetEntityToDataMapper: TweetEntityToDataMapper) : TweetRepository {

  override fun token(): Completable {
    val tweetCloudDataStore: TweetDataStore = tweetDataStoreFactory.createcloudDataStore()
    return tweetCloudDataStore.postToken()
  }

  override fun tweets(): Observable<List<Tweet>> {
    val tweetCloudDataStore: TweetDataStore = tweetDataStoreFactory.createcloudDataStore()
    return tweetCloudDataStore.getTweetList().map { this.tweetEntityToDataMapper.transform(it) }
  }

  override fun like(tweetId: String): Completable {
    val tweetCloudDataStore: TweetDataStore = tweetDataStoreFactory.createcloudDataStore()
    return tweetCloudDataStore.likeTweet(tweetId)
  }

}
