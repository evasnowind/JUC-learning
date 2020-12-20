package com.prayerlaputa.juc.testvolatile.unknown_influence_for_volatile;

import java.util.concurrent.TimeUnit;

/**
 * @author chenglong.yu
 * created on 2020/10/19
 */
public class VolatileExample2 {
    private static boolean flag = false;
    private static int i = 0;
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                flag = true;
                System.out.println("flag 被修改成 true");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        while (!flag) {
            i++;
            /*
            修改:添加System.out.println
            println方法实现中包含synchronized关键字，while循环调用，

            将会发生锁粗化，相当于synchronized将while()包起来，因此flag是线程安全、能正常结束。
            ？？？上面这个锁粗化说法有问题？？？？
             */
            System.out.println("flag标识=" + flag);
        }
        System.out.println("程序结束,i=" + i);
    }
}
