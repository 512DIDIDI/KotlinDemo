package com.dididi.kotlindemo.`class`


/**
 * @author dididi(yechao)
 * @since 22/05/2020
 * @describe 类与接口
 */

/**
 * 抽象类 子类继承必须复写抽象方法
 */
abstract class Foo() {
    /**
     * 抽象方法 子类必须复写 用abstract修饰
     */
    abstract fun abstractFunc()

    /**
     * 副构造器必须调用主构造器
     */
    constructor(num: Int) : this() {

    }

    /**
     * 可被子类复写的方法 用open修饰
     * 可以理解为 1.有个别子类需要复写该方法 2.或者在调用父类方法的基础上增加方法
     */
    open fun openFunc1() {
        println("foo open func1")
    }

    open fun openFunc2() {
        println("foo open func2")
    }

    /**
     * 默认方法不可被子类复写，类似java中的final
     */
    fun nonOverrideFunc() {}
}

/**
 * 接口类 接口是行为的集合 没有状态(没有 backing field)
 */
interface Interface {

    /**
     * 属性，子类需要复写get方法
     */
    val getProperty: String

    /**
     * 子类需要复写set get方法
     */
    var allProperty: Int

    fun interFunc1()

    /**
     * kotlin中的接口方法 父类也可实现
     * 如果父类实现了接口方法 等同于抽象类的open方法
     * 子类可选择是否需要复写
     */
    fun interFunc2() {
        println("Interface func2")
    }
}

/**
 * 子类继承抽象类(继承抽象类需调用其主构造器) 接口类统一用 : 以 , 分隔开
 * 默认是final类型的类，如果需要被继承，需要在class前加open关键字或者abstract
 * 如果需要在类内访问主构造器内的参数，需要加上var/val关键字 等同于构造方法内 this.x = x 相当于定义了类的属性
 */
open class Child(var x: Int, val y: String) : Foo(), Interface {

    /**
     * 主构造器中的参数与类内属性性质一样，只是写在构造器中的参数需要在实例化时传进来
     * var 有set get方法 val 仅有get方法
     */
    var a: Int = 0
    val b: String = "hello"

    /**
     * abstract方法必须复写
     */
    override fun abstractFunc() {
        println("override abstract func")
    }

    /**
     * open方法选择性复写
     */
    override fun openFunc1() {
        super.openFunc1()
        println("override open fuc1")
    }

    /**
     * 如果open方法不想被子类再次复写，可以加上final关键字
     */
    final override fun openFunc2() {
        super.openFunc2()
        println("override open fuc2")
    }

    /**
     * 可以复写接口的属性
     */
    override val getProperty: String
        get() = "2"
    override var allProperty: Int = 0
        get() = field
        set(value) {
            field = value * 2
        }

    override fun interFunc1() {
        println("impl inter func1")
    }

    override fun interFunc2() {
        super.interFunc2()
        println("impl inter func2")
    }
}


class Son(x: Int, y: String) : Child(x, y) {
    override fun abstractFunc() {
        super.abstractFunc()
    }

    /**
     * 可以复写父类的父类的open方法
     */
    override fun openFunc1() {
        super.openFunc1()
    }
}

class Data() {
    //kotlin当中这个叫属性(property)，var声明的属性既有set也有get方法，val属性只有get方法
    //property = backing field(状态) + set + get(行为)
    var age: Int = 0
        //可以理解为java当中的getAge() 这个field属性也就是this.age
        get() = field
        //理解为java中的setAge(value:Int) 这个field属性就是this.age
        set(value) {
            field = value
        }

    var realName = ""

    //no backing-field 没有状态的属性 只有get/set 相当于两个方法
    var name: String
        get() {
            return "dididi"
        }
        set(value) {
            realName = value
        }
}

fun main() {
    val child = Child(1, "dididi")
    child.abstractFunc()
    child.openFunc1()
    child.openFunc2()
    println("x:${child.x}")
    println("y:${child.y}")
    println("a:${child.a}")
    println("b:${child.b}")
    child.x = 5
    child.a = 6
    println("x:${child.x}")
    println("getProperty:${child.getProperty}")
    child.interFunc1()
    child.interFunc2()
    //拿到属性的引用，但这里调用需要一个receiver
    val ageRef = Data::age
    ageRef.set(Data(), 18)
    val data = Data()
    //通过实例化对象拿到属性引用，调用时不需要receiver
    val nameRef = data::name
    nameRef.get()
}



