package com.dididi.kotlindemo.coroutines

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 18/05/2020
 * @describe 使用[Async]
 */

fun main() = runBlocking {
    //顺序调用suspend函数，耗时会是suspend函数相加
    val time = measureTimeMillis {
        val one = doSomethingUsefulOne()
        val two = doSomethingUsefulTwo()
        println("The answer is ${one + two}")
    }
    println("Completed in $time ms")

    //使用async并发 能比上面顺序调用减少delay的1000ms
    //async返回的是Deferred 会在调用await方法时得到其最终结果
    //(注意：1.但协程在async的时候就已经启动了 2.await方法只是获取结果)
    val asyncTime = measureTimeMillis {
        val one = async { doSomethingUsefulOne() }
        //这里one已经是活动状态，说明协程在async时已经启动了
        println("isActive?${one.isActive}")
        val two = async { doSomethingUsefulTwo() }
        println("async answer is ${one.await()+two.await()}")
    }
    println("Completed in $asyncTime ms")

    //惰性调用async 使其在调用start才真正启动
    val lazyTime = measureTimeMillis {
        //通过指定其start属性来惰性启动
        val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
        val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
        //这里one处于非活动状态，说明此时协程还未启动
        println("isActive?${one.isActive}")
        //直到其调用start方法 协程才真正启动
        one.start()
        //这里one是活动状态
        println("isActive?${one.isActive}")
        two.start()
        //通过await方法获取其值(注意，如果没有用start方法直接使用await，会导致其按照顺序启动协程 与方法一类似。)
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $lazyTime ms")
}

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // 假设我们在这里做了一些有用的事
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // 假设我们在这里也做了一些有用的事
    return 29
}