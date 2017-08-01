package com.mooveit.kotlin.kotlintemplateproject.presentation.internal.di.module

import android.app.Application
import android.content.Context
import com.mooveit.kotlin.kotlintemplateproject.data.executor.JobExecutor
import com.mooveit.kotlin.kotlintemplateproject.domain.excecutor.PostExecutionThread
import com.mooveit.kotlin.kotlintemplateproject.domain.excecutor.ThreadExecutor
import com.mooveit.kotlin.kotlintemplateproject.presentation.common.executor.UIThread
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val mApplication: Application) {

    @Provides
    @Singleton
    internal fun providesApplication(): Application {
        return mApplication
    }

    @Provides
    @Singleton
    internal fun provideApplicationContext(): Context {
        return mApplication
    }

    @Provides
    @Singleton
    internal fun provideThreadExecutor(): ThreadExecutor {
        return JobExecutor()
    }

    @Provides
    @Singleton
    internal fun providePostExecutionThread(uiThread: UIThread): PostExecutionThread {
        return uiThread
    }
}