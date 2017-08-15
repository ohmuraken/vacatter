package com.fernandocejas.android10.sample.presentation.view.intro

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import butterknife.Bind
import butterknife.ButterKnife
import butterknife.OnClick
import com.fernandocejas.android10.sample.presentation.R
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent
import com.fernandocejas.android10.sample.presentation.presenter.PostFacePresenter
import com.fernandocejas.android10.sample.presentation.view.fragment.BaseFragment
import javax.inject.Inject


/**
 *
 *
 * Created on 2017/08/15.
 */
class CameraIntro : BaseFragment() {

  private val READ_REQUEST_CODE: Int = 1001
  @Bind(R.id.btn_TakePhoto) lateinit var btn_TakePhoto: Button
  @Bind(R.id.btn_PickGallery) lateinit var btn_PickGallery: Button
  @Inject lateinit var postFacePresenter: PostFacePresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View {
    val fragmentView = inflater!!.inflate(R.layout.intro_camera, container, false)
    ButterKnife.bind(this, fragmentView)
    return fragmentView
  }

  fun context(): Context {
    return this.getActivity().getApplicationContext()
  }

  @OnClick(R.id.btn_PickGallery)
  fun onButtonStorageAccess() {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    intent.type = "image/*"
    startActivityForResult(intent, READ_REQUEST_CODE)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
      postFacePresenter.postFace(data)
    }
  }

}