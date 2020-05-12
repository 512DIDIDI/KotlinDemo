package com.dididi.kotlindemo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


/**
 * @author dididi(yechao)
 * @since 16/01/2019
 * @describe 使用lambda函数返回时，需要注意返回的方法体
 */

/**
 * inline内联函数 由于lambda表达式作为传参对象，内存分配（对于函数对象和类）和虚拟调用会引入运行时间开销
 * 可使用内联函数在编译时将lambda函数插入到调用处，这样在实际运行中与普通函数无异，可减少开销。(适用于以lambda表达式作为参数的函数)
 * 同时inline内联函数还可非局部返回return 可以直接结束函数体
 * noinline则相反 因为inline内联函数会将lambda表达式也会以内联函数定义，而noinline可赋予其lambda表达式不内敛属性，从而可以存储在字段中、传送它
 * crossinline 与inline内联函数相似，不过其禁用了非局部返回return
 */
inline fun lambdaFunction(initial: Int, build: (a: Int, b: Int) -> Int): Int {
    return initial + build(initial * 2, initial)
}

/**
 * lambda表达式作为参数参与模板计算，传入的lambda表达式可自由定制内容
 */
fun setCheckStringNull(checkStringNull: (String) -> Int, plus: (Int, Int) -> Int) =
    checkStringNull("123") + plus(1, 2)

fun main() {
//    //在lambda函数前加标签可指定返回
//    println(lambdaFunction(1) lable@{ a, b ->
//        a + b
//        //可通过return@lambda函数名指定结束lambda函数，否则lambda函数只是作为参数，return返回的是最外层函数
//        //return@lable
//    })
//    println(setCheckStringNull(checkStringNull) { a, b ->
//        a + b
//    })

//    println(2.deduceInt(1))
//    println(deduceInt(3, 1))
//    "123".onClick("2")
//    b({ a ->
//        a.toInt()
//    }) { a, b ->
//        a + b
//    }
}


/**
 * 匿名类函数对象
 */
val checkStringNull = fun(s: String) = s.toIntOrNull() ?: 0

/**
 * lambda表达式函数对象
 */
val plusInt = { a: Int, b: Int -> a + b }

/**
 * 双冒号加函数名函数对象
 */
val b = ::setCheckStringNull

/**
 * 匿名类扩展函数对象 可以通过 deduceInt(Int,Int) 或者 Int.deduceInt(Int)(不能通过ctrl+p来查找传参)来调用
 */
val deduceInt = fun Int.(other: Int) = this - other

/**
 * 普通扩展函数 仅能通过String.onClick(String)调用
 */
fun String.onClick(s: String) {}


