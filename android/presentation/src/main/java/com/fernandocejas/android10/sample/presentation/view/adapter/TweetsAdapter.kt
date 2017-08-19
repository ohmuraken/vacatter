package com.fernandocejas.android10.sample.presentation.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.Bind
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.fernandocejas.android10.sample.presentation.R
import com.fernandocejas.android10.sample.presentation.model.TweetModel
import com.fernandocejas.android10.sample.presentation.presenter.CameraIntroPresenter
import org.w3c.dom.Text
import javax.inject.Inject

/**
 *
 *
 * Created on 8/14/17.
 */
class TweetsAdapter @Inject constructor(
    val context: Context) : RecyclerView.Adapter<TweetsAdapter.TweetViewHolder>() {
  private val TAG = TweetsAdapter::class.java.getName()

  interface OnItemClickListener {
    fun onTweetItemClicked(tweetModel: TweetModel)
  }

  private var tweetsCollection: List<TweetModel>? = null
  private val layoutInflater: LayoutInflater

  private var onItemClickListener: OnItemClickListener? = null

  init {
    this.layoutInflater = context.getSystemService(
        Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    this.tweetsCollection = emptyList<TweetModel>()
  }

  override fun getItemCount(): Int {
    return if (this.tweetsCollection != null) this.tweetsCollection!!.size else 0
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
    val view = this.layoutInflater.inflate(R.layout.row_tweet, parent, false)
    return TweetViewHolder(view)
  }

  /**
   * Tweetごとに、テキスト,画像等を割り当てる
   */
  override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
    val tweetModel = this.tweetsCollection!![position]

    val screenName: String = "@" + tweetModel.screenName
    holder.userId.setText(screenName)
    holder.userName.setText(tweetModel.name)
    holder.tweetText.setText(tweetModel.text)
    Glide.with(context).load(tweetModel.faceChangeUrls?.get(0))
        .diskCacheStrategy( DiskCacheStrategy.NONE )
        .skipMemoryCache(true)
        .fitCenter()
        .into(holder.tweetImage)

    holder.itemView.setOnClickListener {
      if (this.onItemClickListener != null) {
        this.onItemClickListener!!.onTweetItemClicked(tweetModel)
      }
    }
  }

  override fun getItemId(position: Int): Long {
    return position.toLong()
  }

  fun setTweetsCollection(tweetsCollection: Collection<TweetModel>) {
    this.validateTweetsCollection(tweetsCollection)
    this.tweetsCollection = tweetsCollection as List<TweetModel>
    this.notifyDataSetChanged()
  }

  fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
    this.onItemClickListener = onItemClickListener
  }

  private fun validateTweetsCollection(tweetsCollection: Collection<TweetModel>?) {
    if (tweetsCollection == null) {
      throw IllegalArgumentException("The list cannot be null")
    }
  }

  public class TweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    @Bind(R.id.UserName) lateinit var userName: TextView
    @Bind(R.id.UserId) lateinit var userId: TextView
    @Bind(R.id.TweetImage) lateinit var tweetImage: ImageView
    @Bind(R.id.TweetText) lateinit var tweetText: TextView

    init {
      ButterKnife.bind(this, itemView)
    }
  }
}
