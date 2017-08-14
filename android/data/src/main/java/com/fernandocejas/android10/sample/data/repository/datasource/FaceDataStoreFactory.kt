package com.fernandocejas.android10.sample.data.repository.datasource

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.fernandocejas.android10.sample.data.net.RetroApi
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 *
 * Created on 8/13/17.
 */
@Singleton
class FaceDataStoreFactory @Inject constructor(val context: Context){

  val helper: DataStoreHelper = DataStoreHelper(context)
  val api: RetroApi = RetroApi()

  fun createCloudDataStore(): FaceCloudDataStore {
    return FaceCloudDataStore(api, helper)
  }
}