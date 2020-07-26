package com.dididi.kotlindemo.reflect

import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor


/**
 * @author dididi(叶超)
 * @email 512dididi@gmail.com
 * @since 26/07/2020
 * @describe 为数据类实现深拷贝
 */

fun <T : Any> T.deepCopy(): T {
    /**只拷贝数据类，如果不是数据类，直接返回*/
    if (!this::class.isData) {
        return this
    }

    /**如果是数据类，调用数据类主构造器*/
    return this::class.primaryConstructor!!.let { primaryConstructor ->
        /**获取parameter与property的映射map*/
        primaryConstructor.parameters.map { parameter ->
            val value =
                /**需要把协变的[KClass<out T>]转为[KClass<T>],以便能get到*/
                this::class.safeAs<KClass<T>>()!!
                        /**获取[parameter]对应的property*/
                    .memberProperties.first { it.name == parameter.name }
                    .get(this)!!
            /**递归调用[deepCopy]，拷贝数据类内部的数据类对象*/
            parameter to value.deepCopy()
        }.toMap()
                /**直接把map传入，返回数据类实例*/
            .let(primaryConstructor::callBy)
    }
}


data class Person(val id: Int, val name: String, val group: Group)

data class Group(val nation: String, val location: String)

fun main() {
    val dididi = Person(
        1, "dididi",
        Group(
            "china",
            "hangzhou"
        )
    )
    val dididi2 = dididi.copy()
    val dididi3 = dididi.deepCopy()
    /**===比较的是引用  ==比较的是值*/
    println(dididi === dididi2)
    println(dididi === dididi3)
    /**数据类默认的copy实现的是浅拷贝，也就是数据类内部的数据，只拷贝其引用，所以这里group相等*/
    println(dididi.group === dididi2.group)
    /**深拷贝，会创建新的group对象，所以这里引用不相等*/
    println(dididi.group === dididi3.group)
    println(dididi3)
}