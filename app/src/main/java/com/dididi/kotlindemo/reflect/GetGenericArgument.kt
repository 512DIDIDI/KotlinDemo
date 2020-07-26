package com.dididi.kotlindemo.reflect

import java.lang.reflect.ParameterizedType
import kotlin.reflect.full.allSupertypes
import kotlin.reflect.full.declaredFunctions


/**
 * @author dididi(叶超)
 * @email 512dididi@gmail.com
 * @since 25/07/2020
 * @describe 获取泛型实参
 * 注意：获取泛型实参实际是根据字节码文件的signature来描述获取的
 *      如果在实际生产中使用，需要避免混淆导致找不到
 *      Proguard配置： -keepattributes Signature
 */

data class User(val name: String)

interface Api {
    fun getUser(): List<User>
}

abstract class SuperType<T> {
    val typeParameter by lazy {
        /**因为SuperType是抽象类，所以this指向的是具体实例化的子类*/
        this::class
            /**获取父类类型*/
            .supertypes.first()
            /**获取父类类型的泛型参数的类型*/
            .arguments.first().type!!
    }
    val typeParameterJava by lazy {
        this.javaClass
                /**获取父类类型*/
            .genericSuperclass.safeAs<ParameterizedType>()!!
            .actualTypeArguments.first()
    }

    val superClassAll by lazy {
        /**获取所有的父类，继承树*/
        this::class.allSupertypes
    }
}

open class SubType : SuperType<String>()

class SubType2:SubType()

fun main() {
    /**获取getUser函数*/
    Api::class.declaredFunctions.first { it.name == "getUser" }
        //获取getUser的返回值的泛型类型
        .returnType.arguments.forEach {
            println(it)
        }
    /**也可使用函数引用获取getUser函数*/
    Api::getUser
        //同上
        .returnType.arguments.forEach {
            println(it)
        }
    /**使用java反射获取getUser函数*/
    Api::class.java.getDeclaredMethod("getUser")
        /**java获取返回值type需要转为[ParameterizedType]才能获取具体的泛型参数类型*/
        .genericReturnType.safeAs<ParameterizedType>()?.actualTypeArguments?.forEach {
            println(it)
        }
    val subType = SubType()
    /**返回kotlin.String*/
    println(subType.typeParameter)
    /**注意：java返回的泛型类型跟kotlin不一致，返回的是java.lang.String，因为java只认识java类型，而kotlin最终编译是编译为java*/
    println(subType.typeParameterJava)
    val subType2 = SubType2()
    /**会报错，获取不到父类的泛型参数*/
    println(subType2.typeParameter)
}

fun <T> Any.safeAs(): T? {
    return this as? T
}