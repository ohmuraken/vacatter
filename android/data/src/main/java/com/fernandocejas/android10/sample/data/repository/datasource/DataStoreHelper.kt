package com.fernandocejas.android10.sample.data.repository.datasource

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.preference.PreferenceManager
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.apache.commons.io.IOUtils
import java.io.InputStream
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

  fun getTokenSecret(): String {
    val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    return preferences.getString("twitter", "secret")
  }

  fun getUserId(): String {
    val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    return preferences.getString("twitter", "user_id")
  }

  fun convertURItoUri(javaURI: URI): Uri {
    return Uri.parse(javaURI.toString())
  }

  fun convertUriToInputStream(uri: Uri): InputStream {
    return context.getContentResolver().openInputStream(uri)
  }

  fun createRequestBody(stream: InputStream): RequestBody {
    return RequestBody.create(MediaType.parse("multipart/form-data"), IOUtils.toByteArray(stream))
  }

  fun createMultipardBody(name: String, fileName: String,
      requestBody: RequestBody): MultipartBody.Part {
    return MultipartBody.Part.createFormData(name, fileName, requestBody)
  }
}