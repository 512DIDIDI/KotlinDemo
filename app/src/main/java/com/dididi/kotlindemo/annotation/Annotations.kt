package com.dididi.kotlindemo.annotation


/**
 * @author dididi(yechao)
 * @since 17/01/2019
 * @describe
 */

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.VALUE_PARAMETER
)
@Retention(AnnotationRetention.RUNTIME)
@Repeatable
@MustBeDocumented
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