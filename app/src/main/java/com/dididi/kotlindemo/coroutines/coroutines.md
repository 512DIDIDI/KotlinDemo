### 协程
* **概念**

  > * 我们所有的代码都是跑在线程中的，而线程是跑在进程中的，而协程是跑在线程中的。
  >
  > 协程是基于Thread封装的工具包，是为了更方便的进行多线程开发，消除开发过程中许多的回调，多线程中的代码宛如单线程写法，用看起来线程阻塞的写法写出非阻塞式效果。

* **用法**

  1. [协程的性能](Performance.kt)
  2. [协程的创建方式](CreateCoroutines.kt)
  3. [协程的上下文与切换](SwitchCoroutines.kt)
  4. [挂起函数](SuspendFuction.kt)
  5. [取消与超时](CancelAndTimeout.kt)
  6. [并发任务](Async.kt)
  7. [简单的实战](MainActivity.kt)

