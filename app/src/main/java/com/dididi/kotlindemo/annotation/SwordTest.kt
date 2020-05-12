package com.dididi.kotlindemo.annotation

import java.util.*
import kotlin.reflect.full.declaredFunctions


/**
 * @author dididi(yechao)
 * @since 17/01/2019
 * @describe
 */

@Run
class SwordTest {

    @TestCase(id = "1")
    fun testCase(testId: String) {
        println("testId:$testId")
    }

    @TestCase(id = "2")
    fun testCase2(testId: String){
        println("testId2:$testId")
    }

    fun test(){}
}

fun testAnnoProcessing() {
    val sword = SwordTest()
    //获取sword对象实例的KClass类的引用
    val KClass = sword::class
    //获取sword对象类型所声明的所有函数
    val declaredFunctions = KClass.declaredFunctions
    println(declaredFunctions)
    for (f in declaredFunctions) {
        //处理TestCase注解，
        f.annotations.forEach {
            if (it is TestCase) {
                //testCase注解使用的id
                val id = it.id
                //处理注解逻辑
                doSomething(id)
                //通过反射调用SwordTest当中的TestCase注解的方法
                f.call(sword, id)
            }
        }
    }
}

private fun doSomething(id: String) {
    println("Do something in Annotation Processing $id ${Date()}")
}