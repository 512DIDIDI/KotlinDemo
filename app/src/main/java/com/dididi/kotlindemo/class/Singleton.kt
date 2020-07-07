package com.dididi.kotlindemo.`class`


/**
 * @author dididi(yechao)
 * @since 11/01/2019
 * @describe
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