package com.fernandocejas.android10.sample.data.net

import com.fernandocejas.android10.sample.data.entity.TweetEntity
import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 *
 * Created on 8/13/17.
 */
@Singleton
class RetroApi @Inject constructor() {

  val API_BASE_URL: String = "http://mk-azureserver.japaneast.cloudapp.azure.com:5000/vacatter/api/v1.0/"
//  val API_BASE_URL: String = "http://mk-azureserver.japaneast.cloudapp.azure.com:5001/vacatter/demo/api/v1/"
  val service: RestApiService

  init {
    val builder = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(getLogginInterceptor())
    val retro = Retrofit.Builder().baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(builder.build()).build()
    service = retro.create(RestApiService::class.java)
  }

  fun postToken(userId: MultipartBody.Part, accessToken: MultipartBody.Part, accessTokenSecret: MultipartBody.Part): Completable {
    return service.postToken(userId, accessToken, accessTokenSecret)
  }

  fun postFace(userId: MultipartBody.Part, image: MultipartBody.Part): Completable {
    return service.postFace(userId, image)
  }

  fun getTweetList(userId: String): Observable<List<TweetEntity>> {
    return service.getTweetList(userId, 50)
  }

  fun likeTweet(userId: String, tweetId: String): Completable {
    return service.likeTweet(userId, tweetId)
  }

  fun getLogginInterceptor(): Interceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    return interceptor
  }
}
