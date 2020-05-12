package com.dididi.kotlindemo.algorithm


/**
 * @author dididi(yechao)
 * @since 05/03/2019
 * @describe 插入排序 时间复杂度 n² 最好 n(已排好序) 最坏 n²
 */

fun insertionSort(intArray: IntArray) {
    for (i in 0 until intArray.size - 1) {
        //从i+1开始 取出当前项
        val curr = intArray[i + 1]
        var preIndex = i
        //从i往前比较，如果当前值小于前面已排序好的数组，插入其中
        while (preIndex >= 0 && curr < intArray[preIndex]) {
            //向右位移大于当前项的数，
            intArray[preIndex + 1] = intArray[preIndex]
            preIndex--
        }
        //直到找到比当前项还小的数或者已到0位置，赋值
        intArray[preIndex + 1] = curr
    }
}