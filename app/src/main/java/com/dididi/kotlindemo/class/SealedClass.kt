package com.dididi.kotlindemo.`class`


/**
 * @author dididi(叶超)
 * @email 512dididi@gmail.com
 * @since 09/07/2020
 * @describe 密封类
 * 有何用处：因为密封类的子类是可数的，所以可以看作枚举类的另一种方式，
 *          只不过枚举类是实例化的值，而密封类是类的类型作为可数参数
 *          不光是继承它的子类可数，他的内部类也是可数的
 */

/**
 * 注意：密封类是抽象类
 * 并且密封类及其子类仅能在同一个文件下
 */
sealed class PlayState{
    //它的构造函数是private，所以仅能在该文件内继承它
    constructor()
}

object Idle :PlayState()

class Playing(private val name:String):PlayState(){
    fun play(){
        println("$name is playing")
    }
    fun stop(){
        println("$name is stop")
    }
}

class Error(private val errorInfo:String):PlayState(){
    fun recover(){
        println("$errorInfo recovering")
    }
}

class Player{
    var state:PlayState = Idle
    fun playSong(name:String){
        //与枚举类对比，枚举类是值可数，而密封类是类型可数
        state = when(val state = this.state){
            //如果state是object对象
            Idle ->{
                Playing(name).also(Playing::play)
            }
            //如果state是Playing类型
            is Playing ->{
                //类型推导
                state.stop()
                Playing(name).also(Playing::play)
            }
            //如果state是Error类型
            is Error ->{
                state.recover()
                Playing(name).also(Playing::play)
            }
        }
    }
}

fun main(){
    val player = Player()
    player.playSong("Common people")
    player.playSong("Made of stone")
}
