package com.dididi.kotlindemo.reflect

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible


/**
 * @author dididi(叶超)
 * @email 512dididi@gmail.com
 * @since 27/07/2020
 * @describe 可释放对象引用的不可空类型
 * 也就是不可空对象可置为null
 */

/**通过属性代理来代理get set方法*/
class ReleasableNotNull<T> : ReadWriteProperty<Any, T> {

    /**巧妙点在这里，实际上赋值是赋给了[value]，而[value]是可空类型的值*/
    private var value: T? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return value
            ?: throw NullPointerException("${property.name} is not initialized or release already")
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        this.value = value
    }

    /**是否初始化*/
    fun isInitial() = value != null

    /**release就是把value置null*/
    fun release() {
        value = null
    }
}

/**[KProperty0]与[Function0]作用类似，[KProperty0]代表的是property，而数字0代表的是无receiver*/
inline val KProperty0<*>.isInitial: Boolean
    get() {
        /**允许调用反射方法*/
        isAccessible = true
        return (this.getDelegate() as? ReleasableNotNull<*>)?.isInitial()
            ?: throw IllegalAccessException("delegate is not an instance of ReleasableNotNull or is null")
    }

fun KProperty0<*>.release() {
    isAccessible = true
    (this.getDelegate() as? ReleasableNotNull<*>)?.release()
        ?: throw IllegalAccessException("delegate is not an instance of ReleasableNotNull or is null")
}

fun <T> releasableNotNull() = ReleasableNotNull<T>()

data class Bitmap(val width: Int, val height: Int)

class Activity {
    var bitmap by releasableNotNull<Bitmap>()

    fun onCreate() {
        println(::bitmap.isInitial)
        bitmap = Bitmap(1920, 1080)
        println(::bitmap.isInitial)
    }

    fun onDestroy() {
        println(::bitmap.isInitial)
        /**使用::bitmap来获取[KProperty0]*/
        ::bitmap.release()
        println(::bitmap.isInitial)
    }
}

fun main(){
    val activity = Activity()
    activity.onCreate()
    activity.onDestroy()
}