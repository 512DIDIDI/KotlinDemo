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
     * 抽象方法 子类必须复写
     */
    abstract fun abstractFunc()

    /**
     * 可被子类复写的方法
     */
    open fun openFunc1() {
        println("foo open func1")
    }

    open fun openFunc2() {
        println("foo open func2")
    }

    /**
     * 不可被子类复写的方法，类似java中的final
     */
    fun nonOverrideFunc() {}
}

/**
 * 接口类
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
 * 子类继承抽象类 接口类统一用 : 以 , 分隔开
 * 默认是final类型的类，如果需要被继承，需要在class前加open关键字
 * 如果需要在类内访问构造器内的参数，需要加上var/val关键字 等同于构造方法内 this.x = x
 */
open class Child(var x: Int, val y: String) : Foo(), Interface {

    /**
     * 主构造器中的参数与类内变量性质一样，只是写在构造器中的参数需要在实例化时传进来
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

    override val getProperty: String
        get() = TODO("Not yet implemented")
    override var allProperty: Int
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun interFunc1() {
        TODO("Not yet implemented")
    }

    override fun interFunc2() {
        super.interFunc2()
        println("impl inter func2")
    }
}


class Son(x: Int, y: String) : Child(x, y) {

}



