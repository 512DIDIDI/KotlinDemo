package com.dididi.kotlindemo.`class`


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 25/05/2020
 * @describe 拓展方法
 */

/**
 * 通过在方法前加receiver即可实现拓展方法
 */
fun String.times(count:Int) = (1..count).joinToString("") { this }

fun main(){
    //调用者可在任意处使用
    println(".".times(5))
    //获取拓展方法引用
    val times = String::times
    println(times("*",2))
    val a = "."
    //绑定receiver的引用
    val aTimes = a::times
    println(aTimes(5))
}

