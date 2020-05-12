package com.dididi.kotlindemo


/**
 * @author dididi(yechao)
 * @since 14/01/2019
 * @describe
 */

class OperatorOverloading {}

data class Point(var x: Int, var y: Int)

data class Count(val dayIndex: Int) {
    //操作符重载(Count+increment)
    operator fun plus(increment: Int): Count {
        return Count(dayIndex + increment)
    }
}

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {

    override operator fun compareTo(other: MyDate) = when {
        this.year != other.year -> this.year - other.year
        this.month != other.month -> this.month - other.month
        else -> this.dayOfMonth - other.dayOfMonth
    }

}

fun compare(date1: MyDate, date2: MyDate) = date1 < date2

//操作符重载(-Point)
operator fun Point.unaryMinus() = Point(-x, -y)

private val point = Point(10, 20)
private val count = Count(31)
private val date1 = MyDate(2018, 12, 31)
private val date2 = MyDate(2019, 1, 14)

fun main() {
    //输出为-10,-20
    println("${-point.x},${-point.y}")
    println("${(count + 2).dayIndex}")
    println(date1 > date2)
}