package com.dididi.kotlindemo.expression

import java.lang.Exception


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 27/05/2020
 * @describe 分支表达式
 */

fun main() {
    var a = true
    //if-else表达式 等价于java中的三元表达式
    val c: Any = if (a) 0 else 1
    a = false
    //when表达式 类似java中的switch-case 但when的作用域更广
    //支持基本类型
    val b = when (c) {
        0 -> 2
        1 -> 3
        else -> -1
    }
    //支持判断
    val d = when {
        c is String -> 2
        b == 2 -> 3
        else -> -1
    }
    //when中可赋值
    val e = when (val input = readLine()) {
        null -> 0
        else -> input.length
    }
    //try-catch表达式
    val f = try {
        b / d
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }
    println("c$c")
    println("b$b")
    println("d$d")
    println("e$e")
    println("f$f")
}