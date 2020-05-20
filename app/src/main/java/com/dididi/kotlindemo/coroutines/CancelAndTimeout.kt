package com.dididi.kotlindemo.coroutines

import kotlinx.coroutines.*


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 18/05/2020
 * @describe 取消与超时
 * cancel的应用场景    对后台协程进行细粒度控制 例如activity退出等，可以在销毁时cancel掉任务
 */

fun main() = runBlocking {
      //只会打印出三次结果，因为job被cancel了
//    val job = launch {
//        repeat(10) { i ->
//            println("job: I'm sleeping $i ...")
//            delay(500L)
//        }
//    }
//    delay(1300L) // 延迟一段时间
//    println("main: I'm tired of waiting!")
//    //取消job内的任务
//    job.cancel()
//    //将job内的任务执行完毕
//    job.join()
//    println("main: Now I can quit.")

    //job内的任务会全部打印
//    val startTime = System.currentTimeMillis()
//    val countJob = launch(Dispatchers.Default) {
//        var nextPrintTime = startTime
//        var i = 0
//        //因为在执行计算，并且没有检查取消状态，所以job不会被取消
//        while (i < 5) { // 一个执行计算的循环，只是为了占用 CPU
//            // 每秒打印消息两次
//            if (System.currentTimeMillis() >= nextPrintTime) {
//                println("job: I'm sleeping ${i++} ...")
//                nextPrintTime += 500L
//            }
//        }
//    }
//    delay(1300L) // 等待一段时间
//    println("main: I'm tired of waiting!")
//    countJob.cancelAndJoin() // 取消一个作业并且等待它结束
//    println("main: Now I can quit.")

    //通过显示检查取消状态 打印结果只有三个
//    val startTime = System.currentTimeMillis()
//    val job = launch(Dispatchers.Default) {
//        var nextPrintTime = startTime
//        var i = 0
//        while (isActive) { // 可以被取消的计算循环
//            // 每秒打印消息两次
//            if (System.currentTimeMillis() >= nextPrintTime) {
//                println("job: I'm sleeping ${i++} ...")
//                nextPrintTime += 500L
//            }
//        }
//    }
//    delay(1300L) // 等待一段时间
//    println("main: I'm tired of waiting!")
//    job.cancelAndJoin() // 取消该作业并等待它结束
//    println("main: Now I can quit.")

    //可以使用finally捕获取消的协程，释放资源
//    val job = launch {
//        try {
//            repeat(1000) { i ->
//                println("job: I'm sleeping $i ...")
//                delay(500L)
//            }
//        } finally {
//            println("job: I'm running finally")
//        }
//    }
//    delay(1300L) // 延迟一段时间
//    println("main: I'm tired of waiting!")
//    job.cancelAndJoin() // 取消该作业并且等待它结束
//    println("main: Now I can quit.")

//    //释放资源时，需要进行异步操作
//    val job = launch {
//        try {
//            repeat(1000) { i ->
//                println("job: I'm sleeping $i ...")
//                delay(500L)
//            }
//        } finally {
//            //这里为什么要提到这个withContext 因为如果释放资源时，需要进行异步操作，可以使用withContext(NonCancellable)来操作
//            withContext(NonCancellable) {
//                println("job: I'm running finally")
//                delay(1000L)
//                println("job: And I've just delayed for 1 sec because I'm non-cancellable")
//            }
//        }
//    }
//    delay(1300L) // 延迟一段时间
//    println("main: I'm tired of waiting!")
//    job.cancelAndJoin() // 取消该作业并等待它结束
//    println("main: Now I can quit.")

    //当协程执行任务超时时，可以通过此方法抛出超时异常
//    withTimeout(1300L) {
//        repeat(1000) { i ->
//            println("I'm sleeping $i ...")
//            delay(500L)
//        }
//    }

    //用result的值来判断执行任务是否超时
    val result = withTimeoutOrNull(1300L) {
        repeat(2) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
        //如果任务没有超时，则返回最后一行，否则返回null
        "Done"
    }
    println("Result is $result")
}