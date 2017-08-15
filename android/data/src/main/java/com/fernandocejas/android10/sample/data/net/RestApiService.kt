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
  @POST("face") fun postFace(
      @Part user_id: MultipartBody.Part,
      @Part image: MultipartBody.Part
  ): Completable

  @GET("timeline")
  fun tweetEntityList(
      @Query("user_id") user_id: String
  ): Observable<List<TweetEntity>>
}