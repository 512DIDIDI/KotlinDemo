package com.dididi.kotlindemo.`class`


/**
 * @author dididi(叶超)
 * @email 512dididi@gmail.com
 * @since 08/07/2020
 * @describe 数据类
 */

/**
 * 1.实际data class中的属性是一个component
 * 2.data class不能被继承，他是一个final类
 * 3.修饰data class中的属性最好使用val，否则有set方法修改对象后，改变hashCode值，容易造成Map或Set找不到原始的数据，无法移除对象
 * 4.编译器会自动生成toString/hashCode/equals/copy等方法
 */
data class Pet(val name:String,val age:Int)
//   @NotNull
//   public final String component1() {
//      return this.name;
//   }
//
//   public final int component2() {
//      return this.age;
//   }

fun test(){
    val pet = Pet("dog",2)
    //数据类解构，实际name就是component1,age就是component2
    val (name,age) = pet
    //实际上反编译 数据类结构就是如下
    val name1 = pet.component1()
    var age1 = pet.component2()
    println("name$name,age$age")
}