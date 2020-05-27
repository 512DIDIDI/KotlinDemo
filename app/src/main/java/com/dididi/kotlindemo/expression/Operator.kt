package com.dididi.kotlindemo.expression

import java.util.*


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 27/05/2020
 * @describe 运算符与中缀表达式
 * https://www.kotlincn.net/docs/reference/keyword-reference.html
 */

fun main() {
    val a = 2
    val b = 3
    //运算符重载
    a + b
    a.plus(b)
    a - b
    a.minus(b)
    "hello" == "world"
    "hello".equals("world")
    val list = listOf(1, 2, 3, 4)
    1 in list
    list.contains(1)
    val map = mutableMapOf("hello" to 2, "world" to 3)
    map["hello"] = 1
    map.put("hello", 1)
    map["hello"]
    map.get("hello")
    a > 3
    a.compareTo(3)
    func()
    func.invoke()
}

//函数引用
val func = fun(){}