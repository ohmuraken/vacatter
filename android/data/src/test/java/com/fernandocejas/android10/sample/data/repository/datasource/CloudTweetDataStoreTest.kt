package com.fernandocejas.android10.sample.data.repository.datasource

import com.fernandocejas.android10.sample.data.net.RetroApi
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 * Testing CloudTweetDataStore
 *
 * Created on 8/10/17.
 */
@RunWith(MockitoJUnitRunner::class)
class CloudTweetDataStoreTest {

  private var cloudTweetDataStore: CloudTweetDataStore? = null

  val mockApi = Mockito.mock(RetroApi::class.java)
  val mockHelper = Mockito.mock(DataStoreHelper::class.java)

  @Before
  fun setUp() {
    cloudTweetDataStore = CloudTweetDataStore(mockApi, mockHelper)
  }

  @Test
  fun testGetTweetListFromApi() {
    cloudTweetDataStore!!.getTweetList()
    verify(mockApi).getTweetList("USER_ID")
  }
}

