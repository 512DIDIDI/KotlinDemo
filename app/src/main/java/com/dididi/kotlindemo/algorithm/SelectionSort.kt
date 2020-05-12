package com.dididi.kotlindemo.algorithm


/**
 * @author dididi(yechao)
 * @since 05/03/2019
 * @describe 时间复杂度 n²
 */

fun main(){
    val intArray = intArrayOf(2,1,4,5,3,9,6,1)
    selectionSort(intArray)
    intArray.forEach {
        println(it)
    }
}

fun selectionSort(intArray:IntArray){
    for (i in 0 until intArray.size){
        for (j in i until intArray.size){
            //比较i与之后位置的大小，i之前的数组是排好序的
            if (intArray[i]>intArray[j]){
                val temp = intArray[i]
                intArray[i] = intArray[j]
                intArray[j] = temp
            }
        }
    }
}