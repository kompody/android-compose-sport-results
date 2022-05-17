package com.kompody.etnetera.ui.base

import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val errors = ErrorCommandOf()
}