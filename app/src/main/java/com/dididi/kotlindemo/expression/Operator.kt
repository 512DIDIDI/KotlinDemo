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
    val complexA = Complex(2.0, 2.0)
    val complexB = Complex(1.0, 1.0)
    //minus的重载
    println((complexA - complexB).toString())
    //plus的重载
    println((complexA + complexB).toString())
    //get的重载
    println(complexA[0])
    //中缀表达式
    2 to 3
    2.to(3)
    println(2 add 3)
}

//函数引用
val func = fun() {}

class Complex(var real: Double, var image: Double) {
    override fun toString(): String {
        return "$real + $image"
    }
}

operator fun Complex.plus(other: Complex) =
    Complex(this.real + other.real, this.image + other.image)

operator fun Complex.minus(other: Complex) =
    Complex(this.real - other.real, this.image - other.image)

operator fun Complex.get(index: Int) = when (index) {
    0 -> this.real
    1 -> this.image
    else -> throw IndexOutOfBoundsException()
}

//使用infix关键字(并且只有一个参数)来指明函数是中缀表达式
infix fun <A, B> A.to(that: B): Pair<A, B> = Pair(this, that)

//中缀表达式写法:
//1. infix修饰    2. 仅有一个传参
infix fun Int.add(other: Int) = this + other


