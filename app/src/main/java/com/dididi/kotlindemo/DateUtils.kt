package com.dididi.kotlindemo

import java.util.Calendar

/**
 * @author dididi(yechao)
 * @since 14/01/2019
 * @describe
 */

fun MyDate.nextDay() = addTimeIntervals(TimeInterval.DAY, 1)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

enum class Day(val value:String){
    FIRST("1"),SECOND("2");
}

fun MyDate.addTimeIntervals(timeInterval: TimeInterval, number: Int): MyDate {
    val c = Calendar.getInstance()
    c.set(year, month, dayOfMonth)
    when (timeInterval) {
        TimeInterval.DAY -> c.add(Calendar.DAY_OF_MONTH, number)
        TimeInterval.WEEK -> c.add(Calendar.WEEK_OF_MONTH, number)
        TimeInterval.YEAR -> c.add(Calendar.YEAR, number)
    }
    return MyDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE))
}

fun main(){
    val number = "2"
    when(number){
        Day.FIRST.value -> {
            println("first")
        }
        Day.SECOND.value -> {
            println("second")
        }
        else -> {
            println("no match")
        }
    }
}