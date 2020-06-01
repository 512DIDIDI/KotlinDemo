package com.dididi.kotlindemo.function


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 01/06/2020
 * @describe 高阶函数
 * 定义：参数类型包含函数类型或者返回值类型为函数类型的函数叫高阶函数
 */

/**
 * 参数类型包含函数类型
 */
fun lambdaParam(block: (Int) -> Unit) {
    block(2)
}

/**
 * 返回值为函数类型
 */
fun lambdaReturn(): (Int) -> Int {
    return {
        it * 2
    }
}

fun useLambda() {
    //高阶函数的调用
    //1.通过函数引用传递参数
    lambdaParam(::println)
    //2.将lambda表达式作为参数传递 接收函数类型为(Int)->Unit(匿名函数)
    lambdaParam({ int ->
        println(int)
    })
    //简化写法 当lambda表达式仅有一个参数时，默认可以用it指代这个参数
    lambdaParam({
        println(it)
    })
    //再简化写法 当参数的最后一项为函数类型时，可写在()外面
    lambdaParam() {
        println(it)
    }
    //最终简化写法 当参数只有一个且为函数类型时，可省略() 直接写lambda表达式
    lambdaParam {
        println(it)
    }
    //函数类型作为返回值的调用
    //方式1 获取函数引用，通过invoke方法调用
    val value = lambdaReturn()
    println(value(2))
    //方式2 直接通过返回值.invoke方法调用
    println(lambdaReturn()(3))
    //region 可以使用region-endregion来显式折叠内容
    println("折叠测试")
    //endregion
}

/**
 * 高阶函数实战 打印fibonacci
 */
fun main() {
    cost {
        //获取实例化后的函数对象
        val fibonacci = fibonacci()
        for (i in 0..10) {
            //注意 一定要使用invoke()方法，否则得到的是函数引用的类型
            println(fibonacci())
        }
    }
}

//函数类型作为参数
fun cost(block: () -> Unit) {
    val start = System.currentTimeMillis()
    //参数通过invoke方法调用
    block()
    println("cost:${System.currentTimeMillis() - start}ms")
}

//返回函数类型
fun fibonacci(): () -> Long {
    var first = 0L
    var second = 1L
    //返回lambda表达式
    return {
        val next = first + second
        val current = first
        first = second
        second = next
        //lambda表达式最后一行为其返回值
        current
    }
}

