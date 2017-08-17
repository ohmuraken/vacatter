package com.fernandocejas.android10.sample.domain.interactor

import com.fernandocejas.android10.sample.domain.Tweet
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor
import com.fernandocejas.android10.sample.domain.repository.TweetRepository
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.Observable
import javax.inject.Inject

class PostToken @Inject constructor(
    var tweetRepository: TweetRepository,
    override var threadExecutor: ThreadExecutor,
    override var postExecutionThread: PostExecutionThread
) : CompletableUseCase<Void?>(threadExecutor, postExecutionThread) {

  override fun buildUseCaseCompletable(params: Void?): Completable {
    return this.tweetRepository.token()
  }
}