package com.kompody.etnetera.ui.base

import com.kompody.etnetera.R
import com.kompody.etnetera.domain.error.AppException
import com.kompody.etnetera.utils.ResourceDelegate
import timber.log.Timber

class ErrorCommandOf : CommandOf<String>() {

    fun execute(resourceDelegate: ResourceDelegate, throwable: Throwable) {
        val message = makeErrorMessage(resourceDelegate, throwable)
        super.execute(message)
        Timber.e(throwable)
    }

    private fun makeErrorMessage(resourceDelegate: ResourceDelegate, throwable: Throwable): String {
        return when (throwable) {
            is AppException.Api.NotSuccess -> resourceDelegate.getString(R.string.error_load)
            is AppException.Api.Error -> throwable.msg
            else -> resourceDelegate.getString(R.string.error_load)
        }
    }
}