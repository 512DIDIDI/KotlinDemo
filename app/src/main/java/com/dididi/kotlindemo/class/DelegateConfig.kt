package com.dididi.kotlindemo.`class`

import java.io.File
import java.io.FileInputStream
import java.net.URL
import java.util.*
import kotlin.reflect.KProperty


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 02/07/2020
 * @describe 使用属性代理读写config.properties文件
 */

class PropertyDelegate(private val path:String,private val defaultValue:String = ""){

    private lateinit var url: URL

    private val properties:Properties by lazy {
        val prop = Properties()
        //通过classLoader来获取url
        url = try {
            javaClass.getResourceAsStream(path).use {
                prop.load(it)
            }
            javaClass.getResource(path)
        }catch (e:Exception){
            try {
                ClassLoader.getSystemClassLoader().getResourceAsStream(path).use {
                    prop.load(it)
                }
                ClassLoader.getSystemClassLoader().getResource(path)
            }catch (e:Exception){
                FileInputStream(path).use {
                    prop.load(it)
                }
                //这里要用///三斜杠，否则会抛异常 URI has an authority component
                URL("file:///${File(path).canonicalPath}")
            }
        }
        prop
    }

    operator fun setValue(thisRef:Any?,property:KProperty<*>,value:String){
        properties.setProperty(property.name,value)
        //将properties的属性写入文件中
        File(url.toURI()).outputStream().use {
            properties.store(it,"hello")
        }
    }

    operator fun getValue(thisRef: Any?,property: KProperty<*>):String{
        return properties.getProperty(property.name,defaultValue)
    }
}

abstract class AbsProperty(path: String){
    protected val prop = PropertyDelegate(path)
}

class Config:AbsProperty("config.properties"){
    //代理了set/get方法
    var author by prop
    var version by prop
    var desc by prop
}

fun main() {
    val config = Config()
    //实际调用的是prop的getValue方法
    println(config.author)
    //实际调用是prop的setValue方法
    config.author = "dididi"
    config.version = "1.0"
    config.desc = "this is a demo"
}