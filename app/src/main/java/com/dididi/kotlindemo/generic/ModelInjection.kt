package com.dididi.kotlindemo.generic

import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass
import kotlin.reflect.KProperty


/**
 * @author dididi(叶超)
 * @email 512dididi@gmail.com
 * @since 23/07/2020
 * @describe 基于泛型实现Model实例注入
 */



abstract class AbsModel{
    init {
        /**在每次初始化时，将[AbsModel]及其子类加入缓存中*/
        Models.run {
            register()
        }
    }
}

class DatabaseModel :AbsModel(){
    fun query(sql:String) = println("0")
}

class NetModel : AbsModel(){
    fun request(request:String) = println("""{code:"200"}""")
}

/**
 * 使用类名来注册实例
 */
abstract class AbsModel2{
    init{
        Models2.run {
            register()
        }
    }
}

class DatabaseModel2:AbsModel2(){
    fun query(sql: String) = println("1")
}
class NetModel2:AbsModel2(){

    init {
        Models2.run {
            register("Test")
        }
    }

    fun request(request: String) = println("""{code:"404"}""")
}

object Models{

    /**[Class<out AbsModel>]采用协变是为了能够兼容AbsModel的子类，使[Class<NetModel>]也是[Class<AbsModel>]的子类*/
    private val modelMap = ConcurrentHashMap<Class<out AbsModel>,AbsModel>()

    fun AbsModel.register(){
        modelMap[this.javaClass] = this
    }

    /**
     * 通过[KClass]获取缓存中[AbsModel]的实例
     */
    fun <T:AbsModel> KClass<T>.get():T{
        return modelMap[this.java] as T
    }
}

object Models2{
    private val modelMap = ConcurrentHashMap<String,AbsModel2>()

    /**可自定义变量名，这里的name会体现到[AbsModel2]子类实例化时的变量
     * 注意点：name首字母必须大写，为了兼容类名驼峰式命名
     */
    fun AbsModel2.register(name:String = this.javaClass.simpleName){
        modelMap[name] = this
    }

    /**通过javaClass.simpleName获取[AbsModel2]实例*/
    fun <T:AbsModel2> String.get():T{
        return modelMap[this] as T
    }
}

/**
 * 属性代理类，代理[getValue]方法，返回[Models]中的[AbsModel]实例
 */
class ModelDelegate<T:AbsModel>(private val kClass: KClass<T>){
    operator fun getValue(thisRef:Any,property:KProperty<*>):T{
        return Models.run {
            kClass.get()
        }
    }
}

object ModelDelegate2{
    operator fun <T:AbsModel2> getValue(thisRef: Any,property: KProperty<*>):T{
        return Models2.run {
            /**因为属性名通常是首字母小写的驼峰，类名通常时首字母大写的驼峰，所以需要将属性名首字母大写*/
            property.name.capitalize().get()
        }
    }
}

fun initModels(){
    DatabaseModel()
    NetModel()
    DatabaseModel2()
    NetModel2()
}

/**
 * 使用内联特化来优化反射注入，使得实例化时可以以泛型参数替换反射
 */
inline fun <reified T:AbsModel> modelOf():ModelDelegate<T>{
    return ModelDelegate(T::class)
}

/**VM 持有Model引用*/
class MainViewModel{
    /**使用属性代理+反射获取model实例*/
    val databaseModel by ModelDelegate(DatabaseModel::class)
    val netModel by ModelDelegate(NetModel::class)
    /**使用内联特化来替换反射注入*/
    val databaseModel1 by modelOf<DatabaseModel>()
    val netModel1 by modelOf<NetModel>()
    /**使用property.name来缓存实例 限制条件：属性名除首字母小写外必须与类名一致*/
    val databaseModel2:DatabaseModel2 by ModelDelegate2
    /**register更改实例名字 ，但这种方式有个弊端，会注册两个实例到缓存中，一个父类init块中的，一个子类init块中的*/
    val test:NetModel2 by ModelDelegate2
    val netModel2:NetModel2 by ModelDelegate2
}

fun main(){
    initModels()
    val mainViewModel = MainViewModel()
    mainViewModel.databaseModel.query("hello")
    mainViewModel.netModel.request("world")
    mainViewModel.databaseModel1.query("~")
    mainViewModel.netModel1.request("!")
    mainViewModel.databaseModel2.query("%")
    mainViewModel.netModel2.request("(")
    mainViewModel.test.request("&")
}