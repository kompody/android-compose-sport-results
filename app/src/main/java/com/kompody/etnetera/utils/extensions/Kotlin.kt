package com.kompody.etnetera.utils.extensions

inline fun <reified T> Any?.unsafeCastTo(): T {
    return this as T
}

inline fun <reified T> Any?.castTo(): T? {
    return this as? T
}