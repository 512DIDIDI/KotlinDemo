package com.dididi.kotlindemo.coroutines

import kotlin.coroutines.*


/**
 * @author dididi(yechao)
 * @since 11/09/2020
 * @describe 协程的挂起是如何实现的
 */
class ContinuationImpl(private val completion:Continuation<Unit>):Continuation<Any>{

    private var label = 0

    override val context: CoroutineContext
        get() = EmptyCoroutineContext

    override fun resumeWith(result: Result<Any>) {
        try {
            when(label){
                0 -> {}
                1 -> {}
            }
            completion.resume(Unit)
        }catch (e:Exception){
            completion.resumeWithException(e)
        }
    }

    /**
     * 判断是否挂起，
     */
    private fun isSuspended(result:Any)
     = result == kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
}
