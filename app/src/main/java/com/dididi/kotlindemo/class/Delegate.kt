package com.dididi.kotlindemo.`class`

import kotlin.properties.Delegates
import kotlin.reflect.KProperty


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 30/06/2020
 * @describe 代理：我代替你处理它
 * 1.接口代理：对象x代替当前类A实现接口B的方法 形如 class B(x:A):A by x
 * 2.属性代理：对象x代替属性a实现get/set方法 形如 val a by x{}
 */


interface Api {
    fun a()
    fun b()
    fun c()
}

class ApiImpl : Api {
    override fun a() {
        TODO("Not yet implemented")
    }

    override fun b() {
        TODO("Not yet implemented")
    }

    override fun c() {
        TODO("Not yet implemented")
    }
}

class ApiWrapper(private val api: Api) : Api {
    override fun a() {
        api.a()
    }

    override fun b() {
        api.b()
    }

    /**
     * 虽然我们只扩展了c方法，a/b方法，都是直接用api的实例调用，
     * 但由于需要实现[Api]接口，我们三个方法都必须得实现，
     * 正是由于此原因，接口代理出现了
     */
    override fun c() {
        println("hello")
        api.c()
    }
}

/**
 * 使用接口代理，形如 class B(x:A):A by x{}
 * 只需实现自己需要的方法，剩余接口A的方法，都交由x来实现，
 * 也就是：x代替B处理了A的方法实现
 * 在此例中： api 代替了 [ApiDelegate] 实现了 [Api] 接口的方法
 */
class ApiDelegate(private val api: Api) : Api by api {

    override fun c() {
        println("hello")
        api.c()
    }
}

fun main() {
    secondName = "123"
    println(secondName)
    thirdName = "456"
    println(thirdName)
    observable = 1
    println(observable)
    vetoable = 3
    println(vetoable)
    vetoable = 2
    println(vetoable)
}

//属性代理
//by lazy 利用了 Lazy类 代替了firstName这个对象 实现了getValue方法
//并且当对象第一个调用getValue方法时，才会去调用lambda表达式，才能得到firstName的值
val firstName by lazy {
    "hello"
}
var secondName: String? = null
    set(value) {
        println("hello")
        field = value
    }
    get() {
        println("world")
        return field
    }

var thirdName: String? by PropertyX()

//by Delegates.observable 利用了 ReadWriteProperty 类 代替了 observable对象 实现了set/get方法
//Delegates.observable是在setValue中调用，且是在值改变时，才能调用
var observable: Int by Delegates.observable(0) { property, oldValue, newValue ->
    println("old:$oldValue | new:$newValue")
}

//利用了 ReadWriteProperty类 代替了 vetoable对象 实现了set/get方法
//Delegates.vetoable是判断 setValue 是否能够执行下去的前提语句
//如果lambda表达式返回值为false，则值不改变，如果为true，则值更改
var vetoable: Int by Delegates.vetoable(1) { property, oldValue, newValue ->
    oldValue.toFloat() == newValue / 2.toFloat()
}

/**
 * 自定义属性 set/get代理
 */
class PropertyX {
    private var value: String? = null
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
        println("hello")
        this.value = value
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String? {
        println("world")
        return this.value
    }
}