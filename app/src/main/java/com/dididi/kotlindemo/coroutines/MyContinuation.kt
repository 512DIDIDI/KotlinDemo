package com.dididi.kotlindemo.coroutines

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext


/**
 * @author dididi(yechao)
 * @since 12/12/2019
 * @describe
 */

class MyContinuation<T>(val continuation:Continuation<T>):Continuation<T>{
    override val context: CoroutineContext
        get() = continuation.context

    override fun resumeWith(result: Result<T>) {
        continuation.resumeWith(result)
    }
}