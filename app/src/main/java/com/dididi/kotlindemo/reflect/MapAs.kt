package com.dididi.kotlindemo.reflect

import java.lang.IllegalArgumentException
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor


/**
 * @author dididi(叶超)
 * @email 512dididi@gmail.com
 * @since 26/07/2020
 * @describe Model映射（简易版，需要两个参数名相同）
 */

/**任意类型映射为数据类*/
inline fun <reified From : Any, reified To : Any> From.mapAs(): To {
    /**[memberProperties]获取所有属性 将其转为Map，然后再调用Map.mapAs即可*/
    return From::class.memberProperties.map {
        it.name to it.get(this)
    }.toMap().mapAs()
}

/**将map映射为数据类*/
inline fun <reified To : Any> Map<String, Any?>.mapAs(): To {
    /**调用To的构造函数*/
    return To::class.primaryConstructor!!.let { primaryConstructor ->
        primaryConstructor.parameters.map {
            /**首先判断map传入的值是否为空 然后再判断To是否接收可空类型数据*/
            it to (this[it.name] ?: if (it.type.isMarkedNullable) null
            else throw IllegalArgumentException("${it.name} can not be null"))
        }.toMap()
            /**构造函数*/
            .let(primaryConstructor::callBy)
    }
}

data class UserVO(val name: String, val avatarUrl: String?)

data class UserDTO(val id: Int, val name: String, val avatarUrl: String, val url: String)

fun main() {
    val userDTO = UserDTO(
        0,
        "dididi",
        "https://avatars3.githubusercontent.com/u/30096946?s=460&u=18e7db63fe6a743558e6c5ff46a31eca29a68280&v=4",
        "https://github.com/512DIDIDI"
    )
    val userVO: UserVO = userDTO.mapAs()
    println(userVO)
    val userMap = mapOf(
        "id" to 0,
        "name" to "dididi",
        "avatarUrl" to null,
        "url" to "https://github.com/512DIDIDI"
    )
    val userVOFromMap: UserVO = userMap.mapAs()
    println(userVOFromMap)
}