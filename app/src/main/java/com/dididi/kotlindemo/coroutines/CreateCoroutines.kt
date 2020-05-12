package com.dididi.kotlindemo.coroutines

import kotlinx.coroutines.*


/**
 * @author dididi(yechao)
 * @since 12/05/2020
 * @describe 协程的三种创建方式 协程的作用域在闭包范围内
 */

fun main(){
    //方法一 使用runBlocking顶层函数创建
    //线程阻塞 主要用在main函数或测试中
    runBlocking {

    }

    //方法二 使用GlobalScope单例对象直接调用launch开启协程
    //不会线程阻塞 但生命周期会与app相同 且不能取消
    GlobalScope.launch {

    }

    //方法三 通过实例化CoroutineScope对象，调用launch开启协程
    //CoroutineScope()实例化需要CoroutineContext参数
    //推荐使用  通过CoroutineContext来管理和控制生命周期
    CoroutineScope(Dispatchers.IO).launch {

    }

}