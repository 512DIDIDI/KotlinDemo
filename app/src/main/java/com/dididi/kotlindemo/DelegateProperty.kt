package com.dididi.kotlindemo

import kotlin.properties.Delegates


/**
 * @author dididi(yechao)
 * @since 15/01/2019
 * @describe
 */

class DelegateProperty {
    //by lazy用于延迟属性，他只会执行一次并记录执行后的结果，后续调用只会返回该结果
    val lazyProperty: String by lazy {
        println("hello")
        "world"
    }
    //Delegates.observable观察者模式，提供了一个lambda函数，用于当值发生变化时调用
    //Delegates.vetoable()则用于筛选值，可以否决不满足条件的赋值
    var name: String by Delegates.observable("init") { property, oldValue, newValue ->
        println("$oldValue -> $newValue")
    }
}

fun main() {
    val world = DelegateProperty().lazyProperty
    println(world)
    println(world)
}