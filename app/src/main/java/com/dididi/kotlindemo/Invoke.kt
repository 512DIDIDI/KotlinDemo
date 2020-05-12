package com.dididi.kotlindemo


/**
 * @author dididi(yechao)
 * @since 14/01/2019
 * @describe invoke调用
 */

class Invokable {
    var numberOfInvocations: Int = 0
        //private set 指定对应属性的set方法可见性为private get还是为public
        private set

    operator fun invoke(): Invokable {
        numberOfInvocations ++
        return this
    }

}

fun invokeTwice(invokable: Invokable) = invokable()()

fun testInvoke(numberOfInvocation:Int,invokeSeveralTimes:(Invokable) -> Invokable){
    val invokable = Invokable()
    //递归调用
    println(numberOfInvocation == invokeSeveralTimes(invokable).numberOfInvocations)
}

fun main(){
    testInvoke(3){it()()()}
}