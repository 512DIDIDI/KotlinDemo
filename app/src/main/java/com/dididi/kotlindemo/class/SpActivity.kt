package com.dididi.kotlindemo.`class`

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.dididi.kotlindemo.R
import kotlinx.android.synthetic.main.activity_sp.*


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 05/07/2020
 * @describe
 */

class SpActivity : AppCompatActivity() {

    class UserConfig:AbsSpConfig("user_config"){
        //通过属性代理读写sharedPreferences的数据(prop代替了name,age的set/get方法)
        var name by prop
        var age by prop
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sp)
        val user = UserConfig()
        activitySpAddSpBtn.setOnClickListener {
            //实际调用的是prop的set方法
            user.name = "dididi"
            user.age = "23"
            //实际调用的是prop的get方法
            Toast.makeText(this, "name:${user.name} | age:${user.age}", Toast.LENGTH_SHORT).show()
        }
    }
}