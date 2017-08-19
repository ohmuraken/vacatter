package com.fernandocejas.android10.sample.presentation.presenter

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.fernandocejas.android10.sample.domain.interactor.PostToken
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity
import com.fernandocejas.android10.sample.presentation.view.intro.TwitterLoginIntro
import io.reactivex.observers.DisposableCompletableObserver
import javax.inject.Inject


/**
 *
 *
 * Created on 8/14/17.
 */
@PerActivity
class TwitterLoginIntroPresenter @Inject constructor(val postTokenUseCase: PostToken) : Presenter {
  private val TAG = TwitterLoginIntroPresenter::class.java.getName()
  private lateinit var view: TwitterLoginIntro

  fun setView(view: TwitterLoginIntro) {
    this.view = view
  }

  override fun resume() {}

  override fun pause() {}

  override fun destroy() {}

  /**
   * Method is executed in TwitterLoginIntro
   */
  fun postToken() {
    postTokenUseCase.execute(PostTokenOfIntroCompletable(), null)
  }

  private inner class PostTokenOfIntroCompletable : DisposableCompletableObserver() {
    override fun onComplete() {
      Log.d(TAG, "Complete Sending Token")
    }

    override fun onError(e: Throwable) {
      Log.d(TAG, "Error")
    }
  }
}