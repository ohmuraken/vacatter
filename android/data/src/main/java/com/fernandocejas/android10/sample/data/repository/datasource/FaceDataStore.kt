package com.fernandocejas.android10.sample.data.repository.datasource

import io.reactivex.Completable
import java.net.URI

/**
 *
 *
 * Created on 8/13/17.
 */
interface FaceDataStore {
  fun postFace(photo: URI): Completable
}