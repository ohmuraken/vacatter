package com.mooveit.kotlin.kotlintemplateproject.domain.exception

interface ErrorBundle {

    val exception: Exception?

    val errorMessage: String
}