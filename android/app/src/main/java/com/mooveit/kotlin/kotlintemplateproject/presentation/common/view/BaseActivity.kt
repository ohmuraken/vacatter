package com.mooveit.kotlin.kotlintemplateproject.presentation.common.view

import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.mooveit.kotlin.kotlintemplateproject.presentation.PetApp
import com.mooveit.kotlin.kotlintemplateproject.presentation.internal.di.component.ApplicationComponent

abstract class BaseActivity : AppCompatActivity() {

    protected fun addFragment(containerViewId: Int, fragment: Fragment) {
        val fragmentTransaction = this.fragmentManager.beginTransaction()
        fragmentTransaction.add(containerViewId, fragment)
        fragmentTransaction.commit()
    }

    protected val mApplicationComponent: ApplicationComponent
        get() = (application as PetApp).mApplicationComponent
}