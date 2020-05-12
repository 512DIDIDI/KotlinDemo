package com.dididi.kotlindemo.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


/**
 * @author dididi(yechao)
 * @since 12/05/2020
 * @describe 切换协程的方式
 * [Dispatchers] 通过调度器将协程限定在指定的线程执行，或将它分派到一个线程池，亦或是让它不受限地运行
 * [Dispatchers.IO] IO线程 主要用来进行网络请求或文件读写工作
 * [Dispatchers.Main] 主线程 主要用来更新ui
 * [Dispatchers.Default] 使用共享的线程池
 * [Dispatchers.Unconfined] 不局限任何线程
 */

fun main() = runBlocking<Unit>{
    //主线程
    launch {
        println("thread:${Thread.currentThread().name}")
        //切换到IO线程
        launch(Dispatchers.IO) {
            println("thread:${Thread.currentThread().name}")
        }
    }
}