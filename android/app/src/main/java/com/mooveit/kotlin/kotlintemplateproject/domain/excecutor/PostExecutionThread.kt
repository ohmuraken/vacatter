package com.mooveit.kotlin.kotlintemplateproject.domain.excecutor

import io.reactivex.Scheduler

interface PostExecutionThread {

    val scheduler: Scheduler
}
