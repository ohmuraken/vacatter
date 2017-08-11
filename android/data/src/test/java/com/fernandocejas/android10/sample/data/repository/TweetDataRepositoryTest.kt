package com.fernandocejas.android10.sample.data.repository

import com.fernandocejas.android10.sample.data.entity.TweetEntity
import com.fernandocejas.android10.sample.data.entity.mapper.TweetEntityToDataMapper
import com.fernandocejas.android10.sample.data.repository.datasource.CloudTweetDataStore
import com.fernandocejas.android10.sample.data.repository.datasource.TweetDataStoreFactory
import com.nhaarman.mockito_kotlin.given
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner


/**
 * Testing DataRepository
 *
 * Created on 8/9/17.
 */
@RunWith(PowerMockRunner::class)
@PrepareForTest(TweetDataRepository::class, TweetDataStoreFactory::class,
    TweetEntityToDataMapper::class, CloudTweetDataStore::class)
class TweetDataRepositoryTest {

  val FAKE_TWEET_ID: String = "100123"

  var mockCloudTweetDataStore = PowerMockito.mock(CloudTweetDataStore::class.java)
  var mockTweetDataStoreFactory = PowerMockito.mock(TweetDataStoreFactory::class.java)
  var mockTweetEntityToDataMapper = PowerMockito.mock(TweetEntityToDataMapper::class.java)
  var tweetDataRepository: TweetDataRepository = PowerMockito.spy(
      TweetDataRepository(mockTweetDataStoreFactory, mockTweetEntityToDataMapper))

  @Before
  fun setUp() {
    given(mockTweetDataStoreFactory.createcloudDataStore()).willReturn(mockCloudTweetDataStore)
  }

  @Test
  fun testGetTweetsHappyCase() {
    val tweetsList: List<TweetEntity> = mutableListOf()
    val tweetEntity = TweetEntity()
    tweetEntity.tweetId = "AAAA"
    tweetsList.plus(tweetEntity)
    given(mockCloudTweetDataStore.tweetEntityList()).willReturn(Observable.just(tweetsList))

    tweetDataRepository.tweets()
  }
}
