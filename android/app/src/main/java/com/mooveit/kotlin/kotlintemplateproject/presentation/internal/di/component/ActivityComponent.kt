package com.mooveit.kotlin.kotlintemplateproject.presentation.internal.di.component

import com.mooveit.kotlin.kotlintemplateproject.presentation.internal.di.module.ActivityModule
import com.mooveit.kotlin.kotlintemplateproject.presentation.internal.di.scope.PerActivity
import com.mooveit.kotlin.kotlintemplateproject.presentation.view.home.HomeActivity
import dagger.Component
import javax.inject.Singleton

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(activity: HomeActivity)
}