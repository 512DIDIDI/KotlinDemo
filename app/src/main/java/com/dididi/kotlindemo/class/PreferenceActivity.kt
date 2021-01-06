package com.dididi.kotlindemo.`class`

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.dididi.kotlindemo.App
import com.dididi.kotlindemo.R
import kotlinx.android.synthetic.main.activity_sp.*


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 05/07/2020
 * @describe
 */

class PreferenceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sp)
        activitySpAddSpBtn.setOnClickListener {
            //实际调用的是prop的set方法
            UserConfig.name = "dididi"
            UserConfig.age = 23
            //实际调用的是prop的get方法
            Toast.makeText(this, "name:${UserConfig.name} | age:${UserConfig.age}", Toast.LENGTH_SHORT).show()
        }
    }
}

object UserConfig {
    var name by getPreference("")
    var age by getPreference(0)
}

fun <T> getPreference(defaultValue: T) = PreferenceDelegate(App.context, defaultValue).apply {
    pathName = "preference"
}