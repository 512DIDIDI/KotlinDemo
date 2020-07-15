package com.dididi.kotlindemo.generic


/**
 * @author dididi(叶超)
 * @email 512dididi@gmail.com
 * @since 15/07/2020
 * @describe 泛型基本概念
 * 面向对象是对象层面的抽象，
 * 泛型可以理解为类型层面的抽象
 */

/**函数声明泛型 <>内为类型形参*/
fun <T> maxOfG(a: T, b: T): T {
    return a
}

/**类声明泛型 <>内为类型形参*/
sealed class ListG<T>{
    //注意，Cons<>内也声明了一个泛型形参 ，继承ListG，并调用了ListG的构造函数，传入了E的实参
    //一般情况下，用T表示type泛型 用E表示element泛型
    data class Cons<E>(val head:E,val tail:E):ListG<E>()
}

fun main(){
    //如何使用，<>内传入实参，实参参数类型与()参数类型一致，
    val max = maxOfG<String>("hello","world")

    val list = ListG.Cons<Double>(1.0,2.0)
}

