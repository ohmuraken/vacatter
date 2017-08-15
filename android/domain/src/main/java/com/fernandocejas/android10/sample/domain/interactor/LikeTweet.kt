package com.fernandocejas.android10.sample.domain.interactor

import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor
import com.fernandocejas.android10.sample.domain.repository.TweetRepository
import io.reactivex.Completable
import javax.inject.Inject

/**
 * Created by anna on 2017/08/16.
 */
class LikeTweet @Inject constructor(
    val tweetRepository: TweetRepository,
    override val threadExecutor: ThreadExecutor,
    override val postExecutionThread: PostExecutionThread
) : CompletableUseCase<LikeTweet.Params>(threadExecutor, postExecutionThread) {

  override fun buildUseCaseCompletable(params: Params): Completable {
    return this.tweetRepository.likes(params.like);
  }

  class Params private constructor(val like: Int){
    companion object {
      fun forTweet(like: Int): Params {
        return Params(like)
      }
    }
  }
}