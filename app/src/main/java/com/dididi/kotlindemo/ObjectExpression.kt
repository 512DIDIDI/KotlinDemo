package com.dididi.kotlindemo


/**
 * @author dididi(yechao)
 * @since 11/01/2019
 * @describe
 */

/**
 * kotlin双重校验锁单例模式
 */
object Singleton {

    @Volatile
    private var instance: ObjectExpression? = null

    fun getInstance() = instance ?: synchronized(this) {
        instance ?: ObjectExpression().apply {
            instance = this
        }
    }
}

class ObjectExpression {

    companion object {
        @Volatile
        private var instance: ObjectExpression? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: ObjectExpression().apply {
                instance = this
            }
        }
    }
}