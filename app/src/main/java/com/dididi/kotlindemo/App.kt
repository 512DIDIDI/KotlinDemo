package com.dididi.kotlindemo

import android.app.Application
import android.content.Context


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 06/07/2020
 * @describe
 */

class App:Application(){

    companion object{
        lateinit var context:Context
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        context = baseContext
    }
}
