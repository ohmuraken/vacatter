package com.fernandocejas.android10.sample.data.repository.datasource

import android.net.Uri
import com.fernandocejas.android10.sample.data.net.RetroApi
import io.reactivex.Completable
import okhttp3.MultipartBody
import java.io.InputStream
import java.net.URI


/**
 *
 *
 * Created on 8/13/17.
 */
class FaceCloudDataStore constructor(val api: RetroApi,
    val helper: DataStoreHelper) : FaceDataStore {

  override fun postFace(photo: URI): Completable {
    val uri: Uri = helper.convertURItoUri(photo)
    val stream: InputStream = helper.convertUriToInputStream(uri)
    val requestFile = helper.createRequestBody(stream)

    val imageBody = helper.createMultipardBody("image", helper.getFileName(uri), requestFile)
    val tokenBody = MultipartBody.Part.createFormData("user_id", helper.getUserId())

    return this.api.postFace(tokenBody, imageBody)
  }
}