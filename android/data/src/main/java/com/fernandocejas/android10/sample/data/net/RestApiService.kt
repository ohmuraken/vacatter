package com.fernandocejas.android10.sample.data.net

import com.fernandocejas.android10.sample.data.entity.TweetEntity
import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

/**
 *
 *
 * Created on 8/13/17.
 */
interface RestApiService {
  @Multipart
  @POST("face")fun postFace(
      @Part user_id: MultipartBody.Part,
      @Part image: MultipartBody.Part
  ): Completable

  @POST("token") fun postToken(
      @Part user_id: String,
      @Part access_token: String,
      @Part access_token_secret: String
  ): Completable

  @GET("timeline")
  fun getTweetList(
      @Query("user_id") user_id: String,
      @Query("count") count: Int
  ): Observable<List<TweetEntity>>

  @POST("like") fun likeTweet(
      @Part user_id: String,
      @Part tweet_id: String
  ): Completable
}