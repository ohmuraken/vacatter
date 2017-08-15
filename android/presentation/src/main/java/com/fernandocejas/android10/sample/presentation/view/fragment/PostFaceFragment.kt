package com.fernandocejas.android10.sample.presentation.view.fragment

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
import com.fernandocejas.android10.sample.presentation.internal.di.components.UseCaseComponent
import com.fernandocejas.android10.sample.presentation.presenter.PostFacePresenter
import com.fernandocejas.android10.sample.presentation.view.LoadDataView
import javax.inject.Inject


/**
 *
 *
 * Created on 8/14/17.
 */
class PostFaceFragment : BaseFragment(), LoadDataView {
  private val READ_REQUEST_CODE = 42

  @Inject lateinit var postFacePresenter: PostFacePresenter
  @Bind(R.id.btn_StorageAccess) lateinit var btn_StorageAccess: Button

  init {
    setRetainInstance(true)
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    this.getComponent(UseCaseComponent::class.java).inject(this)
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View {
    val fragmentView = inflater!!.inflate(R.layout.fragment_post_face, container, false)
    ButterKnife.bind(this, fragmentView)
    return fragmentView
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    postFacePresenter.setView(this)
  }

  override fun showLoading() {
  }

  override fun hideLoading() {
  }

  override fun showRetry() {
  }

  override fun hideRetry() {
  }

  override fun showError(message: String?) {
  }

  override fun context(): Context {
    return this.getActivity().getApplicationContext()
  }

  @OnClick(R.id.btn_StorageAccess)
  fun onButtonStorageAccess() {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    intent.type = "image/*"
    startActivityForResult(intent, READ_REQUEST_CODE)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
    super.onActivityResult(requestCode, resultCode, resultData)

    if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
      postFacePresenter.postFace(resultData)
    }
  }
}