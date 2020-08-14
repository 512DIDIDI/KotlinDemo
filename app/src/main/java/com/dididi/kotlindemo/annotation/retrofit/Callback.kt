package com.dididi.kotlindemo.annotation.retrofit

import java.io.IOException


/**
 * @author dididi(yechao)
 * @since 14/08/2020
 * @describe
 */

/**主线程回调*/
interface Callback<T> {
    fun response(call: okhttp3.Call, response: T)
    fun failure(call: okhttp3.Call, e: IOException)
}