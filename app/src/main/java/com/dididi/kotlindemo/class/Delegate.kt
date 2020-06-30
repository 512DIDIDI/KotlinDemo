package com.dididi.kotlindemo.`class`


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 30/06/2020
 * @describe 代理：我代替你处理它
 * 1.接口代理：对象x代替当前类A实现接口B的方法 形如 class B(x:A):A by x
 * 2.属性代理：对象x代替属性a实现get/set方法 形如 val a by x{}
 */


interface Api{
    fun a()
    fun b()
    fun c()
}

class ApiImpl:Api{
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

class ApiWrapper(private val api: Api):Api{
    override fun a() {
        api.a()
    }

    override fun b() {
        api.b()
    }

    /**
     * 我们只扩展了c方法，a/b方法，都是直接用api的实例调用，
     * 但由于需要实现[Api]接口，我们三个方法都得实现，
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
class ApiDelegate(private val api: Api):Api by api{

    override fun c() {
        println("hello")
        api.c()
    }
}

class PropertyDelegate{
    val firstName by lazy {
        "hello"
    }
}