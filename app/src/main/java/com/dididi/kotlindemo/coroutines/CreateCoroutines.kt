package com.dididi.kotlindemo.coroutines

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis


/**
 * @author dididi(yechao)
 * @since 12/05/2020
 * @describe 协程的三种创建方式 协程的作用域在闭包范围内
 */

fun main(){
    //方法一 使用runBlocking顶层函数创建
    //调用了runBlocking的主线程会一直阻塞直到runBlocking内部的协程执行完毕 主要用在main函数或测试中
    runBlocking<Unit> {
        //先打印出hello然后等待2s最后打出world 阻塞直到runBlocking内部的协程执行完毕
        println("hello")
        //delay只是挂起协程但不阻塞线程
        delay(2000)
        println("world")
    }

    //方法二 使用GlobalScope单例对象直接调用launch开启协程
    //不会线程阻塞 但生命周期会与app相同 且不能取消，消耗内存
    GlobalScope.launch {
        //只会打出hello不会等待2s不会打印world 不阻塞线程
        println("hello")
        delay(2000)
        println("world")
    }

    //方法三 通过实例化CoroutineScope对象，调用launch开启协程
    //CoroutineScope()实例化需要CoroutineContext参数
    //推荐使用  通过CoroutineContext来管理和控制生命周期
    val coroutineScope = CoroutineScope(Dispatchers.IO)
    coroutineScope.launch {

    }


    runBlocking {
        launch {
            delay(200L)
            println("2.Task from runBlocking")
        }
        // 创建一个协程作用域，并在所有已启动的子协程执行完毕前不会结束
        //同runBlocking类似，但其会释放底层线程用于其他用途
        coroutineScope {
            launch {
                delay(500L)
                println("3.Task from nested launch")
            }

            delay(100L)
            //打印出这句话后，会去执行runBlocking launch中的线程，因为其不会阻塞线程 只是挂起。
            println("1.Task from coroutine scope") // 这一行会在内嵌 launch 之前输出
        }

        println("4.Coroutine scope is over") // 这一行在内嵌 launch 执行完毕后才输出
    }

}