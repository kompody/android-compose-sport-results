package com.kompody.etnetera.ui.base

import com.kompody.etnetera.R
import com.kompody.etnetera.domain.error.AppException
import com.kompody.etnetera.utils.ResourceDelegate
import timber.log.Timber

class ErrorCommandOf : CommandOf<String>() {

    fun execute(resourceDelegate: ResourceDelegate, exception: Exception) {
        val message = makeErrorMessage(resourceDelegate, exception)
        super.execute(message)
        Timber.e(exception)
    }

    private fun makeErrorMessage(resourceDelegate: ResourceDelegate, exception: Exception): String {
        return when (exception) {
            is AppException.Api.NotSuccess -> resourceDelegate.getString(R.string.error_load)
            is AppException.Api.Error -> exception.msg
            else -> resourceDelegate.getString(R.string.error_load)
        }
    }
}