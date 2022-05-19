package com.kompody.etnetera.utils.extensions

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

suspend fun <T> io(
    block: suspend CoroutineScope.() -> T
) : T = withContext(Dispatchers.IO) { block(this) }

suspend fun <T> computation(
    block: suspend CoroutineScope.() -> T
) : T = withContext(Dispatchers.Default) { block(this) }

suspend fun <T> main(
    block: suspend CoroutineScope.() -> T
) : T = withContext(Dispatchers.Default) { block(this) }

fun <T1, T2> combineFlow(
    flow1: Flow<T1>,
    flow2: Flow<T2>
) = combine(flow1, flow2) { t1, t2 -> t1 to t2}

fun CoroutineScope.launchDelayAsync(
    repeatMillis: Long,
    action: () -> Unit
) = this.async {
    delay(repeatMillis)
    action()
}

fun <T> timer(value: T, timeMillis: Long): Flow<T> = flow {
    delay(timeMillis)
    emit(value)
}