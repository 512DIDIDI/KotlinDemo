package com.dididi.kotlindemo


/**
 * @author dididi(yechao)
 * @since 11/01/2019
 * @describe
 */

class ExtensionsFunction {
    //拓展函数用于该类内部使用
    fun Person.swap() {
        val temp = this.height
        this.height = this.weight
        this.weight = temp
    }

    fun test() {
        val person = Person()
        person.swap()
    }


}

data class Person(var name: String? = "", var height: Float? = 0f, var weight: Float? = 0f)

open class C

class D : C() {
    fun foo() = "D:C"
}

//拓展方法并不真正修改类，所以在这里并不是输出 D.foo() 而是 C.foo()
fun C.foo() = "C"

fun D.foo() = "D"

fun D.foo(i: Int) = "D$i"

fun printlnFoo(c: C) {
    println(c.foo())
}

open class BaseT<T>

class ImpT : BaseT<C>()

fun main() {
    printlnFoo(D())
    val d = D()
    //拓展方法重名时 使用的函数是该类的成员函数
    println(d.foo())
    //可接受重载
    println(d.foo(1))
    val pair = Pair(1,2)
    val i = ImpT()
}