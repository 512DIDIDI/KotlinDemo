package com.dididi.kotlindemo.annotation.retrofit

import okhttp3.FormBody
import okhttp3.RequestBody
import java.lang.reflect.Method
import java.lang.reflect.Type
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.valueParameters
import kotlin.reflect.jvm.javaType
import kotlin.reflect.jvm.kotlinFunction


/**
 * @author dididi(yechao)
 * @since 14/08/2020
 * @describe ServiceMethod
 */

/**
 * http请求信息封装
 * @param url           请求地址
 * @param requestMethod http请求方法
 * @param responseT     函数返回值的泛型实参
 */
class ServiceMethod<T>(
    val url: String,
    val requestMethod: RequestMethod,
    private val responseT: T,
    val requestBody: RequestBody? = null
) {

    /**封装okHttp3.Call对象及返回值类型生成[Call]对象*/
    fun adapt(call: okhttp3.Call?): Call<T>? {
        return Call(call, responseT)
    }
}

/**获取类的嵌套类 Service-Article */
val enclosing = { clz: Class<*> ->
    var currentClz: Class<*>? = clz
    sequence {
        while (currentClz != null) {
            currentClz = currentClz?.also { yield(it) }?.enclosingClass
        }
    }
}

/**拼接api注解下包裹的路径*/
val apiPath = { kclz: KClass<*> ->
    //获取T类的外部类list
    val interfaces = enclosing(kclz.java)
        .takeWhile { it.isInterface }.toList()
    //拼接Api注解里的url路径，由于interfaces里获取的是从内向外的Article-Service，而路径拼接是从外向内，所以需要foldRight
    interfaces.foldRight(StringBuilder()) { clazz, acc ->
        acc.append(
            //获取Api注解里的值，如果没有值，则取类名，并在最后加上/拼接
            clazz.getAnnotation(Api::class.java)?.url?.takeIf { it.isNotEmpty() } ?: clazz.name
        ).append("/")
    }.toString()
}

/**
 * 解析方法，注解，返回值等封装成ServiceMethod
 * @param function 待处理的函数
 * @param args     参数值
 */
fun parseAnnotations(method: Method, kclz: KClass<*>, args: Array<Any>): ServiceMethod<*>? {
    //获取api注解的路径
    val apiPath = apiPath(kclz)
    //获取方法对应的KFunction
    val function = method.kotlinFunction ?: return null
    //获取函数返回值的泛型实参
    val returnType = function.returnType.arguments[0].type?.javaType
    //注解处理
    for (annotation in function.annotations) {
        when (annotation) {
            is Get -> return parseGetAnnotation(
                apiPath,
                function,
                args,
                returnType
            )
            is Post -> return parsePostAnnotation(
                apiPath,
                function,
                args,
                returnType
            )
        }
    }
    return null
}

/**每个()括起来的内容就是一个group*/
const val PATH_PATTERN = """(\{(\w+)\})"""

/**
 * 处理get注解
 * @param apiPath       interface api组成的路径
 * @param function      待解析函数
 * @param args          参数值
 * @param returnType    返回值泛型实参
 */
fun parseGetAnnotation(
    apiPath: String,
    function: KFunction<*>,
    args: Array<Any>,
    returnType: Type?
): ServiceMethod<*> {
    //path 路径名-参数值map
    val pathParameterMap = parsePathAnnotation(function, args)
    val endPoint =
        function.findAnnotation<Get>()!!.url.takeIf { it.isNotEmpty() } ?: function.name
    //替换注解{}为参数值
    val compiledEndPoint =
        Regex(PATH_PATTERN).findAll(endPoint).map { matchResult ->
            matchResult.groups[1]!!.value to pathParameterMap[matchResult.groups[2]?.value]
        }.fold(endPoint) { acc, pair ->
            acc.replace(pair.first, pair.second.toString())
        }
    //获取query参数
    val queryParameter = parseQueryAnnotation(function, args)
    //将api路径，替换的path路径，query参数拼接成url
    val url = apiPath + compiledEndPoint + queryParameter
    //封装ServiceMethod对象
    return ServiceMethod(
        url,
        RequestMethod.GET,
        returnType
    )
}

/**
 * 处理Path注解，并转为name-参数值键值对
 * 例如：[page = 1]
 */
fun parsePathAnnotation(function: KFunction<*>, args: Array<Any>): Map<String?, Any> {
    return function.valueParameters.filter {
        //筛选有Path注解的参数
        it.findAnnotation<Path>() != null
    }.map { kParameter ->
        //查找Path注解的值，如果值为空，则取参数名
        (kParameter.findAnnotation<Path>()!!.path.takeIf { it.isNotEmpty() }
            ?: kParameter.name) to args[kParameter.index - 1]
    }.toMap()
}

/**
 * 处理Query注解，解析查询参数，拼接成字符串
 * 例如：?cid=60&name=dididi
 */
fun parseQueryAnnotation(function: KFunction<*>, args: Array<Any>): String {
    return function.valueParameters.filter {
        it.findAnnotation<Query>() != null
    }.fold(StringBuilder().append("?")) { acc, kParameter ->
        acc.append(
            //获取query注解的值，如果为空，则取参数名
            kParameter.findAnnotation<Query>()!!.url.takeIf { it.isNotEmpty() } ?: kParameter.name
        ).append(
            "="
        ).append(
            args[kParameter.index - 1]
        ).append(
            "&"
        )
    }.apply {
        //删除最后一个字符，也就是多余的&符号
        this.deleteCharAt(this.length - 1)
    }.toString()
}

/**
 * 解析post注解
 * 参数作用参考 [parseGetAnnotation]
 */
fun parsePostAnnotation(
    apiPath: String,
    function: KFunction<*>,
    args: Array<Any>,
    returnType: Type?
): ServiceMethod<*> {
    //拼接url
    val endPoint = function.findAnnotation<Post>()!!.url.takeIf { it.isNotEmpty() } ?: function.name
    val url = apiPath + endPoint
    //是否是表单数据
    val isFormUrlEncoded = function.findAnnotation<FormUrlEncoded>() != null
    //拼接RequestBody
    val body = if (isFormUrlEncoded) {
        val builder = FormBody.Builder()
        //处理field注解
        parseFieldAnnotation(function,args).forEach {
            it.key?.apply {
                builder.add(this,it.value.toString())
            }
        }
        builder.build()
    } else {
        null
    }
    return ServiceMethod(
        url,
        RequestMethod.POST,
        returnType,
        body
    )
}

/**
 * 处理field注解，最终会加在body中
 * 例如[username = dididi]
 */
fun parseFieldAnnotation(function: KFunction<*>, args: Array<Any>): Map<String?, Any> {
    return function.valueParameters.filter {
        it.findAnnotation<Field>() != null
    }.map { kParameter ->
        (kParameter.findAnnotation<Field>()!!.field.takeIf { it.isNotEmpty() }
            ?: kParameter.name) to args[kParameter.index - 1]
    }.toMap()
}





