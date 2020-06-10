package com.dididi.kotlindemo.function

import java.io.File


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 10/06/2020
 * @describe 统计文件字符个数
 */

fun main(){
    countCharNumberByFile("app/src/main/java/com/dididi/kotlindemo/function/Event.java")
}

/**
 * 统计文件字符个数
 */
fun countCharNumberByFile(fileName:String){
    File(fileName).inputStream().reader().buffered()
        .use{
            it.readText()
        }//读取文件中的字符串
        .toCharArray()
        .filterNot { it.isWhitespace() }//过滤掉 空格 字符(与filter相反)
        .groupBy { it }//将集合按照 字符 分组，返回一个Map<R,List<T>>
        .map { it.key to it.value.size }//将map内的键值对映射为list，返回List<R>
        .let(::println)
}

/**
 * 简单写法
 */
fun countCharNumberSimply(fileName: String){
    File(fileName).readText()//读取文件中的字符串
        .toCharArray()
        .filterNot { it.isWhitespace() }//过滤掉 空格 字符(与filter相反)
        .groupBy { it }//将集合按照 字符 分组，返回一个Map<R,List<T>>
        .map { it.key to it.value.size }//将map内的键值对映射为list，返回List<R>
        .let(::println)
}