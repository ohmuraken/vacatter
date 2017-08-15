package com.fernandocejas.android10.sample.data.repository.datasource

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.preference.PreferenceManager
import android.renderscript.ScriptGroup.Input
import com.fernandocejas.android10.sample.data.net.RetroApi
import io.reactivex.Completable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.InputStream
import java.net.URI


/**
 *
 *
 * Created on 8/13/17.
 */
class FaceCloudDataStore constructor(val api: RetroApi, val helper: DataStoreHelper) : FaceDataStore {

  override fun postFace(photo: URI): Completable {
    val uri: Uri = helper.convertURItoUri(photo)
    val stream: InputStream = helper.convertUriToInputStream(uri)
    val requestFile = helper.createRequestBody(stream)

    val imageBody = helper.createMultipardBody("image", "image", requestFile)
    val tokenBody = MultipartBody.Part.createFormData("user_id", helper.getUserId())

    return this.api.postFace(tokenBody, imageBody)
  }
}