package com.fernandocejas.android10.sample.data.repository.datasource

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.fernandocejas.android10.sample.data.net.RetroApi
import io.reactivex.Completable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.net.URI


/**
 *
 *
 * Created on 8/13/17.
 */
class FaceCloudDataStore constructor(val api: RetroApi, val helper: DataStoreHelper) : FaceDataStore {

  override fun postFace(photo: URI): Completable {
    val token: String = helper.getToken()
    val file: File = helper.convertFile(photo)
    val requestFile = helper.createRequestFile(file)
    val body = helper.createMultipartBody(file, requestFile)

    // convert uri to Image File
    return this.api.postFace(token, body)
  }
}