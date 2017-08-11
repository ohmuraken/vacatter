package com.fernandocejas.android10.sample.data.repository.datasource

import com.fernandocejas.android10.sample.data.net.Api
import com.fernandocejas.android10.sample.data.net.ApiImpl
import com.fernandocejas.android10.sample.data.net.RestApi
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Testing CloudTweetDataStore
 *
 * Created on 8/10/17.
 */
@RunWith(MockitoJUnitRunner::class)class CloudTweetDataStoreTest {

  private var cloudTweetDataStore: CloudTweetDataStore? = null

  val mockApi = mock<Api>()

  @Before
  fun setUp() {
    cloudTweetDataStore = CloudTweetDataStore(mockApi)
  }

  @Test
  fun testGetTweetListFromApi() {
    cloudTweetDataStore!!.tweetEntityList()
    verify(mockApi).tweetEntityList()
  }
}

