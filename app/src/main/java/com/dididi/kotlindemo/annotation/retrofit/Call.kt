package com.dididi.kotlindemo.annotation.retrofit

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.lang.reflect.Type


/**
 * @author dididi(yechao)
 * @since 14/08/2020
 * @describe
 */

/**封装[okhttp3.Call]对象，这个是作为ServiceApi中方法的返回值，方便进行回调，和body类型转换*/
class Call<T>(private val call: okhttp3.Call?, private val returnType: T) {

    private val gson = Gson()

    /**异步请求*/
    fun enqueue(callback: com.dididi.kotlindemo.annotation.retrofit.Callback<T>) {
        call?.enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                callback.failure(call, e)
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                val finalResponse = parseResponse(response)
                callback.response(call, finalResponse)
            }
        })
    }

    /**同步请求*/
    fun execute(): T {
        return parseResponse(call?.execute())
    }

    /**
     * 解析http响应报文，将其转为ServiceApi的方法的返回类型的泛型实参
     * 这里为了方便，直接转为json，实际上应该是根据convertFactory进行相应类型的转换
     */
    private fun parseResponse(rawResponse: Response?): T {
        return rawResponse?.body()?.charStream().use {
            gson.fromJson(JsonReader(it), returnType as Type)
        }
    }
}