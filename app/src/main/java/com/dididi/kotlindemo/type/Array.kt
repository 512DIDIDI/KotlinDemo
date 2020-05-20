package com.dididi.kotlindemo.type


/**
 * @author dididi(yechao)
 * @since 20/05/2020
 * @describe 数组
 * IntArray int[]
 * Array<Int> Integer[]装箱类型
 * CharArray char[]
 * Array<Char> Character[]
 * Array<String> String[]
 */

fun main() {
    val a = intArrayOf(1, 2, 3, 4, 5)
    //size + lambda表达式 it指代数组下标
    val b = IntArray(5) { it + 1 }
    //.contentToString 打印数组内的元素.toString
    println(b.contentToString())
    //集合的长度都是用size
    val c = a.size
    val d = arrayOf("hello","world")
    //数组写入
    d[1] = "kotlin"
    //数组读取
    println("d[0]${d[0]},d[1]${d[1]}")
    //数组遍历
    for (i in a) {
        println(i)
    }
    //forEach遍历
    a.forEach {
        println(it)
    }
    //查找数组内是否有该元素
    if (1 in a){
        println("1 in array a")
    }
    //查找数组内是否没有该元素
    if (6 !in a){
        println("6 not in array a")
    }
    //离散区间
    //闭区间创建[1,10]
    val intRange = 1..10
    val charRange = 'a'..'z'
    val longRange = 1L..10L
    //开区间创建[1,10)
    val intRangeExclusive = 1 until 10
    val charRangeExclusive = 'a' until 'z'
    val longRangeExclusive = 1L until 10L
    //倒序区间[10,1]
    val intRangeReverse = 10 downTo 1
    val charRangeReverse = 'z' downTo 'a'
    val longRangeReverse = 10L downTo 1L
    //步长 1,3,5,7,9
    val intRangeWithStep = 1..10 step 2
    val charRangeWithStep = 'a'..'z' step 2
    val longRangeWithStep = 1L..10L step 2
    //连续区间(不能设置步长 不能遍历 可以做元素是否在区间内查询)
    val floatRange = 1f..2f
    val doubleRange = 1.0..2.0
    //遍历打印元素的string
    println(intRange.joinToString())
    //查询有无元素在区间内
    if (1 in intRange){
        println("1 in int range")
    }
    if (11 !in intRange){
        println("11 not in int range")
    }
    //java for i++的遍历
    for (i in 0 until a.size){
        println(i)
    }
    //i++遍历的语法糖，实际上就是用IntRange区间实现
    for (i in a.indices){
        println(i)
    }
}
