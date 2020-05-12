package com.dididi.kotlindemo.list


/**
 * @author dididi(yechao)
 * @since 06/09/2019
 * @describe
 */

fun main() {
    val list = listOf(1, 3, 5, 7, 9)
    println(list.myCount { it > 4 })

}

/**
 * List.Any方法:判断list中是否有满足条件的值
 */
fun <T> List<T>.myAny(any: (T) -> Boolean): Boolean {
    forEach {
        if (any(it)) {
            return true
        }
    }
    return false
}

/**
 * List.All:判断list中所有的值是否都满足条件
 */
fun <T> List<T>.myAll(all: (T) -> Boolean): Boolean {
    forEach {
        if (!all(it)) {
            return false
        }
    }
    return true
}

/**
 * List.Count:计算list中满足条件的值的个数
 */
fun <T> List<T>.myCount(count: (T) -> Boolean): Int {
    var c = 0
    forEach {
        if (count(it)) {
            c++
        }
    }
    return c
}