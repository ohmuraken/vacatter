package com.fernandocejas.android10.sample.presentation.presenter

import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.fernandocejas.android10.sample.domain.interactor.PostFace
import com.fernandocejas.android10.sample.domain.interactor.PostFace.Params
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity
import com.fernandocejas.android10.sample.presentation.view.LoadDataView
import com.fernandocejas.android10.sample.presentation.view.intro.CameraIntro
import io.reactivex.observers.DisposableCompletableObserver
import java.net.URI
import javax.inject.Inject


/**
 *
 *
 * Created on 8/14/17.
 */
@PerActivity
class CameraIntroPresenter @Inject constructor(val postFaceUseCase: PostFace) : Presenter {
  private val TAG = CameraIntroPresenter::class.java.getName()
  private lateinit var view: CameraIntro

  fun setView(view: CameraIntro) {
    this.view = view
  }

  override fun resume() {}

  override fun pause() {}

  override fun destroy() {}

  /**
   * Method is executed in CameraIntro
   */
  fun postFaceOfIntro(resultData: Intent?) {
    if (resultData != null) {
      val uri: Uri = resultData.data
      postFaceUseCase.execute(PostFaceOfIntroCompletable(), Params.forFace(URI.create(uri.toString())))
    }
  }

  private inner class PostFaceOfIntroCompletable : DisposableCompletableObserver() {
    override fun onComplete() {
      Log.d(TAG, "Complete")
      val prefer: SharedPreferences = view.context().getSharedPreferences("twitter", Context.MODE_PRIVATE);
      val editor: SharedPreferences.Editor = prefer.edit();
      editor.putBoolean("upload_face", true);
      editor.apply()
    }

    override fun onError(e: Throwable) {
      Log.d(TAG, "Error")
    }
  }
}