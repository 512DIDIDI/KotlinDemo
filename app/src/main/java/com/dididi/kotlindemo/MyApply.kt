package com.dididi.kotlindemo


/**
 * @author dididi(yechao)
 * @since 16/01/2019
 * @describe 实际内部实现使用的是内联函数
 * .前面的是调用者 即this ()内的是参数 即it
 *  (当被声明为内联函数时，
 *  lambda函数体在调用时将不会作为一个匿名类对象，
 *  而是跟随函数实现以真实函数的方式写入调用处，
 *  内联函数的优点是不会创建新的函数体对象，减少了运行时的开销)
 */

fun test(){
    Any().also {
        println(it)
    }
    Any().apply{
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
}

//apply实现

inline fun <T> T.myApply(apply: T.() -> Unit): T {
    apply()
    return this
}

//also实现
fun <T> T.myAlso(also: (T) -> Unit): T {
    also(this)
    return this
}

//let实现 let返回值和调用者没有关系
fun <T, R> T.myLet(let: (T) -> R): R {
    return let(this)
}

//run实现
fun <T, R> T.myRun(run: T.() -> R): R {
    return run()
}

//with实现
fun <T, R> myWith(parameter: T, with: T.() -> R): R {
    return parameter.with()
}