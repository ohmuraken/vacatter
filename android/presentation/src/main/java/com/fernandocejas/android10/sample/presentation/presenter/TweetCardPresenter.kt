package com.fernandocejas.android10.sample.presentation.presenter

import com.fernandocejas.android10.sample.domain.Tweet
import com.fernandocejas.android10.sample.domain.exception.DefaultErrorBundle
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver
import com.fernandocejas.android10.sample.domain.interactor.GetTweetList
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity
import com.fernandocejas.android10.sample.presentation.mapper.TweetModelDataMapper
import com.fernandocejas.android10.sample.presentation.model.TweetModel
import com.fernandocejas.android10.sample.presentation.view.TweetCardView
import javax.inject.Inject

/**
 *
 *
 * Created on 8/14/17.
 */
@PerActivity
class TweetCardPresenter @Inject constructor(val getTweetListUseCase: GetTweetList,
    val tweetModelDataMapper: TweetModelDataMapper) : Presenter {

  private var viewListView: TweetCardView? = null

  fun setView(view: TweetCardView) {
    this.viewListView = view
  }

  override fun resume() {}

  override fun pause() {}

  override fun destroy() {
    this.getTweetListUseCase.dispose()
    this.viewListView = null
  }

  /**
   * Initializes the presenter by start retrieving the tweet list.
   */
  fun initialize() {
    this.loadTweetList()
  }

  /**
   * Loads all tweets.
   */
  private fun loadTweetList() {
    this.hideViewRetry()
    this.showViewLoading()
    this.getTweetList()
  }

  fun onTweetClicked(tweetModel: TweetModel) {
    this.viewListView!!.viewTweet(tweetModel)
  }

  private fun showViewLoading() {
    this.viewListView!!.showLoading()
  }

  private fun hideViewLoading() {
    this.viewListView!!.hideLoading()
  }

  private fun showViewRetry() {
    this.viewListView!!.showRetry()
  }

  private fun hideViewRetry() {
    this.viewListView!!.hideRetry()
  }

  private fun showErrorMessage(errorBundle: ErrorBundle) {
    val errorMessage = ErrorMessageFactory.create(this.viewListView!!.context(),
        errorBundle.exception)
    this.viewListView!!.showError(errorMessage)
  }

  private fun showTweetsCollectionInView(tweetCollection: Collection<Tweet>) {
    val tweetModelCollection = this.tweetModelDataMapper.transform(tweetCollection)
    this.viewListView!!.renderTweetList(tweetModelCollection)
  }

  private fun getTweetList() {
    this.getTweetListUseCase.execute(TweetListObserver(), null)
  }

  private inner class TweetListObserver : DefaultObserver<List<Tweet>>() {

    override fun onComplete() {
      hideViewLoading()
    }

    override fun onError(e: Throwable) {
      hideViewLoading()
      showErrorMessage(DefaultErrorBundle(e as Exception))
      showViewRetry()
    }

    override fun onNext(tweets: List<Tweet>) {
      showTweetsCollectionInView(tweets)
    }
  }
}
