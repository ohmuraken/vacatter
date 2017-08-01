package com.mooveit.kotlin.kotlintemplateproject.presentation.internal.di.component

import android.app.Application
import android.content.Context
import com.mooveit.kotlin.kotlintemplateproject.data.repository.PetRepository
import com.mooveit.kotlin.kotlintemplateproject.domain.excecutor.PostExecutionThread
import com.mooveit.kotlin.kotlintemplateproject.domain.excecutor.ThreadExecutor
import com.mooveit.kotlin.kotlintemplateproject.presentation.internal.di.module.ApplicationModule
import com.mooveit.kotlin.kotlintemplateproject.presentation.internal.di.module.NetworkModule
import com.mooveit.kotlin.kotlintemplateproject.presentation.internal.di.module.RepositoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, RepositoryModule::class, NetworkModule::class))
interface ApplicationComponent {

    fun application(): Application

    fun context(): Context

    fun threadExecutor(): ThreadExecutor

    fun postExecutionThread(): PostExecutionThread

    fun userRepository(): PetRepository
}