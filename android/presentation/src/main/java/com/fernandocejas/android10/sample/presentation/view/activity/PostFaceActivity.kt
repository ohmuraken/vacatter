package com.fernandocejas.android10.sample.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.fernandocejas.android10.sample.domain.interactor.PostFace
import com.fernandocejas.android10.sample.presentation.R
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerUseCaseComponent
import com.fernandocejas.android10.sample.presentation.internal.di.components.UseCaseComponent
import com.fernandocejas.android10.sample.presentation.view.fragment.PostFaceFragment
import com.fernandocejas.android10.sample.presentation.view.fragment.TweetCardFragment

/**
 *
 *
 * Created on 8/14/17.
 */
class PostFaceActivity : BaseActivity(), HasComponent<UseCaseComponent> {

  lateinit var usecaseComponent: UseCaseComponent

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_layout)

    initializeInjector()
    if (savedInstanceState == null) {
      addFragment(R.id.fragmentContainer, PostFaceFragment())
    }
  }

  private fun initializeInjector() {
    usecaseComponent = DaggerUseCaseComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build()
  }

  override fun getComponent(): UseCaseComponent {
    return usecaseComponent
  }

  companion object {
    fun getCallingIntent(context: Context): Intent {
      return Intent(context, PostFaceActivity::class.java)
    }
  }
}
