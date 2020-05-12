package com.dididi.kotlindemo.algorithm

import java.util.*


/**
 * @author dididi(yechao)
 * @since 06/03/2019
 * @describe 时间复杂度 nlogn 缺点 需要占用额外的内存空间
 */

fun main() {
    val intArray = intArrayOf(1, 8, 4, 2, 6, 8, 1, 3, 9)
    val sortArray = mergeSort(intArray)
    sortArray.forEach { print(it) }
}

fun mergeSort(intArray: IntArray): IntArray {
    //拆分成只有一个数的数组
    if (intArray.size < 2) {
        return intArray
    }
    //拆分成左右两个数组
    val mid = intArray.size / 2
    val leftArray = Arrays.copyOfRange(intArray, 0, mid)
    val rightArray = Arrays.copyOfRange(intArray, mid, intArray.size)
    //递归调用，继续拆分
    return sort(mergeSort(leftArray), mergeSort(rightArray))
}

fun sort(leftArray: IntArray, rightArray: IntArray): IntArray {
    val result = IntArray(leftArray.size + rightArray.size)
    //左侧下标
    var lI = 0
    //右侧下标
    var rI = 0
    for (index in 0 until result.size) {
        result[index] = when {
            //如果左侧数组全部小于右侧数组，则右侧数组直接赋值即可
            lI >= leftArray.size -> rightArray[rI++]
            //右侧数组全部小于左侧数组
            rI >= rightArray.size -> leftArray[lI++]
            //验证两侧数组对应下标的数的大小，将小的数赋值，并右移对应下标
            leftArray[lI] < rightArray[rI] -> leftArray[lI++]
            else -> rightArray[rI++]
        }
    }
    return result
}