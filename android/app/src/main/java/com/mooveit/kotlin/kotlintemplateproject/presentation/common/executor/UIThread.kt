package com.mooveit.kotlin.kotlintemplateproject.presentation.common.executor

import com.mooveit.kotlin.kotlintemplateproject.domain.excecutor.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class UIThread @Inject
internal constructor() : PostExecutionThread {

    override val scheduler: Scheduler = AndroidSchedulers.mainThread()
}
