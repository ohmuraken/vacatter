package com.fernandocejas.android10.sample.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import butterknife.Bind
import butterknife.ButterKnife
import butterknife.OnClick
import com.fernandocejas.android10.sample.presentation.R
import com.fernandocejas.android10.sample.presentation.internal.di.components.UseCaseComponent
import com.fernandocejas.android10.sample.presentation.model.TweetModel
import com.fernandocejas.android10.sample.presentation.presenter.TweetCardPresenter
import com.fernandocejas.android10.sample.presentation.view.TweetCardView
import com.fernandocejas.android10.sample.presentation.view.adapter.TweetsAdapter
import com.fernandocejas.android10.sample.presentation.view.adapter.TweetsLayoutManager
import javax.inject.Inject

/**
 *
 *
 * Created on 8/14/17.
 */
class TweetCardFragment : BaseFragment(), TweetCardView {

  /**
   * Interface for Listening tweet events.
   */
  interface TweetCardListener {
    fun onTweetClicked(tweetModel: TweetModel)
  }

  @Inject lateinit var tweetCardPresenter: TweetCardPresenter;
  @Inject lateinit var tweetsAdapter: TweetsAdapter;

  @Bind(R.id.rv_tweets) lateinit var rv_tweets: RecyclerView
  @Bind(R.id.rl_progress) lateinit var rl_progress: RelativeLayout
  @Bind(R.id.rl_retry) lateinit var rl_retry: RelativeLayout
  @Bind(R.id.bt_retry) lateinit var bt_retry: Button

  private lateinit var tweetCardListener: TweetCardListener

  init {
    setRetainInstance(true)
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is TweetCardListener) {
      tweetCardListener = context as TweetCardListener
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    this.getComponent(UseCaseComponent::class.java).inject(this)
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View {
    val fragmentView = inflater!!.inflate(R.layout.fragment_tweet_card, container, false)
    ButterKnife.bind(this, fragmentView)
    setupRecyclerView()
    return fragmentView
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    this.tweetCardPresenter.setView(this)
    if (savedInstanceState == null) {
      this.loadTweetCard();
    }
  }

  override fun onResume() {
    super.onResume()
    this.tweetCardPresenter.resume();
  }

  override fun onPause() {
    super.onPause()
    this.tweetCardPresenter.pause();
  }

  override fun onDestroyView() {
    super.onDestroyView()
    rv_tweets.setAdapter(null)
    ButterKnife.unbind(this)
  }

  override fun onDestroy() {
    super.onDestroy()
    this.tweetCardPresenter.destroy();
  }

  override fun onDetach() {
    super.onDetach()
//    this.tweetCardListener = null
  }

  override fun showLoading() {
    this.rl_progress.setVisibility(View.VISIBLE)
  }

  override fun hideLoading() {
    this.rl_progress.setVisibility(View.GONE)
  }

  override fun showRetry() {
    this.rl_retry.setVisibility(View.VISIBLE)
  }

  override fun hideRetry() {
    this.rl_retry.setVisibility(View.GONE)
  }

  override fun renderTweetList(tweetModelCollection: Collection<TweetModel>) {
    this.tweetsAdapter.setTweetsCollection(tweetModelCollection)
  }

  override fun viewTweet(tweetModel: TweetModel) {
    if (this.tweetCardListener != null) {
      this.tweetCardListener.onTweetClicked(tweetModel)
    }
  }

  override fun showError(message: String) {
    this.showToastMessage(message)
  }

  override fun context(): Context {
    return this.getActivity().getApplicationContext()
  }

  private fun setupRecyclerView() {
//    this.tweetsAdapter.setOnItemClickListener(onItemClickListener)
    this.rv_tweets.setLayoutManager(TweetsLayoutManager(context()))
    this.rv_tweets.setAdapter(tweetsAdapter)
  }

  /**
   * Loads all tweets.
   */
  private fun loadTweetCard() {
    this.tweetCardPresenter.initialize()
  }

  @OnClick(R.id.bt_retry)
  fun onButtonRetryClick() {
    this.loadTweetCard();
  }

//  private val onItemClickListener = TweetsAdapter.OnItemClickListener { tweetModel ->
//    override fun onTweetItemClicked(tweetModel: TweetModel) {
//      if (this.tweetCardListener != null && tweetModel != null) {
//        this.tweetCardPresenter.onTweetClicked(tweetModel)
//      }
//    }
//  }
}