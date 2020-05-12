package com.dididi.kotlindemo


/**
 * @author dididi(yechao)
 * @since 14/01/2019
 * @describe
 */

class InRange{}

//拓展函数(..操作符重载)
operator fun MyDate.rangeTo(other:MyDate) = DateRange(this,other)

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>,Iterator<MyDate> {

    var current = start

    //in操作符重载
    override fun contains(value: MyDate): Boolean = value >= start && value <= endInclusive

    override fun hasNext() = current <= endInclusive

    override fun next():MyDate{
        val result = current
        current = current.nextDay()
        return result
    }
}

fun checkInRange(date: MyDate, first: MyDate, last: MyDate): Boolean {
    return date in DateRange(first, last)
}

fun checkInRange(date:MyDate,dateRange: DateRange):Boolean{
    return date in dateRange
}

fun iterateOverDateRange(firstDate: MyDate, secondDate: MyDate, handler: (MyDate) -> Unit) {
    for (date in firstDate..secondDate) {
        handler(date)
    }
}

private val date1 = MyDate(2019, 1, 2)
private val startDate = MyDate(2019, 1, 1)
private val endDate = MyDate(2019, 1, 14)

fun main() {
    println(checkInRange(date1, startDate, endDate))
    //..操作符生成DateRange区间
    println(checkInRange(date1, startDate..endDate))
    iterateOverDateRange(startDate, date1) {
        println("${it.dayOfMonth}")
    }
}