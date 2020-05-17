package com.dididi.kotlindemo.coroutines

import kotlinx.coroutines.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread


/**
 * @author dididi(yechao)
 * @since 13/05/2020
 * @describe 协程性能
 * 实际上协程是基于Thread封装的工具包
 */

fun main() = runBlocking<Unit>{
    repeat(100000){
        launch {
            delay(1000)
            println(".")
        }
    }
}

//相对于直接使用Thread，性能有很大提升
//fun main(){
//    repeat(100000){
//        thread {
//            Thread.sleep(1000)
//            print(".")
//        }
//    }
//}

//但实际上对于java来说，不应该直接用Thread和Thread.sleep()，而应该使用线程池创建线程
//相对于java的Executors线程池，没有实质上的性能提升
//fun main() {
//    val executor = Executors.newSingleThreadScheduledExecutor()
//    var task: Runnable? = null
//    repeat(100000) {
//        task = Runnable {
//            print(".")
//        }
//    }
//    repeat(100000){
//        executor.schedule(task, 1, TimeUnit.SECONDS)
//    }
//}
