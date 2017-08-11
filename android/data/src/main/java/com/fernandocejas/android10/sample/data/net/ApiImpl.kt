package com.fernandocejas.android10.sample.data.net

import android.content.Context
import android.net.ConnectivityManager
import com.fernandocejas.android10.sample.data.entity.TweetEntity
import com.fernandocejas.android10.sample.data.entity.mapper.TweetEntityJsonMapper
import com.fernandocejas.android10.sample.data.exception.NetworkConnectionException
import io.reactivex.Observable
import java.net.MalformedURLException
import javax.inject.Inject

/**
 * {@link Api} implementation for retrieving data from the network
 *
 * Created on 8/10/17.
 */
class ApiImpl @Inject constructor(var context: Context, var tweetEntityJsonMapper: TweetEntityJsonMapper) : Api {

  override fun tweetEntityList(): Observable<List<TweetEntity>> {
    return Observable.create { emitter ->
      if (isThereInternetConnection()) {
        try {
          val resTwitterEntities = getTweetEntitiesFromApi()
          println(resTwitterEntities)
          if (resTwitterEntities != null) {
            println(tweetEntityJsonMapper.transformTweetEntityCollection(resTwitterEntities))
            emitter.onNext(tweetEntityJsonMapper.transformTweetEntityCollection(resTwitterEntities))
            emitter.onComplete()
          } else {
            emitter.onError(NetworkConnectionException())
          }
        } catch (e: Exception) {
          emitter.onError(NetworkConnectionException(e.cause))
        }
      } else {
        emitter.onError(NetworkConnectionException())
      }
    }
  }

  @Throws(MalformedURLException::class)
  fun getTweetEntitiesFromApi(): String? {
    return ApiConnection.createGET(API_URL_GET_TWEET_LIST).requestSyncCall()
  }

  /**
   * Checks if the device has any active internet connection.

   * @return true device with internet connection, otherwise false.
   */
  private fun isThereInternetConnection(): Boolean {
    val isConnected: Boolean

    val connectivityManager = this.context.getSystemService(
        Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting

    return isConnected
  }
}