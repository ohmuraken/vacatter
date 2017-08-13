package com.fernandocejas.android10.sample.domain.repository

import com.fernandocejas.android10.sample.domain.Face
import io.reactivex.Observable

/**
 * Interface that represents a Repository for getting [@link Face] related data.
 *
 * Created on 8/13/17.
 */
interface FaceRepository {
  /**
   * Get an [Observable] which will emit a [@link Profile] Image uri.
   */
  fun face(): Observable<Face>
}