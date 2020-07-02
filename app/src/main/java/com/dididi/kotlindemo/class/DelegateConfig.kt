package com.dididi.kotlindemo.`class`

import java.net.URL
import kotlin.reflect.KProperty


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 02/07/2020
 * @describe 使用属性代理读写config，properties文件
 */

class Prop(){
    private lateinit var url: URL
    operator fun setValue(thisRef:Any?,property:KProperty<*>,value:String){

    }
}