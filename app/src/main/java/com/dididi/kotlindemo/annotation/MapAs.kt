package com.dididi.kotlindemo.annotation

import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor


/**
 * @author dididi(yechao)
 * @since 17/08/2020
 * @describe Model映射 增强版 （能够处理不同的参数名称）
 * 例如ORM数据库的数据映射
 */

/**
 * 用于注解整个数据类，采取何种策略映射参数
 */
@Target(AnnotationTarget.CLASS)
annotation class MappingStrategy(val kclz:KClass<NameStrategy>)

/**
 * 用于注解参数名
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class FieldName(val name: String)

interface NameStrategy{
    fun mapTo(name: String):String
}

/**
 * 驼峰命名转为下划线命名
 */
object CamelToUnderlineStrategy:NameStrategy{
    override fun mapTo(name: String): String {
        TODO("Not yet implemented")
    }
}

/**
 * 下划线命名转为驼峰命名
 */
object UnderlineToCamelStrategy:NameStrategy{
    override fun mapTo(name: String): String {
        TODO("Not yet implemented")
    }
}

/**任意类型映射为数据类*/
inline fun <reified From : Any, reified To : Any> From.mapAs(): To {
    //[memberProperties]获取所有属性 将其转为Map，然后再调用Map.mapAs即可
    return From::class.memberProperties.map {
        it.name to it.get(this)
    }.toMap().mapAs()
}

/**将map映射为数据类*/
inline fun <reified To : Any> Map<String, Any?>.mapAs(): To {
    return To::class.primaryConstructor!!.let { primaryConstructor ->
        primaryConstructor.parameters.map {
            //首先判断map传入的值是否为空
            it to (this[it.name]
                //如果找不到参数名对应的值，则查找FieldName注解对应的值
                ?: it.findAnnotation<FieldName>()?.name?.let(this::get)
                //再判断To是否接收可空类型数据
                ?: if (it.type.isMarkedNullable) null
                else throw IllegalArgumentException("${it.name} can not be null"))
        }.toMap()
            .let(primaryConstructor::callBy)
    }
}

data class UserVO(
    val name: String,
    @FieldName("avatar_url")
    val avatarUrl: String?
)

data class UserDTO(val id: Int, val name: String, val avatar_url: String, val url: String)

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