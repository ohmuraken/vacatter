package com.fernandocejas.android10.sample.data.repository.datasource

import android.content.Context
import com.fernandocejas.android10.sample.data.net.RetroApi
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Factory that crated {@link TweetDataStore}
 *
 * Created on 8/11/17.
 */
@Singleton
class TweetDataStoreFactory @Inject constructor(val context: Context) {

  val helper: DataStoreHelper = DataStoreHelper(context)
  val api: RetroApi = RetroApi()

  fun createcloudDataStore(): CloudTweetDataStore {
    return CloudTweetDataStore(api, helper)
  }
}