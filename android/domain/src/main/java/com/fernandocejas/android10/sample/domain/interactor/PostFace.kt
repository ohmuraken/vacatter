package com.fernandocejas.android10.sample.domain.interactor

import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor
import com.fernandocejas.android10.sample.domain.repository.FaceRepository
import io.reactivex.Completable
import java.net.URI
import javax.inject.Inject

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * face date sever.
 *
 * Created on 8/13/17.
 */
class PostFace @Inject constructor(
    val faceRepository: FaceRepository,
    override val threadExecutor: ThreadExecutor,
    override val postExecutionThread: PostExecutionThread
) : CompletableUseCase<PostFace.Params>(threadExecutor, postExecutionThread) {

  override fun buildUseCaseCompletable(params: Params): Completable {
    return this.faceRepository.postFace(params.photo);
  }

  class Params private constructor(val photo: URI) {
    companion object {
      fun forFace(photo: URI): Params {
        return Params(photo)
      }
    }
  }
}
