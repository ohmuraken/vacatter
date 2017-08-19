package com.fernandocejas.android10.sample.presentation.view.intro

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import butterknife.Bind
import butterknife.ButterKnife
import butterknife.OnClick
import com.fernandocejas.android10.sample.presentation.R
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent
import com.fernandocejas.android10.sample.presentation.internal.di.components.UseCaseComponent
import com.fernandocejas.android10.sample.presentation.presenter.CameraIntroPresenter
import com.fernandocejas.android10.sample.presentation.view.activity.MainCameraActivity
import javax.inject.Inject


/**
 *
 *
 * Created on 2017/08/15.
 */
class CameraIntro : Fragment() {

  private val READ_REQUEST_CODE: Int = 1001
  @Bind(R.id.img_TakePhoto) lateinit var img_TakePhoto: ImageButton
  @Bind(R.id.img_PickGallery) lateinit var img_PickGallery: ImageButton
  @Inject lateinit var cameraIntroPresenter: CameraIntroPresenter

  init {
    setRetainInstance(true)
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    getComponent(UseCaseComponent::class.java).inject(this)
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View {
    val fragmentView = inflater!!.inflate(R.layout.intro_camera, container, false)
    ButterKnife.bind(this, fragmentView)
    return fragmentView
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    cameraIntroPresenter.setView(this)
  }

  @OnClick(R.id.img_TakePhoto)
  fun clickImageButtonTakePhoto() {
    val intentToLaunch = MainCameraActivity.getCallingIntent(this.context());
    this.context().startActivity(intentToLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
  }

  @OnClick(R.id.img_PickGallery)
  fun onImageButtonStorageAccess() {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    intent.type = "image/*"
    startActivityForResult(intent, READ_REQUEST_CODE)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
      cameraIntroPresenter.postFaceOfIntro(data)
    }
  }

  /**
   * For Dagger
   */
  fun <C> getComponent(componentType: Class<C>): C {
    return componentType.cast((this.getActivity() as HasComponent<C>).getComponent())
  }

  /**
   * For Dagger
   */
  fun context(): Context {
    return this.getActivity().getApplicationContext()
  }
}