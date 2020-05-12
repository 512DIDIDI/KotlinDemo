package com.dididi.kotlindemo


/**
 * @author dididi(yechao)
 * @since 15/01/2019
 * @describe 属性与字段
 */

class PropertyField {
    var size = 0
        //调用set()方法时会调用它
        set(value) {
            if (value > 10)
            //field(幕后字段)指代的是当前属性，即size，如果不使用幕后字段，将无法改变size的值
                field = value
        }
    //自定义属性访问器，调用get()方法时会调用他
    val isEmpty: Boolean
        get() = this.size == 0
    var privateSetProperty = 0
        //设置set方法可见性为private get方法仍为public
        private set
    //幕后属性提供了一种方法，即外部只能访问backingProperty获取该属性值，但不能写入，
    //内部则可以任意读写，通过_backingProperty
    private var _backingProperty = 0
    val backingProperty: Int
        get() = _backingProperty
    //lateinit var 用于延迟初始化，即不想定义一个可空类型的属性，但又不想编译器检查时报错，
    //可定义一个延迟初始化的变量，告诉编译器我稍后会将它初始化
    private lateinit var lateinitProperty:List<Int>
    private var listProperty:List<Int>
    fun initList(){
        lateinitProperty = ArrayList()
    }
    //检查是否已初始化(通过类名反射属性名检查)
    fun checkInit() = this::lateinitProperty.isInitialized
    //如果是在构造函数内初始化，可以不用加lateinit var
    constructor(list:List<Int>){
        listProperty = list
    }
}