package com.fernandocejas.android10.sample.data.net

import android.content.Context
import android.net.Uri
import com.fernandocejas.android10.sample.data.entity.TweetEntity
import com.fernandocejas.android10.sample.data.entity.mapper.TweetEntityJsonMapper
import com.fernandocejas.android10.sample.data.exception.NetworkConnectionException
import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 *
 * Created on 8/13/17.
 */
@Singleton
class RetroApi @Inject constructor() {

  val API_BASE_URL: String = "http://mk-azureserver.japaneast.cloudapp.azure.com:5001/vacatter/demo/api/v1/"
  val service: RestApiService

  init {
    val builder = OkHttpClient.Builder().addInterceptor(getLogginInterceptor())
    val retro = Retrofit.Builder().baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(builder.build()).build()
    service = retro.create(RestApiService::class.java)
  }

  fun postFace(user_id: MultipartBody.Part, image: MultipartBody.Part): Completable {
    return service.postFace(user_id, image)
  }

  fun tweetEntityList(user_id: String): Observable<List<TweetEntity>> {
    return service.tweetEntityList(user_id)
  }

  fun likeTweet(user_id: String, like: Int): Completable{
    return service.likeTweet(user_id, like)
  }

  fun getLogginInterceptor(): Interceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    return interceptor
  }
}