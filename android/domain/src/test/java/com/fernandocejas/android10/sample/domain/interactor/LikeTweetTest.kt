package com.fernandocejas.android10.sample.domain.interactor

import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor
import com.fernandocejas.android10.sample.domain.interactor.LikeTweet.Params
import com.fernandocejas.android10.sample.domain.repository.TweetRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class LikeTweetTest {
  lateinit var mockThreadExecutor: ThreadExecutor
  lateinit var mockPostExecutionThread: PostExecutionThread
  lateinit var mockTweetRepository: TweetRepository
  lateinit var likeTweet: LikeTweet

  @Before
  fun setUp() {
    mockThreadExecutor = Mockito.mock(ThreadExecutor::class.java)
    mockPostExecutionThread = Mockito.mock(PostExecutionThread::class.java)
    mockTweetRepository = Mockito.mock(TweetRepository::class.java)
    likeTweet = LikeTweet(mockTweetRepository, mockThreadExecutor, mockPostExecutionThread)
  }

  @Test
  fun testLikeTweetUseCaseObserverHappyCase() {
    likeTweet.buildUseCaseCompletable(Params.forTweet(20))

    Mockito.verify(mockTweetRepository).likes(20)
  }

}