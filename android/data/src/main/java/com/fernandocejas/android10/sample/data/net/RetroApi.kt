package com.fernandocejas.android10.sample.data.net

import android.content.Context
import android.net.Uri
import io.reactivex.Completable
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 *
 * Created on 8/13/17.
 */
@Singleton
class RetroApi @Inject constructor() {

  val API_BASE_URL: String = "http://mk-azureserver.japaneast.cloudapp.azure.com:5000/api/v1/"
  val service: RestApiService

  init {
    val builder = OkHttpClient.Builder().addInterceptor(getLogginInterceptor())
    val retro = Retrofit.Builder().baseUrl(API_BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(builder.build()).build()
    service = retro.create(RestApiService::class.java)
  }

  fun postFace(token: String, file: MultipartBody.Part): Completable {
    return service.postFace(token, file)
  }

  fun getLogginInterceptor(): Interceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    return interceptor
  }
}