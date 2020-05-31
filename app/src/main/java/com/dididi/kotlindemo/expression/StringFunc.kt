package com.dididi.kotlindemo.expression


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 31/05/2020
 * @describe String的四则运算
 */

//-实现
operator fun String.minus(right: Any?) = this.replaceFirst(right.toString(), "")

/**
 * 乘法实现
 * [joinToString]实际是迭代器中的元素遍历添加到string 这里把transform函数改为了this，则可以实现重复
 */
operator fun String.times(right: Int) = (1..right).joinToString("") { this }

/**
 * 滑动窗口实现除法
 * 滑动窗口来判断right.toString有多少个匹配，返回List<Boolean>
 */
operator fun String.div(right: Any?): Int {
    return this.windowed(right.toString().length, 1) {
        it == right.toString()
    }.count { it }
}


fun main() {
    val value = "hello world"

    println(value - "hello")
    println(value * 2)
    println(value / 1)
    println(value / "l")
    println(value / "ld")
}
