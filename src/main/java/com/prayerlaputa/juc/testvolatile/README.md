
参考Effective Java中第66条

Effective Java中定义：
多线性并发时，如果 A 线程修改了共享变量，此时 B 线程感知不到此共享变量的变化，叫做活性失败。

如何解决活性失败呢？

让两个线程之间对共享变量有 happens-before 关系，最常用的操作就是volatile 或 加锁。

书里说：这是可以接受的，这种优化称作提升（hoisting）。

对应到深入理解JVM，是JIT里的“循环表达式外提（Loop Expression Hoisting）”

在EffectiveJavaExample示例中：
一个没有被 volatile 修饰的变量 stopRequested ，在子线程和主线程中都有用到的时候，Java 内存模型只是不能保证后台线程何时“看到”主线程对 stopRequested 的值所做的改变，而不是永远看不见。

**加了 volatile，jvm 一定会保证 stopRequested 的可见性。**  
**不加 volatile，jvm 会尽量保证 stopRequested 的可见性。**


## 回头看

对于VolatileExample中示例代码的问题，可以结合上面内容做如下回答：
由于变量 flag 没有被 volatile 修饰，而且在子线程休眠的 100ms 中， while 循环的 flag 一直为 false，循环到一定次数后，触发了 jvm 的即时编译功能，进行循环表达式外提（Loop Expression Hoisting），导致形成死循环。而如果加了 volatile 去修饰 flag 变量，保证了 flag 的可见性，则不会进行提升。

