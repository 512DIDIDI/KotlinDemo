package com.dididi.kotlindemo.`class`


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 29/06/2020
 * @describe 延迟初始化
 */


/**
 * 通过创建可空类型
 */
private var name: String? = null

/**
 * 使用 [lateinit var] 来延迟初始化，需要注意以下几点：
 * 1.不支持基本类型
 * 2.必须在明确变量值的生命周期下使用
 * 3.不要在复杂逻辑中使用
 * 4.kotlin 1.2中加入的lateinit是否初始化api最好不要用，可以直接使用? 可空类型
 */
private lateinit var fly: Any

/**
 * 通过 [by lazy] 属性代理延迟初始化(最优解)
 * 只有在第一次被访问时执行 初始化属性
 * 同时把 声明与初始化 内聚在了一块 利于阅读
 */
private val textView by lazy {
    2
}
