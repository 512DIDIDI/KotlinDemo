package com.dididi.kotlindemo.annotation.retrofit

import com.dididi.kotlindemo.annotation.ArticleData
import java.io.IOException


/**
 * @author dididi(yechao)
 * @since 14/08/2020
 * @describe
 */

fun main() {
    val articleApi =
        Retrofit.create<WanandroidService.ArticleApi>()
    articleApi.getArticle(0, 60)
        .enqueue(object :
            Callback<ArticleData> {
            override fun response(call: okhttp3.Call, response: ArticleData) {
                println(response)
            }

            override fun failure(call: okhttp3.Call, e: IOException) {
            }
        })
    val loginApi = Retrofit.create<WanandroidService.LoginApi>()
    println(loginApi.login("dididi","123456").execute())
}

@Api("https://www.wanandroid.com")
interface WanandroidService {
    @Api("article")
    interface ArticleApi {
        @Get("list/{page}/json")
        fun getArticle(@Path("page") page: Int, @Query("cid") cid: Int): Call<ArticleData>
    }

    @Api("user")
    interface LoginApi {
        @FormUrlEncoded
        @Post("login")
        fun login(
            @Field("username")
            userName: String,
            @Field("password")
            password: String
        ): Call<LoginResponse>
    }
}

data class LoginResponse(
    val data: Data,
    val errorCode: Int,
    val errorMsg: String
)

data class Data(
    val admin: Boolean,
    val chapterTops: List<Any>,
    val coinCount: Int,
    val collectIds: List<Int>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)