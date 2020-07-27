package com.dididi.kotlindemo.annotation


/**
 * @author dididi(yechao)
 * @since 17/01/2019
 * @describe 注解基本概念
 * 对程序的附加信息说明，可以告诉开发者，或者编译器，或者运行时的信息
 * 可对类，函数，函数参数，属性等做标注
 * 注解的信息可用于
 *  1.源码级(告诉开发者或开发文档 例如 @Deprecated)
 *  2.编译期(告诉编译器的信息 例如 @NoArgs)
 *  3.运行时(例如反射 @Metadata)
 */

/**指定注解作用对象 [AnnotationTarget]*/
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.VALUE_PARAMETER
)
/**指定注解作用实际 作用域(作用域大的可以包含作用域小的)：[AnnotationRetention.SOURCE] < [AnnotationRetention.BINARY] < [AnnotationRetention.RUNTIME] */
@Retention(AnnotationRetention.RUNTIME)
@Repeatable
@MustBeDocumented
/**[id]注解参数 注意：仅支持基本类型 KClass 枚举 其他注解，一定是在编译期能确定的类型，不支持自定义类型*/
annotation class TestCase(val id: String)

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.VALUE_PARAMETER
)
@Retention(AnnotationRetention.RUNTIME)
@Repeatable
@MustBeDocumented
annotation class Run

/**注解使用*/
@Run
fun main(){}
@TestCase("1")
class AnTest()