package com.dididi.kotlindemo.function


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 09/06/2020
 * @describe 集合变换与序列
 */

fun main() {
    val list = listOf(1, 2, 3, 4)
    //饿汉式执行，一个一个集合变换函数执行
    list
        .filter {
            //filter(过滤器：过滤出符合条件的元素)
            println("filter$it")
            it % 2 == 0
        }.map {
            //map(映射：将元素映射为其它类型或其它值的元素)
            println("map$it")
            "it${it * 2} = $it * 2"
        }.forEach(::println)
    //asSequence转换为序列后，变成懒汉式执行，集合变换变为了中间且无状态的，需要执行forEach才能使其一步一步往下执行
    list.asSequence()
        //这里后面执行的都是sequence的函数
        .filter {
            //filter(过滤器：过滤出符合条件的元素)
            println("filter$it")
            it % 2 == 0
        }.map {
            //map(映射：将元素映射为其它类型或其它值的元素)
            println("map$it")
            "it${it * 2} = $it * 2"
        }.forEach(::println)
    //flatMap:遍历集合内的元素，将lambda表达式返回的集合映射到集合中,将元素映射为集合
    list.flatMap {
        //返回一个IntRange IntRange实现了Iterable
        0 until it
    }.joinToString().let(::println)
    //fold：给定初始值initial 累加(集合内元素)与(lambda表达式返回的结果)，返回初始值的类型
    println(list.fold("") { acc, i ->
        acc + i
    })
    //reduce：与fold同理，只是没有初始值，返回集合内元素类型
    println(list.reduce { acc, i -> acc - i })
    //zip：将另一集合与该集合每个元素对应组成一个新的集合(新集合类型默认为Pair，或者lambda表达式返回类型)
    list.zip(listOf("hello", "dididi", "world", "!")) { a, b ->
        listOf(b + a)
    }.joinToString().let(::println)
}