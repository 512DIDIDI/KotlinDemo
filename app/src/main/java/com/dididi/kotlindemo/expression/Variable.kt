package com.dididi.kotlindemo.expression


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 27/05/2020
 * @describe 常量与变量
 */


class Variable {

    //类似这种情况 val就不等价于java中的final常量
    //运行时才能确定值，调用处通过引用获取值
    val b: Int
        get() {
            return Math.random().toInt()
        }

    companion object{
        //常量 类似java static final
        //编译时即可确定常量的值，并用值替换调用处
        const val c = 3
    }
}

fun main() {
    //可读写变量
    var a = 3
    //只读变量(注意 仅在局部变量时才等价于java的final 如果是类属性 不一定是常量)
    val b = 2
}
