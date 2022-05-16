package com.kompody.etnetera.data.database.base

interface IModelFieldConverter<T, R> {
    fun saveTo(value: T): R
    fun restoreFrom(value: R): T
}