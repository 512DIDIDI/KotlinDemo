package com.dididi.kotlindemo.function

import com.dididi.kotlindemo.function.cost

/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 02/06/2020
 * @describe 内联函数
 * 所谓内联(inline)，就是在编译时将内联函数替换到调用处
 * 适用：高阶函数(有函数类型的传参)
 * 优点：1.高阶函数的调用被替换，节省高阶函数调用的开销 2.节省了创建lambda表达式的开销
 * 过程：两次内联 1.高阶函数内联到调用处 2.lambda表达式内联到高阶函数参数调用处
 * 性能对比：Lambda[cost]调用处的时间与本类中[main]方法的时间消耗对比
 */

fun main(){
    val start = System.currentTimeMillis()
    costInline {
        //获取实例化后的函数对象
        val fibonacci = fibonacci()
        for (i in 0..10) {
            //注意 一定要使用invoke()方法，否则得到的是函数引用的类型
            println(fibonacci())
        }
    }
    //使用了内联函数 这里的打印时间与内联函数内的耗时一样。
    println("main:${System.currentTimeMillis() - start}ms")
}

/**
 * 高阶函数可以使用内联标记来优化性能
 */
inline fun costInline(block:()->Unit){
    val start = System.currentTimeMillis()
    block()
    println("cost:${System.currentTimeMillis() - start}ms")
}