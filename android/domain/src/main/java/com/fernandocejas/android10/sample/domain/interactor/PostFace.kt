package com.fernandocejas.android10.sample.domain.interactor

import com.fernandocejas.android10.sample.domain.Face
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor
import com.fernandocejas.android10.sample.domain.repository.FaceRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * face date sever.
 *
 * Created on 8/13/17.
 */
class PostFace @Inject constructor(
    val faceRepository: FaceRepository,
    val threadExecutor: ThreadExecutor,
    val postExecutionThread: PostExecutionThread
) : UseCase<Face, Void>(threadExecutor, postExecutionThread) {

  override public fun buildUseCaseObservable(params: Void?): Observable<Face> {
    return this.faceRepository.face();
  }
}
