package com.dididi.kotlindemo


/**
 * @author dididi(yechao)
 * @since 15/01/2019
 * @describe
 */

interface Base {
    val message: String
    fun print()
    fun printOverride()
}

class BaseImpl(val x: Int) : Base {

    override val message: String = "BaseImpl$x"

    override fun print() {
        println("BaseImpl.print()$message")
    }

    override fun printOverride() {
        println("BaseImpl.printOverride()$message")
    }
}

//委托b类来完成没有覆盖(override)自己的函数(print)
class Derived(b: Base) : Base by b {

    //但委托类访问不到被覆盖的属性，所以会使用委托类自己覆盖的属性，而非Derived类的message
    override val message: String
        get() = "Derived"

    override fun printOverride() {
        println("Derived.printOverride()$message")
    }
}

fun main() {
    val b = BaseImpl(3)
    val d = Derived(b)
    d.print()
    d.printOverride()
}