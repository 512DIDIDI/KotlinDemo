### 函数

* [高阶函数](Lambda.kt)
  1. 定义
  2. 高阶函数的调用与简化
  3. 高阶函数作为参数和返回值的写法
* [内联函数](Inline.kt)
  1. 内联函数定义
  2. 内联的优缺点/适用地/过程/性能与限制
  3. `crossinline`/`noinline`的使用场景
  4. 内联属性
* [常用的高阶函数](MyApply.kt)
  1. `apply` / `also` / `let` / `run` 的使用场景与实现
  2. `use` 用来处理异常
  3. 文件流读写
* [集合变换的高阶函数](CollectFunction.kt)
  1. `iterable`与`sequence`调用相同拓展函数的区别
  2. `filter` / `map` / `flatMap` / `fold` / `reduce` / `zip` 的使用场景与描述
* [SAM转换](SAM.kt)
  1. SAM转换目前仅支持java方法+java接口(1.3.xx版本)
  2. 在kotlin中写SAM，应写成高阶函数方式，避免通过接口传值
  3. SAM使用时移除添加接口的注意点
* [练习-统计文件中字符个数](CountCharNumber.kt)
  1. `readText` / `filterNot` / `groupBy` 使用场景与描述
* [练习-实现html DSL](HtmlDsl.kt)
  1. DSL的概念
  2. 高阶函数与拓展函数的实践
  3. 两个receive的拓展函数如何实现
