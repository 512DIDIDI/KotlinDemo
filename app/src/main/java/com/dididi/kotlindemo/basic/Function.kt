package com.dididi.kotlindemo.basic


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 21/05/2020
 * @describe 函数类型
 * 可以赋值，传递引用，调用
 * 区分函数和方法的概念 1.函数包含方法，方法是函数的一种特殊类型 2.有receiver的才叫方法 就是xxx.() 中的xxx
 */

fun main() {
    //函数引用，类似指针 用于函数传递(当重载时编译器会根据语境来分析)
    val f: () -> Unit = ::foo
    val g: (Int) -> String = ::foo
    // h i j的函数类型相同 只是写法不同
    val h: (Foo, Int, String) -> Any = Foo::bar
    val i: Foo.(Int, String) -> Any = Foo::bar
    val j: Function3<Foo, Int, String, Any> = Foo::bar
    //三种写法的函数调用
    h(Foo(), 2, "3")
    Foo().i(2, "3")
    j(Foo(), 2, "3")
    //实例化receiver的函数引用
    val foo = Foo()
    //没有receiver作为参数传递了 因为receiver实例化了 m函数实际上就是关于int与string变量的函数
    //类比 f(r,x,y) = r*(x+y) 令r=2 m(x,y) = f(2,x,y) = 2*(x+y)
    val m: (Int, String) -> Any = foo::bar
    //将函数作为参数传入
    fooWithFun(h)
    val (a, b, c) = multiReturnValues()
    println("a$a,b$b,c$c")
    //d = 1.0是具名参数 当出现默认参数时，可以使用具名参数指定对应的参数值
    defaultParameter(1, "hello", d = 1.0)
}

// () -> Unit
fun foo() {}

// (Int) -> String
fun foo(p0: Int): String {
    return ""
}

//函数作为形参 函数类型为 (Foo, Int, String) -> Any
fun fooWithFun(p: (Foo, Int, String) -> Any) {
    //调用函数，注意 函数形参传进来的只是函数体，实际上是由调用者决定参数的具体值
    println(p(Foo(), 1, "String"))
}

//可变长参数，实际vararg实现是利用array来实现的
fun varargFun(vararg args: String) {
    //kotlin当中的listOf 传的就是可变长参数
    val l = listOf(1, 2, 3)
    //实际就是数组
    args.forEach {
        println(it)
    }
}

//与上述函数等价
fun varargFun1(args: Array<String>) {

}

//多返回值，可以返回多个结果 (Pair或者Triple)
fun multiReturnValues(): Triple<Int, String, Double> = Triple(1, "dididi", 2.0)

//默认参数，在传参中直接赋值，这样可以在调用的时候可以使用默认值
fun defaultParameter(a: Int, b: String, c: Long = 0L, d: Double = 0.0) {}

// Foo.bar(Int,String) -> Any   ==  bar(Foo,Int,String) -> Any
// 这个Foo就是bar方法的receiver
// 可以写作等式2，其实java中隐式将receiver作为this第一个参数传递进去
class Foo {
    fun bar(p0: Int, p1: String): Any {
        println("p0$p0 p1$p1")
        return 1
    }
}