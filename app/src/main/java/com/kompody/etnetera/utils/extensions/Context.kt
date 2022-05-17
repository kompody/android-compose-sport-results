package com.kompody.etnetera.utils.extensions

import android.content.Context
import android.widget.Toast
import com.kompody.etnetera.BuildConfig

fun Context.showToast(msg: String) {
    if (!BuildConfig.DEBUG) return

    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}