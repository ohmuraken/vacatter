package com.mooveit.kotlin.kotlintemplateproject.presentation.common.presenter

import android.view.View
import com.mooveit.kotlin.kotlintemplateproject.domain.interactor.UseCase

open class Presenter(vararg useCases: UseCase<*, *>) {

    private val useCasesList: List<UseCase<*, *>> = ArrayList()

    init {
        for (userCase in useCases) {
            useCasesList.plus(userCase)
        }
    }

    open fun onResume(view: View) {}

    open fun onPause() {
        for (userCase in useCasesList) {
            userCase.dispose()
        }
    }

    open fun onDestroy() {
    }
}