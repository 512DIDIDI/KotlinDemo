package com.dididi.kotlindemo.generic


/**
 * @author dididi(叶超)
 * @email 512dididi@gmail.com
 * @since 16/07/2020
 * @describe 泛型约束
 * 相当于限制泛型为某一个或某些类型的子类。
 */

/**为泛型添加类型约束，限制其为某一个类型的子类，在调用泛型时，就能使用到其父类的特性*/
fun <T : Comparable<T>> maxOf(a: T, b: T): T {
    //在此例中，T实现了Comparable接口，所以a b能够比较大小，当然，传入的a b 也必须是实现Comparable接口的实例
    return if (a > b) a else b
}

/**
 * 添加多个泛型约束，使用 [where] 关键字
 * 传入的a b必须是Comparable的实现类，同时也是()->Unit的lambda函数
 */
fun <T> callMax(a: T, b: T) where T : Comparable<T>, T : () -> Int {
    if (a > b) a() else b()
}

/**
 * 添加多个泛型参数
 */
fun <T, R> callMax(a: T, b: T): R where T : Comparable<T>, T : () -> R, R : Number {
    return if (a > b) a() else b()
}