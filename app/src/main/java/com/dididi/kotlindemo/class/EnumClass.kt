package com.dididi.kotlindemo.`class`


/**
 * @author dididi(叶超)
 * @email 512dididi@gmail.com
 * @since 08/07/2020
 * @describe 枚举类
 */

/**
 * 普通枚举类
 */
enum class State {
    IDLE, BUSY
}

/**
 * 定义有构造器的枚举类
 */
enum class Color(val id: Int) {
    RED(0),ORANGE(1),YELLOW(2),GREEN(3),CYAN(4),BLUE(5),PURPLE(6)
}

/**
 * 为枚举实现接口
 */
enum class StateRunnable : Runnable {
    IDLE,
    BUSY {
        /**
         * 可以为单独的枚举实例接口实现
         */
        override fun run() {
            println("busy state")
        }
    };

    /**
     * 可以实现枚举类内通用接口实现
     */
    override fun run() {
        println("common state")
    }
}

/**
 * 可以为枚举类定义拓展函数
 */
fun State.next(): State {
    return State.values().let {
        val nextOrdinal = (ordinal + 1) % it.size
        it[nextOrdinal]
    }
}

fun enumTest(){
    val state = State.IDLE
    //获取枚举属性名
    state.name
    //获取枚举实例的序号
    state.ordinal
    //分支表达式可使用枚举类，并且不需要else语句，因为枚举类是可以知道个数的
    when(state){
        State.IDLE -> {}
        State.BUSY -> {}
    }
    //可以使用枚举类实例，创建枚举区间，按照ordinal排序
    val colorRange = Color.RED .. Color.YELLOW
    val color = Color.BLUE
    if (color !in colorRange){
        println("not in color range")
    }
}