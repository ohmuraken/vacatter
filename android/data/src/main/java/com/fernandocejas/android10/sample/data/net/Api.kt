package com.fernandocejas.android10.sample.data.net

import android.media.FaceDetector.Face
import android.net.Uri
import com.fernandocejas.android10.sample.data.entity.TweetEntity
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Connection Internet
 *
 * Created on 8/10/17.
 */

interface Api {
  val API_BASE_URL: String
    get() = "http://mk-azureserver.japaneast.cloudapp.azure.com:5000/vacatter/demo/api/v1/"

  val API_URL_GET_TWEET_LIST: String
    get() = API_BASE_URL + "tweets"

  fun tweetEntityList(): Observable<List<TweetEntity>>
}