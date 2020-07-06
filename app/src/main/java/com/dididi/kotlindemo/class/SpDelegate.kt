package com.dididi.kotlindemo.`class`

import android.content.Context
import com.dididi.kotlindemo.App
import kotlin.reflect.KProperty


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 05/07/2020
 * @describe sharedPreferences读写的属性代理类
 */

class SpDelegate(pathName: String) {

    private val sharedPref = App.context.getSharedPreferences(pathName,Context.MODE_PRIVATE)

    /**
     * 重载SpDelegate的setValue方法，并将其set方法改写为 写入 value到sp文件中，并以property.name作为key
     */
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
        with(sharedPref.edit()) {
            putString(property.name, value)
            apply()
        }
    }

    /**
     * 重载SpDelegate的getValue方法，通过property.name作为key值，查询sp中对应的值
     */
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String? {
        return sharedPref.getString(property.name, "")
    }

}

abstract class AbsSpConfig(pathName: String){
    //实例化prop类，可作为子类的代理类
    protected val prop = SpDelegate(pathName)
}