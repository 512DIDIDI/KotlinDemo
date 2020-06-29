package com.dididi.kotlindemo.`class`


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 29/06/2020
 * @describe 类的构造器
 */

val persons = hashMapOf<String, Person>()

/**
 * 构造同名工厂函数，可以使用同名函数来创建生成类对象，
 * 用途：可在封装的类中，通过同名工厂函数实现自定义初始化
 * kotlin中的 [String] 就用到了同名工厂函数
 */
fun Person(name: String): Person {
    //可实现从缓存中读取类
    return persons[name] ?: Person(22, name).also {
        persons[name] = it
    }
}

/**
 * var/val 修饰的变量相当于成员变量，可在类内部访问
 * 没有使用var/val修饰的变量 仅能在init代码块中访问当
 */
class Person(var age: Int, name: String) {

    /**
     * 副构造器，尽量通过主构造器初始化，this(xx) ，保证初始化通过唯一路径构造
     */
    constructor(age: Int, name: String, sex: String) : this(age, name) {

    }

    /**
     * 成员变量必须初始化
     */
    var initAge = age

    init {
        val initName = name
    }

    /**
     * 可以有多个init代码块，最终编译时会组合在一起
     */
    init {
        var sex = "male"
    }

    fun printlnAge() {
        println("age:$age")
    }
}