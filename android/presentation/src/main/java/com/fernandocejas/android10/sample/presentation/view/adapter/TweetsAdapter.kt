package com.fernandocejas.android10.sample.presentation.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.Bind
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.fernandocejas.android10.sample.presentation.R
import com.fernandocejas.android10.sample.presentation.model.TweetModel
import javax.inject.Inject

/**
 *
 *
 * Created on 8/14/17.
 */
class TweetsAdapter @Inject constructor(
    val context: Context) : RecyclerView.Adapter<TweetsAdapter.TweetViewHolder>() {

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

    // CardViewBind Text
    holder.title.text = tweetModel.tweetId.toString()
    Glide.with(context).load(tweetModel.faceChangeUrls?.get(0))
        .fitCenter()
        .into(holder.thumbnail)

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
    @Bind(R.id.thumbnail) lateinit var thumbnail: ImageView
    @Bind(R.id.title) lateinit var title: TextView

    init {
      ButterKnife.bind(this, itemView)
    }
  }
}
