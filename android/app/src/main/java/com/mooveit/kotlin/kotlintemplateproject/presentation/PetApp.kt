package com.mooveit.kotlin.kotlintemplateproject.presentation

import android.app.Application
import com.mooveit.kotlin.kotlintemplateproject.BuildConfig
import com.mooveit.kotlin.kotlintemplateproject.presentation.common.Constants
import com.mooveit.kotlin.kotlintemplateproject.presentation.internal.di.component.ApplicationComponent
import com.mooveit.kotlin.kotlintemplateproject.presentation.internal.di.component.DaggerApplicationComponent
import com.mooveit.kotlin.kotlintemplateproject.presentation.internal.di.module.ApplicationModule
import com.mooveit.kotlin.kotlintemplateproject.presentation.internal.di.module.NetworkModule
import com.mooveit.kotlin.kotlintemplateproject.presentation.internal.di.module.RepositoryModule
import com.squareup.leakcanary.LeakCanary

class PetApp : Application() {

    lateinit var mApplicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        initializeInjector()
        initializeLeakDetection()
    }

    private fun initializeInjector() {
        this.mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .repositoryModule(RepositoryModule())
                .networkModule(NetworkModule(Constants.Api.BASE_API_URL))
                .build()
    }

    private fun initializeLeakDetection() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this)
        }
    }
}