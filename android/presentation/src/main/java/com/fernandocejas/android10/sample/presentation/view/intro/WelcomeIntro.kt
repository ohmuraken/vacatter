package com.fernandocejas.android10.sample.presentation.view.intro

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.fernandocejas.android10.sample.presentation.R

/**
 *
 *
 * Created on 2017/08/15.
 */
class WelcomeIntro : Fragment(){
  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View {
    val fragmentView = inflater!!.inflate(R.layout.intro_welcome, container, false)
    return fragmentView
  }
}