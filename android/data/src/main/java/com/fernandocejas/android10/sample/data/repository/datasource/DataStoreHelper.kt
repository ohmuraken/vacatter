package com.fernandocejas.android10.sample.data.repository.datasource

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
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
class DataStoreHelper constructor(val context: Context) {
  fun getToken(): String {
    val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    return preferences.getString("twitter", "token")
  }

  fun convertFile(uri: URI): File {
    return File(uri)
  }

  fun createRequestFile(file: File): RequestBody {
    return RequestBody.create(MediaType.parse("multipart/form-data"), file)
  }

  fun createMultipartBody(file: File, requestBody: RequestBody) :  MultipartBody.Part{
    return MultipartBody.Part.createFormData("picture", file.name, requestBody)
  }
}