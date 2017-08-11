package com.fernandocejas.android10.sample.domain.interactor

import com.fernandocejas.android10.sample.domain.Tweet
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor
import com.fernandocejas.android10.sample.domain.repository.TweetRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Usecase that get Tweet List
 *
 * Created on 2017/08/11.
 */
class GetTweetList @Inject constructor(
    var tweetRepository: TweetRepository,
    var threadExecutor: ThreadExecutor,
    var postExecutionThread: PostExecutionThread
) : UseCase<List<Tweet>, Void>(threadExecutor, postExecutionThread) {

  override public fun buildUseCaseObservable(params: Void?): Observable<List<Tweet>> {
    return this.tweetRepository.tweets()
  }

}
