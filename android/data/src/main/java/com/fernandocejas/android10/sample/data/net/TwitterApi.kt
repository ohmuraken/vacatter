package com.fernandocejas.android10.sample.data.net

import io.reactivex.Completable
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * Interface can access Server.
 *
 * Created on 2017/08/13.
 */
interface TwitterApi {

  @Multipart
  @POST("face")
  fun postFace (
      @Part user_id: MultipartBody.Part,
      @Part image: MultipartBody.Part
  ): Completable
}
