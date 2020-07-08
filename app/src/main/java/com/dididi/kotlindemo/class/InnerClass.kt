package com.dididi.kotlindemo.`class`


/**
 * @author dididi(叶超)
 * @email 512dididi@gmail.com
 * @since 08/07/2020
 * @describe 内部类
 */

class InnerClass {

    /**
     * 内部类，相当于java的内部类，会持有外部类实例的引用，InnerClass().Inner
     */
    inner class Inner {

    }

    /**
     * 静态内部类，不会持有外部实例的引用，InnerClass.StaticClass
     */
    class StaticClass {

    }

    object ObjectClass {
        /**
         * 内部object，实际object是静态内部类，所以不存在inner类
         */
        object StaticObjectClass {

        }
    }

    /**
     * 匿名内部类定义在非静态区域中，会持有外部类的引用，会造成内存泄漏
     */
    val runnable = object : Runnable {
        override fun run() {

        }
    }

    companion object{
        /**
         * 匿名内部类定义在静态区域(Companion object或者顶级类object中) 就不会持有外部类的引用
         */
        val runnable = object :Runnable{
            override fun run() {
                TODO("Not yet implemented")
            }
        }
    }

    /**
     * kotlin的匿名内部类可实现多个接口
     */
    val runnableCloneable: Runnable = object : Runnable, Cloneable {
        override fun run() {
            TODO("Not yet implemented")
        }
    }

    fun local(){
        /**
         * 本地类，定义在函数内部的类
         */
        class LocalClass{

        }

        /**
         * 本地函数，定义在函数内部的函数
         */
        fun LocalFunction(){

        }
    }

    fun test(){
        //访问Inner class 会持有外部类实例的引用
        InnerClass().Inner()
        //访问静态类 不会持有外部类的实例引用
        InnerClass.StaticClass()
    }
}