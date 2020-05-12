package com.dididi.kotlindemo.reflect


/**
 * @author dididi(yechao)
 * @since 17/01/2019
 * @describe
 */

open class BaseContainer<T>

class Container<T : Comparable<T>>(var elements: MutableList<T>) : BaseContainer<Int>() {

    fun sort(): Container<T> {
        elements.sort()
        return this
    }

    override fun toString(): String {
        return "Container(elements = $elements)"
    }
}

fun isOdd(num: Int) = num % 2 != 0
var one = 1

fun main() {
    val container = Container(mutableListOf(1, 3, 2, 4, 5, 6, 7))
    //获取KClass引用
    val kClass = container::class
    //获取java Class引用
    val jClass = container.javaClass
    //获取类型参数信息，即泛型信息
    val typeParameters = kClass.typeParameters
    val kTypeParameters = typeParameters[0]
    //是否具体化(泛型)
    println(kTypeParameters.isReified)
    //类型参数名字
    println(kTypeParameters.name)
    //类型参数上界(即:后的内容)
    println(kTypeParameters.upperBounds)
    println(kTypeParameters.variance)
    //从构造函数获取入参信息
    val constructors = kClass.constructors
    for (KFunction in constructors){
        KFunction.parameters.forEach {
            val name = it.name
            val type = it.type
            println("name = $name , type = $type")
            for (KTypeProjection in type.arguments){
                println(KTypeProjection.type)
            }
        }
    }
//    val nums = listOf(1, 3, 5, 4)
//    //通过::操作符将isOdd函数作为参数引用
//    println(nums.filter(::isOdd))
//    //属性引用
//    println(::one.get())
//    ::one.set(2)
//    println(one)
//    val digitRegex = "\\d+".toRegex()
//    digitRegex.matches("7")
//    digitRegex.matches("6")
//    digitRegex.matches("5")
//    digitRegex.matches("x")
//    //可以通过反射简化代码 函数引用
//    val isDigit = digitRegex::matches
//    isDigit("7")
//    isDigit("6")
//    isDigit("5")
//    isDigit("x")

}
