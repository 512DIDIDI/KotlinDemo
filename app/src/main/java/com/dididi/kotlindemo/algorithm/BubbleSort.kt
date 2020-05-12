package com.dididi.kotlindemo.algorithm


/**
 * @author dididi(yechao)
 * @since 05/03/2019
 * @describe 时间复杂度 n² 最好情况 n(已经排好序)
 */

fun main() {
    val intArray = intArrayOf(1, 5, 3, 8, 2, 6)
    bubbleSort(intArray)
    intArray.forEach {
        println(it)
    }
}

fun bubbleSort(intArray: IntArray) {
    //外部循环size次
    for (i in 0 until intArray.size) {
        //内部每次从第一个开始，末尾i+1长度的是已经排序完成的
        for (j in 0 until intArray.size - i - 1) {
            //交换邻近值
            if (intArray[j + 1] > intArray[j]) {
                val temp = intArray[j]
                intArray[j] = intArray[j + 1]
                intArray[j + 1] = temp
            }
        }
    }
}