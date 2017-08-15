package com.fernandocejas.android10.sample.presentation.presenter

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.fernandocejas.android10.sample.domain.interactor.PostFace
import com.fernandocejas.android10.sample.domain.interactor.PostFace.Params
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity
import com.fernandocejas.android10.sample.presentation.view.LoadDataView
import io.reactivex.observers.DisposableCompletableObserver
import java.net.URI
import javax.inject.Inject


/**
 *
 *
 * Created on 8/14/17.
 */
@PerActivity
class PostFacePresenter @Inject constructor(val postFaceUseCase: PostFace) : Presenter {
  private val TAG = PostFacePresenter::class.java.getName()
  private lateinit var viewListView: LoadDataView

  fun setView(view: LoadDataView) {
    this.viewListView = view
  }

  override fun resume() {
  }

  override fun pause() {
  }

  override fun destroy() {
  }

  fun postFace(resultData: Intent?) {
    if (resultData != null) {
      val uri: Uri = resultData.data
      postFaceUseCase.execute(PostFaceCompletable(), Params.forFace(URI.create(uri.toString())))
    }
  }

  private inner class PostFaceCompletable : DisposableCompletableObserver() {
    override fun onComplete() {
      Log.d(TAG, "Complete")
    }

    override fun onError(e: Throwable) {
      Log.d(TAG, "Error")
    }
  }
}