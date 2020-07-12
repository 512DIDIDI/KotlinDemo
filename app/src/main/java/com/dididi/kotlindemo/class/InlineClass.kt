package com.dididi.kotlindemo.`class`


/**
 * @author dididi(叶超)
 * @email 512dididi@gmail.com
 * @since 09/07/2020
 * @describe 内联类
 * 可以理解为java的包装类 Integer相较于int
 * 编译器会尽可能使用被包装的类型进行优化(例如在调用处替换为被包装类型)
 * 内联类的限制，注意：
 * 1. 主构造器必须有且仅有一个只读属性
 * 2. 不能定义有backing-field的其他属性(不能有状态)
 * 3. 被包装类型必须不能是泛型
 * 4. 可以实现接口，但不能继承父类也不能被继承
 * 5. 内联类不能定义为其他类的内部类
 */

inline class BoxInt(val value: Int) : Comparable<Int> {

    /**
     * 内联类其属性成员也必须是只读，不能存储状态
     */
    val name: Int
        get() {
            return 0
        }

    operator fun inc(): BoxInt {
        return BoxInt(value + 1)
    }

    override fun toString(): String {
        return "BoxInt.value:$value"
    }

    override fun compareTo(other: Int): Int {
        return value.compareTo(other)
    }
}

/**
 * 由于枚举类会创建对象，因此会增加dex包大小与内存占用
 * 可以使用内联类来模拟枚举，优化枚举带来的性能损耗
 * 相当于本来需要使用IDLE，BUSY两个对象，现在优化为一个int属性来标记
 */
inline class InlineState(val ordinal: Int) {
    companion object {
        val IDLE = InlineState(0)
        val BUSY = InlineState(1)
    }

    fun values() = arrayOf(IDLE, BUSY)

    val name: String
        get() {
            return when (this) {
                IDLE -> "IDLE"
                BUSY -> "BUSY"
                else -> ""
            }
        }
}

fun main() {
    var boxInt = BoxInt(5)
    val newValue = boxInt.value * 200
    println(newValue)
    boxInt++
    println(boxInt)
    //上述代码，编译器会优化为(会将方法和构造函数编译为静态方法)：
    //      int boxInt = BoxInt.constructor-impl(5);
    //      int newValue = boxInt * 200;
    //      boolean var2 = false;
    //      System.out.println(newValue);
    //      boxInt = BoxInt.inc-impl(boxInt);
    //      BoxInt var4 = BoxInt.box-impl(boxInt);
    //      boolean var3 = false;
    //      System.out.println(var4);
}



