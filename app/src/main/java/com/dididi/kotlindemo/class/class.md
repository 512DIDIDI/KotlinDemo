### 类与对象

* [类与接口](Class.kt)
  1. 抽象类
  2. 接口
  3. `open`关键字
  4. 构造器
  5. 属性`property`
* [拓展方法](ExtFunction.kt)
  1. 实现方式
  2. 调用
* [空类型安全](NullSafe.kt)
  1. `?`空类型判断
  2. `!!`不为空类型
  3. `?:` elvis语法
  4. 平台类型
  5. `is` 类型判断
  6. `as`/`as?` 强制类型转换
* [构造方法](Constructor.kt)
  1. 主构造器/副构造器
  2. 成员变量
  3. `init`代码块
  4. 构造同名工厂函数
* [延迟初始化](LazyInit.kt)
  1. 创建可空类型
  2. `lateinit var`
  3. `by lazy`
* [权限控制](Visibility.kt)
  1. `public` / `internal` / `protected` / `private`
* [接口代理与属性代理](Delegate.kt)
  1. 接口代理
  2. 属性代理
  3. `by Delegates.observable`  / `by Delegates.vetoable`
* [练习-使用属性代理读写config.properties文件](DelegateConfig.kt)
  1. 重载`setValue` / `getValue`
  2. 通过`ClassLoader`获取`Properties`键值对
* [练习-使用属性代理读写sharedPreferences文件](PreferenceDelegate.kt)
  1. 读写SP文件
  2. 使用泛型来存储和读写不同类型的属性
* [单例类](Singleton.kt)
  1. `object`顶级类创建单例模式
  2. `JvmStatic` / `JvmField` 注解使用场景
* [内部类](InnerClass.kt)
  1. 内部类 / 静态内部类  / 内部object / 匿名内部类 对外部类引用的区别和注意
  2. 本地类 / 本地函数
* [数据类](DataClass.kt)
  1. `component`属性
  2. `data class`的使用注意事项
  3. 数据类解构
* [枚举类](EnumClass.kt)
  1. 定义方式
  2. 接口实现
  3. 枚举类拓展函数
  4. `name`获取属性名 `ordinal`获取实例序号
  5. 枚举类在`when`语句的用法
  6. 枚举区间
* [密封类](SealedClass.kt)
  1. 密封类的定义
  2. 密封类在`when`中的使用
  3. 其他注意事项
* [内联类](InlineClass.kt)
  1. 内联类定义
  2. 内联类限制条件
  3. 内联类的优势
  4. 枚举的劣势 / 以及使用内联类来模拟枚举类应用场景
* [练习-递归整型列表](RecursiveIntList.kt)
  1. 递归的诸多练习
  2. 重载`component`方法以实现解构