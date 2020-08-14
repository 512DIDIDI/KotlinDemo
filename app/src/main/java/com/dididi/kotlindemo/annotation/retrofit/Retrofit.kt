package com.dididi.kotlindemo.annotation.retrofit

import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.reflect.KClass


/**
 * @author dididi(yechao)
 * @since 13/08/2020
 * @describe
 */


object Retrofit {

    /**函数缓存池*/
    private val serviceMethodCache = HashMap<Method, ServiceMethod<*>?>()

    inline fun <reified T> create(): T {
        return Proxy.newProxyInstance(
            Retrofit::class.java.classLoader,
            arrayOf(T::class.java)
        ) { proxy, method, args ->
            //创建ServiceMethod
            val serviceMethod =
                loadServiceMethod(
                    method,
                    T::class,
                    args
                )
            //创建OkHttpCall
            val call = getOkHttpCall(
                serviceMethod
            )
            //将OkHttpCall封装成Call，作为返回值
            serviceMethod?.adapt(call)
        } as T
    }

    /**加载ServiceMethod，如果缓存池中有则直接拿，没有则通过[parseAnnotations]创建*/
    val loadServiceMethod = { method: Method, kclz: KClass<*>, args: Array<Any> ->
        serviceMethodCache[method] ?: synchronized(
            serviceMethodCache
        ) {
            if (serviceMethodCache[method] == null) {
                //创建serviceMethod类对象
                val serviceMethod =
                    parseAnnotations(
                        method,
                        kclz,
                        args
                    )
                serviceMethodCache[method] = serviceMethod
                serviceMethod
            } else {
                serviceMethodCache[method]
            }
        }
    }
}

/**
 * 创建OkHttpCall对象
 */
fun getOkHttpCall(serviceMethod: ServiceMethod<*>?): okhttp3.Call? {
    val okHttp = OkHttpClient()
    return when (serviceMethod?.requestMethod) {
        RequestMethod.GET ->
            okHttp.newCall(
                Request.Builder().url(serviceMethod.url).get().build()
            )
        RequestMethod.POST -> {
            serviceMethod.requestBody?.let {
                okHttp.newCall(Request.Builder().url(serviceMethod.url).post(it).build())
            }
        }
        null -> null
    }
}

/**http请求方法*/
enum class RequestMethod {
    GET, POST
}








