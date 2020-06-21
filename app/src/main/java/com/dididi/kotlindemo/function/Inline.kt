package com.dididi.kotlindemo.function

/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 02/06/2020
 * @describe 内联函数
 * 所谓内联(inline)，就是在编译时将内联函数替换到调用处
 * 适用：高阶函数(有函数类型的传参)
 * 优点：1.高阶函数的调用被替换，节省高阶函数调用的开销 2.节省了创建lambda表达式的开销 3.可以在lambda表达式中直接使用return从外部函数返回
 * 缺点：可能会使代码量增加。
 * 过程：两次内联 1.高阶函数内联到调用处 2.lambda表达式内联到高阶函数参数调用处
 * 性能对比：Lambda类中[cost]调用处的时间与本类中[main]方法的时间消耗对比
 * 限制：
 * 1.public/protected的内联方法只能访问对应类的public成员
 * 2.内联函数的函数参数不能赋值给变量(不能存储)
 * 3.内联函数的函数参数只能传递给其他内联函数参数(例如你只能在forEach{ block() } 但不能Runnable{ block() })
 */

fun main() {
    val start = System.currentTimeMillis()
    costInline {
        //获取实例化后的函数对象
        val fibonacci = fibonacci()
        for (i in 0..10) {
            //注意 一定要使用invoke()方法，否则得到的是函数引用的类型
            println(fibonacci())
        }
    }
    //使用了内联函数 这里的打印时间与内联函数内的耗时一样。
    println("main:${System.currentTimeMillis() - start}ms")
    //内联函数的return
    val ints = intArrayOf(0, 1, 2, 3, 4)
    ints.forEach {
        //使用return标签名 可以跳出当次循环 如同java中的continue
        if (it == 3) return@forEach
        println("hello$it")
    }
    hasZeros(ints)
    ordinaryFunction {
        //非内联函数直接return外部函数不被允许
//        return
    }
    nonLocalReturn {
        //内联函数从nonLocalReturn的外部函数return
        return
    }
    //无法打印这句，因为上面nonLocalReturn中的return 从main函数中返回了
    println("nonLocalReturn")
}

/**
 * 高阶函数可以使用内联标记来优化性能
 */
inline fun costInline(block: () -> Unit) {
    val start = System.currentTimeMillis()
    block()
    println("cost:${System.currentTimeMillis() - start}ms")
}

private lateinit var test: () -> Unit

/**
 * 非内联函数不允许使用return从外部函数返回
 */
fun ordinaryFunction(block: () -> Unit) {
    block()
    test = block
}

/**
 * 内联函数可以在lambda表达式中直接使用return从外部函数返回
 */
inline fun nonLocalReturn(block: () -> Unit) {
    block()
    //内联函数的函数参数不能赋值给变量，不能被存储，且public/protected内联函数只能访问public成员
//    test = block
}

fun hasZeros(ints: IntArray): Boolean {
    ints.forEach {
        //return返回的是forEach外部函数hasZeros
        if (it == 0) return true
    }
    return false
}

/**
 * 注意这种情况，当block()传参在另一个非内联函数内，需要使用 [crossinline] 禁止其局部返回
 */
inline fun banNonLocalReturn(crossinline block: () -> Unit) {
    Runnable { block() }
}

/**
 * 当使用多个函数传参的内联函数，但需要禁止某些函数内联，使用 [noinline] 关键字来禁止函数内联
 */
inline fun noInlineFunction(block: () -> Unit, noinline body: () -> Unit) {
    block()
    body()
}

var pocket: Double = 0.0
var money: Double
    //内联属性
    inline get() = pocket
    inline set(value) {
        pocket = value
    }