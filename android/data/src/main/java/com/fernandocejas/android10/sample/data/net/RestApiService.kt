package com.fernandocejas.android10.sample.data.net

import io.reactivex.Completable
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 *
 *
 * Created on 8/13/17.
 */
interface RestApiService {
  @Multipart
  @POST("face") fun postFace(
      @Part token: MultipartBody.Part,
      @Part file: MultipartBody.Part
  ): Completable
}