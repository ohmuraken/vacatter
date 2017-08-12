package com.fernandocejas.android10.sample.domain.interactor

import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor
import com.fernandocejas.android10.sample.domain.repository.TweetRepository
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

/**
 *
 *
 * Created on 2017/08/11.
 */
@RunWith(PowerMockRunner::class)
@PrepareForTest(
    GetTweetList::class,
    TweetRepository::class
)
class GetTweetListTest {
  var mockThreadExecutor = PowerMockito.mock(ThreadExecutor::class.java)
  var mockPostExecutionThread = PowerMockito.mock(PostExecutionThread::class.java)
  var mockTweetRepository = PowerMockito.mock(TweetRepository::class.java)
  var getTweetList = PowerMockito.spy(
      GetTweetList(
          mockTweetRepository,
          mockThreadExecutor,
          mockPostExecutionThread
      )
  )

  @Before
  fun setUp() {
  }

  @Test
  fun testGetTweetListUseCaseObservableHappyCase() {
    getTweetList.buildUseCaseObservable(null)

    verify(mockTweetRepository).tweets()
  }
}