package com.dididi.kotlindemo.`class`

import android.content.Context
import java.lang.IllegalArgumentException
import kotlin.reflect.KProperty


/**
 * @author dididi(叶超)
 * @email 512dididi@gmail.com
 * @since 05/07/2020
 * @describe sharedPreferences读写的属性代理类
 * @use
 * simply:
 *      var name by PreferenceDelegate(context,"")
 *      var age by PreferenceDelegate(context,0)
 *      name = "dididi"
 *      age = 23
 *      println(name)
 * recommend:
 *      fun <T> getPreference(defaultValue: T) = PreferenceDelegate(App.context, defaultValue).apply {
 *          pathName = "user_preference"
 *      }
 *      inner class User{
 *          var name by getPreference("")
 *          var age by getPreference(0)
 *      }
 *      val user = User()
 *      user.name = "dididi"
 *      user.age = 23
 *      println("${user.name} | ${user.age}")
 */

class PreferenceDelegate<T>(context: Context, private val defaultValue: T) {

    /**
     * 默认sp文件名
     */
    var pathName = "default_preference"

    private val sharedPref by lazy {
        context.getSharedPreferences(pathName, Context.MODE_PRIVATE)
    }

    /**
     * 重载SpDelegate的setValue方法，并将其set方法改写为 写入 value到sp文件中，并以property.name作为key
     */
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        setPreference(property.name, value)
    }

    /**
     * 重载SpDelegate的getValue方法，通过property.name作为key值，查询sp中对应的值
     */
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getPreference(property.name, defaultValue)
    }

    /**
     * 根据不同类型的[value]调用不同的方法存入sp中
     */
    private fun <V> setPreference(key: String, value: V) = with(sharedPref.edit()) {
        when (value) {
            is Long -> putLong(key, value)
            is String -> putString(key, value)
            is Float -> putFloat(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            else -> throw IllegalArgumentException("[$value : This type is not supported]")
        }
    }.apply()

    /**
     * 根据传入的默认值类型，来确定getValue应该返回什么类型的值
     */
    @Suppress("UNCHECKED_CAST")
    private fun <T> getPreference(key: String, defaultValue: T): T = sharedPref.run {
        val res: Any? = when (defaultValue) {
            is Long -> getLong(key, defaultValue)
            is Float -> getFloat(key, defaultValue)
            is String -> getString(key, defaultValue)
            is Int -> getInt(key, defaultValue)
            is Boolean -> getBoolean(key, defaultValue)
            else -> throw IllegalArgumentException("[$defaultValue : This type is not supported]")
        }
        res as T
    }
}




