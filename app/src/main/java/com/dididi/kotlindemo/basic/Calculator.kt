package com.dididi.kotlindemo.basic

import java.lang.Exception
import java.util.*


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 21/05/2020
 * @describe 四则计算器
 */

fun main() {
    //获取键盘一行输入使用readLine() 返回String字符串
//    val input = readLine()?.toCharArray() ?: return showHelp()
    //也可以使用Scanner获取特定的输入类型 next() nextInt() nextFloat()...
    val read = Scanner(System.`in`)
    val list = mutableListOf<String>()
    //输入对应的计算操作
    val operator = mapOf(
        Pair("+", ::plus), Pair("-", ::minus), Pair("*", ::times), Pair("/", ::div)
    )
    println("enter first arg:")
    list += read.next()
    println("enter the operation:")
    //注意调用一次read.next() 就需要输入一次
    val op = read.next()
    if (op in operator.keys) {
        list += op
    } else {
        return showHelp()
    }
    println("enter second arg:")
    list += read.next()
    val opFunc = operator[op] ?: return showHelp()
    try {
        println("input:${list.joinToString(" ")}")
        println("output:${opFunc(list[0].toInt(), list[2].toInt())}")
    } catch (e: Exception) {
        return showHelp()
    }
}

fun plus(arg0: Int, arg1: Int) = arg0 + arg1
fun minus(arg0: Int, arg1: Int) = arg0 - arg1
fun times(arg0: Int, arg1: Int) = arg0 * arg1
fun div(arg0: Int, arg1: Int) = arg0 / arg1

fun showHelp() {
    println(
        """
    calculator help(only support + - * /) eg:
        input:3+2
        output:5
    """.trimIndent()
    )
}