package com.fernandocejas.android10.sample.data.repository

import com.fernandocejas.android10.sample.data.repository.datasource.FaceDataStoreFactory
import com.fernandocejas.android10.sample.domain.repository.FaceRepository
import io.reactivex.Completable
import java.net.URI
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 *
 * Created on 8/13/17.
 */
@Singleton
class FaceDataRepository @Inject constructor(val dataFactory: FaceDataStoreFactory) : FaceRepository{

  override fun postFace(photo: URI): Completable {
    val faceCloudDataStore = dataFactory.createCloudDataStore()
    return faceCloudDataStore.postFace(photo)
  }
}