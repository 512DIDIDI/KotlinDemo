package com.dididi.kotlindemo.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext


/**
 * @author dididi(yechao)
 * @since 12/12/2019
 * @describe
 */

suspend fun main() {
    CoroutineScope(Dispatchers.IO).launch {
        withContext(Dispatchers.Unconfined){
            log(Thread.currentThread().name)
        }
        log(Thread.currentThread().name)
    }.join()
}

//类型别名 用于缩短1.较长的泛型类型 2.函数 3.内部类或嵌套类
typealias Callback = (Int) -> Unit

suspend inline fun Job.Key.currentJob() = coroutineContext[Job]

private fun log(number: Any?) {
    println(number)
}

