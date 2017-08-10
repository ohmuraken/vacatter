package com.fernandocejas.android10.sample.data.entity.mapper

import com.google.gson.JsonSyntaxException
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Testing Tweet Entity Class
 *
 * Created on 8/10/17.
 */
@RunWith(MockitoJUnitRunner::class)
class TweetEntityJsonMapperTest {

  private var tweetEntityJsonMapper: TweetEntityJsonMapper? = null

  private val JSON_RESPONSE_TWEET_COLLECTION = "[\n" +
      "    {\n" +
      "        'id': '894607123866296320',\n" +
      "        'media_urls': [\n" +
      "            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg',\n" +
      "            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg',\n" +
      "            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg',\n" +
      "            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg'\n" +
      "        ],\n" +
      "        'favorite_count': 40,\n" +
      "        'retweet_count': 34,\n" +
      "        'user_id': '471707277',\n" +
      "        'name': '真奈',\n" +
      "        'retweeted': False,\n" +
      "        'favorited': True,\n" +
      "        'text': 'だあああああ',\n" +
      "        'face_change_urls': [\n" +
      "            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg',\n" +
      "            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg',\n" +
      "            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg',\n" +
      "            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg'\n" +
      "        ]\n" +
      "    },\n" +
      "    {\n" +
      "        'id': '894607123866296322',\n" +
      "        'media_urls': [\n" +
      "            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg',\n" +
      "            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg',\n" +
      "            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg',\n" +
      "            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg'\n" +
      "        ],\n" +
      "        'favorite_count': 43,\n" +
      "        'retweet_count': 34,\n" +
      "        'user_id': '471707277',\n" +
      "        'name': '真奈',\n" +
      "        'retweeted': True,\n" +
      "        'favorited': False,\n" +
      "        'text': 'なああああああああ',\n" +
      "        'face_change_urls': [\n" +
      "            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg',\n" +
      "            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg',\n" +
      "            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg',\n" +
      "            'https://pbs.twimg.com/media/DGpIcHeVoAApbaf.jpg'\n" +
      "        ]\n" +
      "    }\n" +
      "]"

  @Rule
  @JvmField
  var expectedException = ExpectedException.none()

  @Before
  fun setUp() {
    tweetEntityJsonMapper = TweetEntityJsonMapper()
  }

  @Test
  fun testTransformTweetEntityCollection() {
    val tweetEntityCollection = tweetEntityJsonMapper!!.transformTweetEntityCollection(
        JSON_RESPONSE_TWEET_COLLECTION)

    assertThat(tweetEntityCollection[0].tweetId).isEqualTo("894607123866296320")
    assertThat(tweetEntityCollection[0].mediaUrls.size).isEqualTo(4)
    assertThat(tweetEntityCollection[1].tweetId).isEqualTo("894607123866296322")
    assertThat(tweetEntityCollection[1].mediaUrls.size).isEqualTo(4)
  }

  @Test
  fun testTransformUserEntityCollectionNotValidResponse() {
    expectedException.expect(JsonSyntaxException::class.java)
    tweetEntityJsonMapper!!.transformTweetEntityCollection("Tony Stark")
  }
}
