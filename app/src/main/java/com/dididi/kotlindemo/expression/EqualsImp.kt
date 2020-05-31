package com.dididi.kotlindemo.expression


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 31/05/2020
 * @describe equals hashCode实现
 */

fun main() {
    val set = HashSet<Person>()
    repeat(5) {
        //可以用+=代替add
        set += Person(23, "dididi")
    }
    println(set.joinToString())
    val person = Person(20, "dididi")
    set += person
    println(set.joinToString())
//    //直接更改已存在hashmap或hashset中的值，会导致对象的hashCode改变，最终导致map或set无法移除对象
//    person.age++
//    set -= person
//    //这里打印出来仍是2，移除不了person对象
//    println(set.joinToString())
    //解决方法，定义的data类或者key对象，设置其为不可变
    //创建新对象来改变set中的值
    val person2 = Person(person.age + 1, "dididi")
    set -= person
    set += person2
    println(set.joinToString())
}

class Person(val age: Int, val name: String) {
    override fun equals(other: Any?): Boolean {
        val other2 = other as? Person ?: return false
        return other2.age == this.age && other2.name == this.name
    }


    override fun hashCode(): Int {
        return 1 + age * 7 + 17 * name.hashCode()
    }

    override fun toString(): String {
        return "age:$age name:$name"
    }
}