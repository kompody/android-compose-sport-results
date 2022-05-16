package com.kompody.etnetera.domain.error

import java.io.IOException

sealed class AppException : IOException() {
    sealed class Api : AppException() {
        object NotValidToken : Api()
        object NotSuccess : Api()
        class Error(val msg: String) : Api()
    }

    class Error(val exception: Exception): AppException()
}

