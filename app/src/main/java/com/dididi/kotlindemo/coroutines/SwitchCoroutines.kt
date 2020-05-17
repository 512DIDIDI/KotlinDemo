package com.dididi.kotlindemo.coroutines

import kotlinx.coroutines.*


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
        println("1.thread:${Thread.currentThread().name}")
        //第一种切换方式 使用launch(CoroutineContext) 指定切换线程
        launch(Dispatchers.IO) {
            println("3.thread:${Thread.currentThread().name}")
        }
        println("2.thread:${Thread.currentThread().name}")
        //第二种切换方式 使用withContext(CoroutineContext) 切换到指定线程，并在闭包结束后切换回去
        withContext(Dispatchers.IO){
            println("4.thread:${Thread.currentThread().name}")
        }
        println("5.thread:${Thread.currentThread().name}")
    }
}