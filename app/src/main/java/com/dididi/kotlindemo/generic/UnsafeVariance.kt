package com.dididi.kotlindemo.generic


/**
 * @author dididi(叶超)
 * @email 512dididi@gmail.com
 * @since 20/07/2020
 * @describe 违反型变约束
 * 违反型变约束：
 *      1.声明为<协变>的类出现<逆变点>或者声明为<逆变>的类出现<协变点>(如[ListU]中的[contains])
 *      2.声明为<不变>的类接收<逆变>或<协变>的类型参数(如[DustbinU]中的[list])
 * 安全前提：
 *      1.泛型参数<协变>，<逆变点>不能引起修改，始终保证<只读不写>(如 [ListU] 中定义的[contains]方法，虽然传入了逆变点，但没有修改内部的值，只是用来做比较)
 *      2.泛型参数<逆变>，<协变点>不能被外部获取，始终保证<只写不读>(如 [DustbinU] 中只能定义[put]，不能定义[get]方法)
 * 总结：也就是虽然你可以声明违反型变约束的<逆变点><协变点>，但仍然需要遵循<协变只读不写><逆变只写不读>
 */

sealed class ListU<out T> {
    object Nil : ListU<Nothing>() {
        override fun toString(): String {
            return "Nil"
        }
    }

    /**注意，这里的[head]也是<协变点>，因为他是成员属性，有[getter]方法*/
    data class Cons<T>(val head: T, val tail: ListU<T>) : ListU<T>() {
        override fun toString(): String {
            return "$head,$tail"
        }
    }

    fun joinToString(sep: Char = ' '): String {
        return when (this) {
            is Nil -> "Nil"
            is Cons -> "$head$sep${tail.joinToString(sep)}"
        }
    }

    /**
     * 泛型协变中出现逆变点，(函数参数类型为泛型类型)
     * 违反了泛型约束，如果必须要使用，需要加上 [@UnsafeVariance] 注解声明逆变点
     */
    operator fun contains(t: @UnsafeVariance T): Boolean {
        fun containsInner(list: ListU<T>, t: T): Boolean = when (list) {
            Nil -> false
            is Cons -> if (list.head == t) true else containsInner(list.tail, t)
        }
        return containsInner(this, t)
    }
}

class DustbinU<in T>{

    /**MutableList<T>接收的是不变的泛型参数，但传入了一个逆变的泛型参数，也是违反型变约束的*/
    private val list = mutableListOf<T>()

    fun put(t: T) {
        list+=t
    }
}

fun main(){
    val map = mapOf("a" to 1)
    //这里defaultValue也是一个逆变点，但map的V其实是协变的
    val num = map.getOrDefault("b",0.1)
    println(num)
    val dustbin = DustbinU<Int>()
    dustbin.put(2)
}