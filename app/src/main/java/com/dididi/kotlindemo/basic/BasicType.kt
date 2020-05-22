package com.dididi.kotlindemo.basic


/**
 * @author dididi(yechao)
 * @since 20/05/2020
 * @describe 基本类型
 */

fun main() {
    //var 可读写变量 set get方法都有
    val a: Int = 2
    //val 可读变量 仅有get方法 类似java final关键字
    val b: String = "hello"
    //类型推导，可省去类型定义
    val c = 2
    //Long类型，结尾带L
    val d = 1000L
    //double类型 直接用小数点
    val e = 1.0
    //float类型 结尾加f或F
    val f = 1f
    //数值类型转换 .toXXX()
    val g: Long = c.toLong()
    //无符号类型 类型前面加U 值末尾加U
    val h: UInt = 100U
    //字符串模板 用 ${XXX} 代替 java 的 +
    val i = "hello world$e"
    val j = "hello"
    //===判断两个变量的引用是否相等 是否指向同一个对象 (在基本类型下 就等价于==)
    val k = b === j
    //==判断两个字符串的值是否相等  类似equal()
    val l = b == j
    //raw string 保留原始的string
    val m = """
        fun main{
            println("hello world")
        }
    """.trimIndent()
    println(k)
    println(l)
}

