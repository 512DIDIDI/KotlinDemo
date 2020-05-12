package com.dididi.kotlindemo.list


/**
 * @author dididi(yechao)
 * @since 16/08/2019
 * @describe
 */

fun main(args: Array<String>) {
    args.myMap(::println)
    args.map { println(it) }
}

/**
 * map遍历功能实现机制
 */
fun <T> Array<out T>.myMap(abc: (T) -> Unit) {
    this.forEach {
        abc(it)
    }
}



