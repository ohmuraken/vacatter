package com.fernandocejas.android10.sample.data.repository.datasource

import android.content.Context
import com.fernandocejas.android10.sample.data.entity.mapper.TweetEntityJsonMapper
import com.fernandocejas.android10.sample.data.net.Api
import com.fernandocejas.android10.sample.data.net.ApiImpl
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Factory that crated {@link TweetDataStore}
 *
 * Created on 8/11/17.
 */
@Singleton
class TweetDataStoreFactory @Inject constructor(context: Context) {

  @Inject lateinit var context: Context

  fun createcloudDataStore(): CloudTweetDataStore {
    val api: Api = ApiImpl(context, TweetEntityJsonMapper())
    return CloudTweetDataStore(api)
  }
}