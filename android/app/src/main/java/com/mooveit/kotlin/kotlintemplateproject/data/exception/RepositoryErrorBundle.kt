package com.mooveit.kotlin.kotlintemplateproject.data.exception

import com.mooveit.kotlin.kotlintemplateproject.domain.exception.ErrorBundle

internal class RepositoryErrorBundle(override val exception: Exception?) : ErrorBundle {

    override val errorMessage: String
        get() {
            var message = ""
            if (this.exception != null && this.exception.message != null) {
                message = this.exception.message!!
            }
            return message
        }
}