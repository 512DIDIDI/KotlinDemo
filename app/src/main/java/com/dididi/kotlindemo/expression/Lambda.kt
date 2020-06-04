package com.dididi.kotlindemo.expression


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 28/05/2020
 * @describe Lambda表达式
 */

fun main(){
    println(f3(1))
}

//普通函数
fun func() {
    //用变量调用匿名函数 func2.invoke()
    func2()
}

/**
 * 匿名函数
 * func2是变量名
 * 用变量名()或变量名.invoke调用匿名函数
 * ()-Unit 函数类型
 */
val func2: () -> Unit = {}

/**
 * lambda表达式
 * ()->Unit 函数类型
 * lambda表达式返回值是闭包的最后一行
 * java中的lambda表达式实际是kotlin中的SAM(Single Abstract Method) 也就是接口并且只有单一方法
 * 实际java由于并没有引入函数类型，而只是SAM的语法糖
 * 而kotlin由于引进了[Function],创建了包含很多参数的[Function0]...等等，而实际上这也是SAM，
 * 只是kotlin对SAM接口中的方法invoke做了操作符重载，使其使用()就相当于调用.invoke方法
 * 所以实际kotlin中使用lambda表达式或者匿名函数作为返回值或者传参等实际都是[Function]的子类接口，跟java并无差别
 * 只是kotlin引入了[Function]接口，我们可以实例化出函数对象
 */
val lambda: () -> Unit = {
    println("hello")
}

/**
 * lambada表达式的几种写法
 */
val f1: (Int) -> Unit = { p ->
    println(p)
}

/**
 * 使用function类型
 * 表达式参数类型从表达式类型推断而来，所以p不需要类型
 */
val f2: Function1<Int, Unit> = { p ->
    println(p)
}

/**
 * 表达式类型从参数声明类型推断而来，所以不需要写函数类型
 */
val f3 = { p: Int ->
    println(p)
    //表达式最后一行为返回值
    "hello"
}

/**
 * 如果lambda表达式只有一个参数，可以用it指代那个参数
 */
val f4:Function1<Int,Unit> = {
    println(it)
}
