package com.dididi.kotlindemo.function

import java.util.concurrent.Executors


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 10/06/2020
 * @describe SAM(Single Abstract Method)转换
 * SAM转换的类型一定是接口类型
 */

fun main() {
    //kotlin的SAM转换不支持kotlin接口，因此只能采取匿名内部类的写法，并且不能简写
    addSamInterface(object : SamInterface {
        override fun invoke() {

        }
    })
    //kotlin的SAM转换不支持kotlin方法(Java接口传参)，只能采用匿名内部类写法，但可以简写为Runnable{}
    addRunnable(Runnable {
        println("hello")
    })
    //java方法+kotlin接口，同样不支持SAM转换
    Event.addSamInterface(object : SamInterface {
        override fun invoke() {

        }
    })
    //kotlin需要用SAM时，应使用如下写法，直接通过函数传递参数，避免通过接口
    addSamInterface {

    }
    val executor = Executors.newSingleThreadExecutor()
    //kotlin的SAM转换仅支持java方法的java接口
    executor.submit {
        println("hello")
    }
    val listener = object:Event.EventListener{
        override fun invoke() {
            println("invoke")
        }
    }
    //如果需要添加或移除java方法中的listener，使用实例化匿名内部类的方式，
    //不要直接使用lambda表达式，因为lambda表达式传入，最后是在接口方法中调用lambda表达式，而非直接创建匿名内部类
    Event.addEventListener(listener)
    Event.removeEventListener(listener)
}

/**
 * kotlin传入kotlin接口，一般不这么写
 */
fun addSamInterface(listener: SamInterface) {
    listener.invoke()
}

/**
 * kotlin传入java接口
 */
fun addRunnable(runnable: Runnable) {
    runnable.run()
}

typealias FunctionX = () -> Unit

/**
 * 可以通过传入函数类型的参数，来代替上述SAM写法
 */
fun addSamInterface(functionX: FunctionX) {
    functionX.invoke()
}

/**
 * Runnable{} 的写法等价于 runnable(block) ，所谓的lambda表达式实际是以传参在run方法中调用
 */
val runnable1 = Runnable{}
fun runnable(block:()->Unit):Runnable{
    return object :Runnable{
        override fun run() {
            block()
        }
    }
}

interface SamInterface {
    fun invoke()
}