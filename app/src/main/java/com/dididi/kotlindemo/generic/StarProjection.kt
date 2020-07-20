package com.dididi.kotlindemo.generic

import com.dididi.kotlindemo.expression.func


/**
 * @author dididi(叶超)
 * @email 512dididi@gmail.com
 * @since 20/07/2020
 * @describe 星投影
 * 星投影的投影规则，
 *      1.<协变点>取的是协变点的<上限>，也就是协变点的父类
 *      2.<逆变点>取得是逆变点的<下限>，而由于kotlin不能定义泛型下限，所以取得是[Nothing]
 * 星投影的适用范围：
 *      1.不能直接或间接应用在属性或函数上(例如 1.QueryMap<String,*>()  2.maxOf<*>(1,3) )
 *      2.适用于类型描述的场景(例如 1. val queryMap:QueryMap<*,*>  2. if(f is Function(*,*))  3. HashMap<String,List<*>>() )
 */

class QueryMap<out K:CharSequence,out V:Any>{
    fun getKey():K = TODO()
    fun getValue():V = TODO()
}

class Function<in P1,in P2>{
    fun invoke(p1:P1,p2:P2){}
}

fun main(){
    //星投影，1.可用于类型定义处
    val queryMap:QueryMap<*,*> = QueryMap<String,Int>()
    //星投影返回的是协变点的上限(也就是getKey返回的是CharSequence，getValue返回的是Any)
    queryMap.getKey()
    queryMap.getValue()
    //逆变点的星投影
    val function:Function<*,*> = Function<String,Int>()
    //取逆变点的下限，也就是[Nothing]，而由于[Nothing]无法实例化，所以也就不能调用
//    function.invoke()
    //星投影，2.可用于类型判断上
    if (function is Function<*,*>){

    }
    //星投影，3.可用于包装类型上，如HashMap<K,V>()，在这里的实例化中，K，V都能得到具体的类型，String和List，而List<E>中的泛型仍能使用*星投影
    val map = HashMap<String,List<*>>()
}