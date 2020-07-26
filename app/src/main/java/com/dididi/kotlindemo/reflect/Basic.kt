package com.dididi.kotlindemo.reflect

import kotlin.reflect.typeOf


/**
 * @author dididi(yechao)
 * @since 17/01/2019
 * @describe
 * 反射基本概念：允许程序再运行时访问程序结构(类，接口，方法，属性等语法)的一类特性
 * kotlin反射分为四大类：
 *  1.KClass(访问对象实际类型，不包含泛型参数，如Map)
 *  2.KType(描述为进行泛型擦除的类型或泛型参数，例如Map<String,Int>)
 *  3.KFunction(描述函数)
 *  4.KProperty(描述属性)
 * 常见用途：
 *  1.列出类型的属性，方法，内部类等
 *  2.调用给定名称及签名的方法或访问指定名称的属性
 *  3.通过签名信息获取泛型实参的具体类型
 *  4.访问运行时注解及其信息完成注入或者配置操作
 * kotlin反射对比java反射优缺点：
 *  优点：kotlin反射支持访问kotlin几乎所有特性(除了拓展函数，拓展属性【因为其实现是静态方法】)，API设计更友好，而java无法访问kotlin语法特性
 *  缺点：kotlin反射需要引入反射库(2.5mb 编译后400kb)且首次调用需要反序列化，调用慢，而java正好相反。
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

object B{
    fun hello(){
        println("B.hello")
    }
}

@ExperimentalStdlibApi
fun main() {
    val container = Container(mutableListOf(1, 3, 2, 4, 5, 6, 7))
    /**获取KClass引用**/
    val kClass = container::class
    /**获取java Class引用*/
    val jClass = container.javaClass
    /**KClass与java Class转换*/
    val jCls = kClass.java
    val kCls = jCls.kotlin
    /**获取类型参数信息，即泛型信息*/
    val typeParameters = kClass.typeParameters
    val kTypeParameters = typeParameters[0]
    /**是否具体化(泛型)*/
    println(kTypeParameters.isReified)
    /**类型参数名字*/
    println(kTypeParameters.name)
    /**类型参数上界(即:后的内容)*/
    println(kTypeParameters.upperBounds)
    /**获取泛型型变*/
    println(kTypeParameters.variance)
    /**从构造函数获取入参信息*/
    val constructors = kClass.constructors
    for (KFunction in constructors){
        KFunction.parameters.forEach {
            val name = it.name
            val type = it.type
            println("name = $name , type = $type")
            /**打印泛型信息，KTypeProjection，有两个变量(是否泛型型变，泛型参数)*/
            type.arguments.forEach {
                println(it)
            }
        }
    }
    /**可通过typeOf来获取KType，能够保留泛型信息，不被泛型擦除*/
    val map = typeOf<Map<String,Int>>()
    map.arguments.forEach {
        println(it)
    }
    /**通过objectInstance来获取object实例*/
    B::class.objectInstance?.hello()
}
