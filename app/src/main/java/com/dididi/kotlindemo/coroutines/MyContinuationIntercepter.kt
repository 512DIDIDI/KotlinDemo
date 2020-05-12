package com.dididi.kotlindemo.coroutines

import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext


/**
 * @author dididi(yechao)
 * @since 12/12/2019
 * @describe
 */

class MyContinuationIntercepter : ContinuationInterceptor {
    override val key: CoroutineContext.Key<*>
        get() = ContinuationInterceptor

    override fun <T> interceptContinuation(continuation: Continuation<T>) =
        MyContinuation(continuation)
}