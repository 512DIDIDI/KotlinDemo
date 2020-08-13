package com.dididi.kotlindemo.`class`


/**
 * @author dididi(yechao)
 * @since 11/01/2019
 * @describe 单例模式
 * 饿汉与懒汉的区别在于：
 *          1. 饿汉是在用到类的时候就初始化单例(例如加载Singleton这个类)
 *          2. 而懒汉是在需要用到单例时才初始化单例(一般来说就是调用getInstance()方法)
 */

/**
 * 饿汉式单例模式
 */
object Single{
    //java访问：Single.INSTANCE.getX() Single.INSTANCE.f() kotlin访问： Single.x Single.f()
    var x = "2"
    fun f(){}
    //加了@JvmStatic后，java的访问，就如同Single的静态方法一样:Single.getY() Single.t()
    @JvmStatic var y = 3
    @JvmStatic fun t(){}
    //加了@JvmField后，就会生成一个静态变量z，访问就是：Single.z
    @JvmField var z = 2
}

/**
 * kotlin双重校验锁单例模式
 */
object Singleton {

    @Volatile
    private var instance: ObjectExpression? = null

    fun getInstance() = instance ?: synchronized(this) {
        instance
            ?: ObjectExpression().apply {
            instance = this
        }
    }
}

class ObjectExpression {

    companion object {
        @Volatile
        private var instance: ObjectExpression? = null

        fun getInstance() = instance
            ?: synchronized(this) {
            instance
                ?: ObjectExpression().apply {
                instance = this
            }
        }
    }
}