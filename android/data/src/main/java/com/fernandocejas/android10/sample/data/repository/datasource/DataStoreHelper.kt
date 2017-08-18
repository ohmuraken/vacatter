package com.fernandocejas.android10.sample.data.repository.datasource

import android.content.Context
import android.net.Uri
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
  val shared = context.getSharedPreferences("twitter", Context.MODE_PRIVATE)

  fun getToken(): String {
    return shared.getString("token", "")
  }

  fun getTokenSecret(): String {
    return shared.getString("secret", "")
  }

  fun getUserId(): String {
    return shared.getString("user_id", "")
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