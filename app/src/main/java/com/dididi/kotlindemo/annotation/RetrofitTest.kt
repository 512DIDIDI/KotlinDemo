package com.dididi.kotlindemo.annotation

import com.dididi.kotlindemo.annotation.retrofit.Api
import com.dididi.kotlindemo.annotation.retrofit.Get
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.reflect.Proxy
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions
import kotlin.reflect.full.valueParameters


/**
 * @author dididi(yechao)
 * @since 13/08/2020
 * @describe 仿写retrofit的动态代理及注解处理
 * retrofit：
 * 1. 通过create动态代理创建ServiceApi的代理对象，
 * 2. 通过InvocationHandler.invoke处理ServiceApi内的所有方法，
 * 3. 创建ServiceMethod或从缓存中读取，ServiceMethod主要是处理注解，返回类型等
 * 4. 将ServiceMethod封装成OkHttpCall（Retrofit2.Call的子类），OkHttpCall可以在需要的时候(如调用enqueue)创建OkHttp3.Call对象，进行网络请求。
 * 5. CallAdapter将OkHttpCall转为对应的适配器（原生是Retrofit2.Call,kotlin还可以使用Deffered），作为ServiceApi的方法的返回值
 * 6. 主线程处理响应
 */



@Api("https://www.wanandroid.com")
interface Service {

    @Api("article")
    interface ArticleApi {

        @Get("list/{page}/json?cid={cid}")
        fun getArticle(page: Int, cid: Int): ArticleData
    }
}

object RetrofitTest {

    /**每个()括起来的内容就是一个group*/
    const val PATH_PATTERN = """(\{(\w+)\})"""

    val okHttp = OkHttpClient()
    val gson = Gson()

    /**获取类的嵌套类 Service-Article */
    val enclosing = { clz: Class<*> ->
        var currentClz: Class<*>? = clz
        sequence {
            while (currentClz != null) {
                currentClz = currentClz?.also { yield(it) }?.enclosingClass
            }
        }
    }

    inline fun <reified T> create(): T {
        /**获取函数名称与函数的map，便于后续根据method.name查找到对应的函数*/
        val functionMap = T::class.functions.map { it.name to it }.toMap()

        /**获取T类的外部类list*/
        val interfaces = enclosing(T::class.java).takeWhile { it.isInterface }.toList()

        /**拼接Api注解里的url路径，由于interfaces里获取的是从内向外的Article-Service，而路径拼接是从外向内，所以需要foldRight*/
        val apiPath = interfaces.foldRight(StringBuilder()) { clazz, acc ->
            acc.append(
                //获取Api注解里的值，如果没有值，则取类名，并在最后加上/拼接
                clazz.getAnnotation(Api::class.java)?.url?.takeIf { it.isNotEmpty() } ?: clazz.name
            ).append("/")
        }.toString()

        /**动态代理生成T的代理接口对象*/
        return Proxy.newProxyInstance(
            RetrofitTest::class.java.classLoader,
            arrayOf(T::class.java)
        ) { proxy, method, args ->
            /**通过functionMap找到相应的方法，执行它*/
            functionMap[method.name]?.takeIf { it.isAbstract }?.let { function ->
                /**获取参数名-值的map*/
                val parameterMap = function.valueParameters.map {
                    it.name to args[it.index - 1]
                }.toMap()

                /**获取Get注解的值，如果为空，则取函数名*/
                val endPoint = function.findAnnotation<Get>()!!.url.takeIf { it.isNotEmpty() }?:function.name
                /**替换{}的内容为method传入的值*/
                val compiledEndPoint = Regex(PATH_PATTERN).findAll(endPoint).map {
                    matchResult ->
                    //groups[0]获取的是整个匹配的内容 groups[1]获取的第一个()括出来的内容，也就是{page}，
                    //groups[2]获取的是第二个()括出来的内容，也就是page
                    matchResult.groups[1]!!.value to parameterMap[matchResult.groups[2]!!.value]
                }.fold(endPoint){
                    acc, pair ->
                    //替换{page}为参数值
                    acc.replace(pair.first,pair.second.toString())
                }
                /**获取最终拼接成功的url*/
                val url = apiPath + compiledEndPoint
                println(url)
                /**调用OkHttp发起同步请求，并转为gson序列*/
                okHttp.newCall(Request.Builder().url(url).get().build()).execute().body()?.charStream()?.use {
                    gson.fromJson(JsonReader(it),method.genericReturnType)
                }
            }
        } as T
    }
}

fun main() {
    val article = RetrofitTest.create<Service.ArticleApi>()
    println(article.getArticle(0, 60))
}

data class ArticleData(
    val data: Data,
    val errorCode: Int,
    val errorMsg: String
)

data class Data(
    val curPage: Int,
    val datas: List<DataX>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class DataX(
    val apkLink: String,
    val audit: Int,
    val author: String,
    val canEdit: Boolean,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    val desc: String,
    val descMd: String,
    val envelopePic: String,
    val fresh: Boolean,
    val id: Int,
    val link: String,
    val niceDate: String,
    val niceShareDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val realSuperChapterId: Int,
    val selfVisible: Int,
    val shareDate: Long,
    val shareUser: String,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: List<Any>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
)
