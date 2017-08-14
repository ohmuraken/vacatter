package com.fernandocejas.android10.sample.presentation.view

import com.fernandocejas.android10.sample.presentation.model.TweetModel

/**
 *
 *
 * Created on 8/14/17.
 */
interface TweetCardView : LoadDataView {
  /**
   * Render a user list in the UI.

   * @param tweetModelCollection The collection of [TweetModel] that will be shown.
   */
  fun renderTweetList(tweetModelCollection: Collection<TweetModel>)

  /**
   * View a [TweetModel] profile/details.

   * @param tweetModel The user that will be shown.
   */
  fun viewTweet(tweetModel: TweetModel)
}