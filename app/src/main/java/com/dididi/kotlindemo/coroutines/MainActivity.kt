package com.dididi.kotlindemo.coroutines

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.Toast
import com.dididi.kotlindemo.R
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val loadings = arrayListOf<Dialog>()
    private lateinit var service: LoginService
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        service = retrofit.create(LoginService::class.java)
        activityMainGetImage.setOnClickListener {
            setImage()
        }
        activityMainSplitImage.setOnClickListener {
            splitImage()
        }
        activityMainLogin.setOnClickListener {
            login(activityMainUserName.text.toString(), activityMainPassword.text.toString())
        }
    }

    private fun login(userName: String, password: String) {
        mainScope.launch {
            showLoading()
            askLogin(userName, password, object :
                Callback {
                override fun success() {
                    Toast.makeText(this@MainActivity, "登录成功", Toast.LENGTH_SHORT).show()
                }

                override fun fail(errorMsg: String?) {
                    Toast.makeText(this@MainActivity, "登录失败$errorMsg", Toast.LENGTH_SHORT).show()
                }
            })
            closeLoading()
        }
    }

    interface LoginService {
        @POST("user/login")
        @FormUrlEncoded
        fun loginAsync(@Field("username") username: String, @Field("password") password: String): Deferred<LoginResponse>?
    }

    interface Callback {
        fun success()
        fun fail(errorMsg: String?)
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

    private suspend fun askLogin(userName: String, password: String, callback: Callback) =
        withContext(Dispatchers.IO) {
            val loginResponse = service.loginAsync(userName, password)?.await()
            withContext(Dispatchers.Main) {
                loginResponse ?: let {
                    callback.fail("result null")
                    return@withContext
                }
                if (loginResponse.errorCode != 0) {
                    callback.fail(loginResponse.errorMsg)
                } else {
                    callback.success()
                }
            }
        }

    private fun showLoading() {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_loading, null, false)
        val dialog = Dialog(this, R.style.custom_dialog)
        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(true)
        loadings.add(dialog)
        dialog.show()
    }

    private fun closeLoading() {
        loadings.forEach {
            it.dismiss()
        }
    }

    private fun setImage() {
        mainScope.launch {
            //主线程
            log("main")
            //切换到io线程进行下载图片操作
            val image =
                getImageResource("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576236172134&di=47f0d77404cd15208c59ba9dcb6ed6c2&imgtype=0&src=http%3A%2F%2Ffile02.16sucai.com%2Fd%2Ffile%2F2015%2F0408%2F779334da99e40adb587d0ba715eca102.jpg")
            //主线程更新ui
            activityMainIv.setImageBitmap(image)
        }
    }

    private fun splitImage() {
        mainScope.launch {
            val source =
                getImageResource("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576236172134&di=47f0d77404cd15208c59ba9dcb6ed6c2&imgtype=0&src=http%3A%2F%2Ffile02.16sucai.com%2Fd%2Ffile%2F2015%2F0408%2F779334da99e40adb587d0ba715eca102.jpg")
            //async开启协程返回的对象是Deferred 是一个job 可通过await获取结果
            val bitmap1 = async { splitBitmap(source!!, 2, 2, 0, 1) }
            val bitmap2 = async { splitBitmap(source!!, 3, 3, 1, 1) }
            activityMainIv.setImageBitmap(bitmap1.await())
            activityMainIv2.setImageBitmap(bitmap2.await())
        }
    }

    /**
     * [withContext]用于切换指定线程
     */
    private suspend fun getImageResource(url: String): Bitmap? = withContext(Dispatchers.IO) {
        val connection = URL(url).openConnection() as HttpURLConnection
        log("io")
        return@withContext connection.run {
            connectTimeout = 6000
            doInput = true
            useCaches = false
            requestMethod = "GET"
            connect()
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BitmapFactory.decodeStream(inputStream)
            } else {
                null
            }
        }
    }

    private suspend fun splitBitmap(
        source: Bitmap, sliceX: Int,
        sliceY: Int, posX: Int, posY: Int
    ): Bitmap = withContext(Dispatchers.Default) {
        val cropWidth = source.width / sliceX
        val cropHeight = source.height / sliceY
        Bitmap.createBitmap(source, cropWidth * posX, cropHeight * posY, cropWidth, cropHeight)
    }

    private fun log(any: Any?) {
        println("${Thread.currentThread().name}_$any")
    }
}
