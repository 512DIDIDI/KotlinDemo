package com.dididi.kotlindemo.annotation

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions
import kotlin.reflect.full.valueParameters


/**
 * @author dididi(yechao)
 * @since 13/08/2020
 * @describe
 */

@Api("https://www.wanandroid.com")
interface WanandroidService {
    @Api("article")
    interface ArticleApi {
        @Get("list/{page}/json?cid={cid}")
        fun getArticle(page: Int,cid: Int): Call<ArticleData>
    }

    @Api("user")
    interface LoginApi {
        @Post("login")
        fun login(userName: String, password: String): Call<LoginResponse>
    }
}


object Retrofit {

    /**每个()括起来的内容就是一个group*/
    const val PATH_PATTERN = """(\{(\w+)\})"""

    /**函数缓存池*/
    val functionCache = HashMap<Method, ServiceMethod?>()

    val getFunction = { method: Method, kclz: KClass<*>, args: Array<Any> ->
        functionCache[method] ?: synchronized(functionCache) {
            if (functionCache[method] == null) {
                val functionMap = kclz.functions.map { it.name to it }.toMap()
                val function = functionMap[method.name]
                val serviceMethod = parseAnnotation(function, args)
                functionCache[method] = serviceMethod
                serviceMethod
            } else {
                functionCache[method]
            }
        }
    }

    val okHttp = OkHttpClient()

    inline fun <reified T> create(): T {
        return Proxy.newProxyInstance(
            Retrofit::class.java.classLoader,
            arrayOf(T::class.java)
        ) { proxy, method, args ->
            val serviceMethod = getFunction(method, T::class, args)
            when (serviceMethod?.requestMethod) {
                RequestMethod.GET -> okHttp.newCall(
                    Request.Builder().url(serviceMethod.url).get().build()
                )
                RequestMethod.POST -> null
                RequestMethod.PUT -> null
                RequestMethod.DELETE -> null
                null -> null
            }
        } as T
    }
}

fun parseAnnotation(function: KFunction<*>?, args: Array<Any>): ServiceMethod? {
    if (function == null) {
        return null
    }
    val parameterMap = function.valueParameters.map {
        it.name to args[it.index - 1]
    }.toMap()
    var url = ""
    var requestMethod = RequestMethod.GET
    for (annotation in function.annotations) {
        if (annotation is Get) {
            val endPoint =
                function.findAnnotation<Get>()!!.url.takeIf { it.isNotEmpty() } ?: function.name
            val compiledEndPoint =
                Regex(Retrofit.PATH_PATTERN).findAll(endPoint).map { matchResult ->
                    matchResult.groups[1]!!.value to parameterMap[matchResult.groups[2]?.value]
                }.fold(endPoint) { acc, pair ->
                    acc.replace(pair.first, pair.second.toString())
                }
            url = compiledEndPoint
            requestMethod = RequestMethod.GET
            return ServiceMethod(url, requestMethod)
        }
        if (annotation is Post) {

        }
    }
    return null
}

class ServiceMethod(val url: String, val requestMethod: RequestMethod)

enum class RequestMethod {
    GET, POST, PUT, DELETE
}

fun main(){
}


data class LoginResponse(
    var errorCode: Int,
    var errorMsg: String?,
    var data: Data
) {
    data class Data(
        var id: Int,
        var username: String,
        var password: String,
        var nickname: String,
        var publicname: String,
        var email: String?,
        var icon: String?,
        var type: Int,
        var collectId: List<Int>?
    )
}

