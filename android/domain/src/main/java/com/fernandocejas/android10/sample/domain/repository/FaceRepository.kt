package com.fernandocejas.android10.sample.domain.repository

import com.fernandocejas.android10.sample.domain.Face
import io.reactivex.Completable
import io.reactivex.Observable
import java.net.URI

/**
 * Interface that represents a Repository for getting [@link Face] related data.
 *
 * Created on 8/13/17.
 */
interface FaceRepository {
  /**
   * Post an [Completable] which will emit a [@link Face] Image uri.
   */
  fun postFace(photo: URI): Completable
}