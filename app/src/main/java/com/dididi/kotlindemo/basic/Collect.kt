package com.dididi.kotlindemo.basic


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 20/05/2020
 * @describe 集合
 * 增加了不可变的集合框架接口(例如[List] [MutableList])
 * 复用Java api实现
 */

fun main(){
    //不可变List 并不是用的java.util.List 而是kotlin.collects.List 没有remove add方法
    val a:List<Int> = listOf(1,2,3,4)
    //可变List 有remove add
    val b:MutableList<Int> = mutableListOf(1,2,3,4)
    //不可变map，键值对可以用 to 来配对 或者是Pair
    val mapA = mapOf<String,Any>("name" to "dididi", "age" to 23)
    //不可变map
    val mapB = mutableMapOf<String,Any>("name" to "dididi", "age" to 23)
    //用的不是java.util.ArrayList 而是kotlin.collects.ArrayList
    //其实内部实现是用 typealias 类型别名来实现的
    //（typealias是为了方便kotlin用于其他平台开发时 能够用相同名字实现该平台的对应集合类）
    val stringList = ArrayList<String>()
    for (i in 0..10){
        //kotlin的add remove语法糖
        //todo: 这里要注意 集合的add 会重载 plus/plusAssign 方法 如果使用val 就会默认使用plusAssign方法
        stringList += "num:$i"
        b -= i
    }
    //集合的set与get list map都是一样
    stringList[3] = "hello world"
    println("stringList:${stringList.joinToString()} / stringList[3]:${stringList[3]}")
    println("b:${b.joinToString()}")
    //Pair创建
    val pair = "name" to "dididi"
    val pair2 = Pair("name","dididi")
    //获取第一个第二个元素
    val first = pair.first
    val second = pair.second
    //解构表达式
    val (x,y) = pair
    println("x:$x y:$y")
    //Triple创建
    val triple = Triple(1,2,3)
    //获取第一个第二个第三个元素
    val firstT = triple.first
    val secondT = triple.second
    val thirdT = triple.third
    //解构表达式
    val (c,d,e) = triple
    println("c:$c d:$d e:$e")
    println("问题1：集合遍历-0-")
    a.forEach {
        println(it)
    }
    for (i in a.indices){
        println(i)
    }
    mapA.forEach {
        println("key:${it.key} value:${it.value}")
    }
    for (entry in mapA) {
        println("key:${entry.key} value:${entry.value}")
    }
    println("问题2：集合包含关系")
    if (1 in b){
        println("1 in list a")
    }
    if ("hello" in mapA.keys){
        println(mapA["hello"])
    }
    println("""
        问题3：
        因为kotlin为了方便多个平台的使用一致性，
        可以借用typealias关键字来实现不同语言下的集合，
        开发者使用kotlin却只要用同一名称调用集合框架，降低开发难度
    """.trimIndent())
}