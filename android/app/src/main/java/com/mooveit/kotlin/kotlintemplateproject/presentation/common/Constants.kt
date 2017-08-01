package com.mooveit.kotlin.kotlintemplateproject.presentation.common

interface Constants {

    interface Api {
        companion object {
            val BASE_API_URL = "http://petstore.swagger.io/v2/"
        }
    }

    companion object {

        val PET_TAG = "mooveit"
        val PET_STATUS_AVAILABLE = "available"
    }
}