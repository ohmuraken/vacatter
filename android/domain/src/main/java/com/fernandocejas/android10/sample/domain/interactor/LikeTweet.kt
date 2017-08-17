package com.fernandocejas.android10.sample.domain.interactor

import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor
import com.fernandocejas.android10.sample.domain.repository.TweetRepository
import io.reactivex.Completable
import javax.inject.Inject

class LikeTweet @Inject constructor(
    val tweetRepository: TweetRepository,
    override val threadExecutor: ThreadExecutor,
    override val postExecutionThread: PostExecutionThread
) : CompletableUseCase<LikeTweet.Params>(threadExecutor, postExecutionThread) {

  override fun buildUseCaseCompletable(params: Params): Completable {
    return this.tweetRepository.like(params.tweetId);
  }

  class Params private constructor(val tweetId: String){
    companion object {
      fun forTweet(tweetId: String): Params {
        return Params(tweetId)
      }
    }
  }
}