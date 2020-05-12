package com.dididi.kotlindemo


/**
 * @author dididi(yechao)
 * @since 14/01/2019
 * @describe
 */

class DestructuringDeclarations

fun isLeapDay(date: MyDate): Boolean {

    //数据类自动声明 componentN(),可以使用解构声明
    val (year, month, dayOfMonth) = date

    // 29 February of a leap year
    return year % 4 == 0 && month == 2 && dayOfMonth == 29
}