package com.dididi.kotlindemo.`class`


/**
 * @author dididi(叶超)
 * @email 512dididi@gmail.com
 * @since 12/07/2020
 * @describe 递归整型列表
 * 用递归代替for循环
 */

sealed class IntList {
    /**尾节点，空节点*/
    object Nil : IntList() {
        override fun toString(): String {
            return "Nil"
        }
    }

    /**递归节点*/
    data class Cons(val head: Int, val tail: IntList) : IntList() {
        override fun toString(): String {
            return "$head，$tail"
        }
    }

    fun joinToString(sep: Char = ' '): String {
        return when (this) {
            Nil -> "Nil"
            //递归可以轻松代替for循环
            is Cons -> "$head$sep${tail.joinToString(sep)}"
        }
    }
}

fun intListOf(vararg ints: Int): IntList {
    return when (ints.size) {
        0 -> IntList.Nil
        else -> IntList.Cons(
            ints[0],
            //array前加* ，可以使数组展开为vararg
            intListOf(*ints.slice(1 until ints.size).toIntArray())
        )
    }
}

fun IntList.sum():Int{
    return when (this) {
        IntList.Nil -> 0
        is IntList.Cons -> head + tail.sum()
    }
}

/**component方法可重载，以实现解构*/
operator fun IntList.component1():Int{
    return when(this){
        IntList.Nil -> 0
        is IntList.Cons -> head
    }
}

operator fun IntList.component2():Int{
    return when(this){
        IntList.Nil -> 0
        is IntList.Cons -> tail.component1()
    }
}

operator fun IntList.component3():Int{
    return when(this){
        IntList.Nil -> 0
        is IntList.Cons -> tail.component2()
    }
}

fun main() {
    val list = intListOf(0, 1, 2, 3)
    println(list)
    println(list.joinToString('-'))
    println(list.sum())
    val (first,second,third) = list
    println(first)
    println(second)
    println(third)
}