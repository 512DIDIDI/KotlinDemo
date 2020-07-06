package com.dididi.kotlindemo.`class`


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 29/06/2020
 * @describe 类与成员的可见性 kotlin默认可见性为 [public]
 */

/**
 * 公开可见
 */
public class PublicClass

/**
 * 仅模块内可见 [module] 内可见
 */
internal class InternalClass{
    /**
     * 可通过 @JvmName() 限定java的调用，也局限在模块内
     */
//    @JvmName("%jk")
    internal fun test(){

    }
}

abstract class AbstractClass{
    /**
     * 类内及子类可见
     */
    protected fun test(){}
}

/**
 * 类或文件内可见
 */
private class PrivateClass

