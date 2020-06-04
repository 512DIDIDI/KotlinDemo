package com.dididi.kotlindemo.function

import java.io.File


/**
 * @author dididi(yechao)
 * @since 16/01/2019
 * @describe 实际内部实现使用的是内联函数
 * .前面的是调用者 即this ()内的是参数 即it
 *  (当被声明为内联函数时，
 *  lambda函数体在调用时将不会作为一个匿名类对象，
 *  而是跟随函数实现以真实函数的方式写入调用处，
 *  内联函数的优点是不会创建新的函数体对象，减少了运行时的开销)
 *  apply
 *  also  返回值为调用者本身                     推荐使用
 *  let   返回值为lambda表达式的返回值            推荐使用
 *  run
 *  use   用于 [Closeable] 的异常处理和资源关闭   推荐使用
 */

fun main() {
    Any().also {
        println(it)
    }
    //apply容易嵌套过深导致receiver不明，因为都是this 不知道使用哪个apply,建议使用also和let
    Any().apply {
        val test = this
        apply {
            val hello = this@apply
            3.apply {
                test
                hello
                val f = this@apply
            }
        }
        println(this)
    }
    val a = Any().let{
        "hello"
    }
    //kotlin读取文件流
    // inputStream()获取InputStream流
    // reader()把inputStream包装成reader
    // buffered()把reader包装成bufferedReader
    File("build.gradle").inputStream().reader().buffered()
            //使用use函数 kotlin实现了资源的close和各种异常的处理
        .use {
            println(it.readLines())
        }
}

//apply实现                       不推荐 因为容易嵌套造成receiver不明
inline fun <T> T.myApply(apply: T.() -> Unit): T {
    apply()
    return this
}

//also实现                        推荐使用
inline fun <T> T.myAlso(also: (T) -> Unit): T {
    also(this)
    return this
}

//let实现 let返回值和调用者没有关系  推荐使用
inline fun <T, R> T.myLet(let: (T) -> R): R {
    return let(this)
}

//run实现                          不推荐 因为容易嵌套造成receiver不明
inline fun <T, R> T.myRun(run: T.() -> R): R {
    return run()
}

//with实现
inline fun <T, R> myWith(parameter: T, with: T.() -> R): R {
    return parameter.with()
}