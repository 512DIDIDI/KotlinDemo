package com.dididi.kotlindemo

import java.lang.Exception
import java.lang.NullPointerException


/**
 * @author dididi(yechao)
 * @since 01/04/2020
 * @describe 密封类
 */

sealed class Result<out T : Any>
data class Success<out T : Any>(val data: T) : Result<T>()
data class Error(val exception: Exception) : Result<Nothing>()
object InProgress : Result<Nothing>()


fun doAction(result: Result<Int>) {
    //在when的情况下 ide可自动覆盖所有result的情况，不再需要使用else
    val action = when (result) {
        is Success -> println(result.data)
        is Error -> println(result.exception)
        InProgress -> println("progress")
    }
}

fun main() {
    doAction(Success(2))
    doAction(Error(NullPointerException()))
    doAction(InProgress)
}