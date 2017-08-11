package com.fernandocejas.android10.sample.data.entity.mapper

import com.fernandocejas.android10.sample.data.entity.TweetEntity
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

/**
 * Class used to transform from Strings representing json to valid objects.
 *
 * Created on 8/9/17.
 */
class TweetEntityJsonMapper constructor() {

  @Inject
  var gson: Gson = Gson()

  /**
   * Transform from valid json string to List of [TweetEntity].

   * @param tweetListJsonResponse A json representing a collection of users.
   * *
   * @return List of [TweetEntity].
   * *
   * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
   */
  @Throws(JsonSyntaxException::class)
  fun transformTweetEntityCollection(tweetListJsonResponse: String?): List<TweetEntity> {
    val listOfTweetEntityType = object : TypeToken<List<TweetEntity>>() {}.type
    return this.gson.fromJson(tweetListJsonResponse, listOfTweetEntityType)
  }
}