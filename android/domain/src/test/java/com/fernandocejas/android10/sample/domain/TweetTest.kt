package com.fernandocejas.android10.sample.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

/**
 * Testing Tweet Class
 *
 * Created by kazutaka on 2017/08/11.
 */
class TweetTest {
  val FAKE_TWEET_ID: String = "123456"
  lateinit var tweet: Tweet

  @Before
  fun setUp() {
    this.tweet = Tweet(this.FAKE_TWEET_ID)
  }

  @Test
  fun testTweetConstructorHappyCase() {
    val tweetId: String = tweet.tweetId
    assertThat(FAKE_TWEET_ID).isEqualTo(tweetId)
  }
}