package com.mooveit.kotlin.kotlintemplateproject.data.exception

class NetworkConnectionException : Exception {

    constructor() : super()

    constructor(cause: Throwable) : super(cause)
}
