package com.dididi.kotlindemo.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 17/05/2020
 * @describe 挂起函数
 * 1.是什么                  实质上是切换到指定线程的函数操作，执行完任务后再自动切回原线程
 * 2.挂起的对象是什么         挂起协程
 * 3.suspend关键字的作用      提醒调用者此函数是个耗时操作
 * 4.何时用挂起函数           任务耗时或者需要等待
 * 5.怎么用                  使用suspend修饰函数 并在函数内部使用另一个挂起函数(例如 [withContext] )
 * 6.被谁调用                被协程或者另一个挂起函数调用(本质上都是协程)因为kotlin切回来的动作叫resume，需要在协程中恢复
 * 7.非阻塞式挂起是什么       代码看上去写起来像单线程阻塞式，实际上挂起函数帮我们切了线程执行了耗时操作
 */

fun main(){
    val coroutineScope = CoroutineScope(Dispatchers.Main)
    coroutineScope.launch {
        //io线程获取图片
        val imageSrc = getImage()
        //切换为主线程更新ui
    }
}

/**
 * 返回值为withContext最后一行
 */
suspend fun getImage() = withContext(Dispatchers.IO){
    ""
}