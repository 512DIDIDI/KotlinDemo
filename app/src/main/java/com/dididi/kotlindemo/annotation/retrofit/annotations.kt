package com.dididi.kotlindemo.annotation.retrofit


/**
 * @author dididi(yechao)
 * @since 14/08/2020
 * @describe 仿retrofit注解
 */

/**api路径*/
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Api(val url: String = "")

/**get请求*/
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Get(val url: String = "")

/**post请求*/
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Post(val url:String = "")

/**查询参数*/
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Query(val url:String ="")

/**路径替换*/
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Path(val path:String ="")

/**表单注解*/
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class FormUrlEncoded

/**表单参数*/
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Field(val field:String ="")