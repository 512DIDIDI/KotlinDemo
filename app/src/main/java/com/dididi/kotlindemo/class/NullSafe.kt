package com.dididi.kotlindemo.`class`

import java.io.File


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 25/05/2020
 * @describe 空类型安全
 * 1. 尽可能使用val来声明不可变引用 让程序含义更加清晰确定
 * 2. 尽可能减少函数对外部变量的访问，也能为函数式编程提供基础
 * 3. 必要时创建局部变量指向外部变量
 */

fun main() {
    //kotlin默认的类型是非空类型
    val a: String = "hello"
    println(a.length)
    //如果需要标注类型为可空 用?
    var b: String? = "world"
    //?. 等价于 if(b == null){return null}else{return b}
    println(b?.length)
    //!! 适用于b绝对不可能为空的场景，慎用，因为如果b为空会报空指针
    println(b!!.length)
    b = null
    //?: elvis语句 等价于 if(b.length == null){return 0} else{return b.length}
    println(b?.length ?: 0)
    //kotlin跨平台开发时需要注意，当使用java js native代码时，kotlin获取到的类型是平台类型
    //编译器无法知道平台类型是否为空，使用平台类型的变量需小心注意非空判断
    val files = File("abc")
    //如这个例子 Array<(out)File!>! 就是平台类型  类型带!就是平台类型，调用时尽量都加上?.做非空判断，否则会出现空指针
    val dic = files.listFiles()
    //这样会出现空指针
//    println(dic.size)
    //安全的调用方式
    println(dic?.size)
    val fruit: Fruit = Apple()
    //判断类型 kotlin中的is判断后
    if (fruit is Apple) {
        //范围内就不需要再强转类型了
        println(fruit.name)
    }
    //as用来强制类型转换
    val apple =  fruit as Apple
    //可空类型转换
    val apple2 = (fruit as? Apple)?.name
}

open class Fruit()

class Apple() : Fruit(){
    val name = "apple"
}