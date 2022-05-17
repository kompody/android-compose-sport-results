package com.kompody.etnetera.ui.base

import timber.log.Timber

class ErrorCommandOf : CommandOf<Exception>() {
    override fun execute(exception: Exception?) {
        Timber.e(exception)
        super.execute(exception)
    }
}