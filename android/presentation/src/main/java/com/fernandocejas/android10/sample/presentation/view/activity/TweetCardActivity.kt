package com.fernandocejas.android10.sample.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import com.fernandocejas.android10.sample.presentation.R
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerUseCaseComponent
import com.fernandocejas.android10.sample.presentation.internal.di.components.UseCaseComponent
import com.fernandocejas.android10.sample.presentation.view.fragment.TweetCardFragment

/**
 * Activity for getting Tweet List.
 *
 * Created on 8/14/17.
 */
class TweetCardActivity : BaseActivity(), HasComponent<UseCaseComponent> {

  lateinit var usecaseComponent: UseCaseComponent

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_layout)

    this.initializeInjector()
    if (savedInstanceState == null) {
      addFragment(R.id.fragmentContainer, TweetCardFragment())
    }
  }

  fun initializeInjector() {
    this.usecaseComponent = DaggerUseCaseComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build()
  }

  override fun getComponent(): UseCaseComponent {
    return usecaseComponent
  }
}

/**
 * Instead of static method
 */
fun getCallingIntent(context: Context): Intent {
  return Intent(context, TweetCardActivity::class.java)
}