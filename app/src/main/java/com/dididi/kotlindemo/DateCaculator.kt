package com.dididi.kotlindemo

import java.text.SimpleDateFormat
import java.util.*


/**
 * @author dididi(yechao)
 * @since 14/01/2019
 * @describe
 */

//+操作符重载，加1
operator fun MyDate.plus(timeInterval: TimeInterval): MyDate =
    this.addTimeIntervals(timeInterval, 1)

//*操作符重载 返回TimesTimeInterval
operator fun TimeInterval.times(number: Int) = TimesTimeInterval(this, number)

//+操作符重载，+倍数
operator fun MyDate.plus(timesTimeInterval: TimesTimeInterval) =
    this.addTimeIntervals(timesTimeInterval.timeInterval, timesTimeInterval.number)

class TimesTimeInterval(val timeInterval: TimeInterval, val number: Int)

fun task1(today: MyDate): MyDate {
    return today + TimeInterval.YEAR + TimeInterval.WEEK
}

fun task2(today: MyDate): MyDate {
    return today + TimeInterval.YEAR * 2 + TimeInterval.WEEK * 3 + TimeInterval.DAY * 15
}

val today = MyDate(2019, 1, 14)

fun main() {
//    println("${task2(today).dayOfMonth}")
    long2Date( 1585291477L)
}

private fun long2Date(date: Long) {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
    println(sdf.format(Date(date * 1000L)))
}