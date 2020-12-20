package com.prayerlaputa.juc.testvolatile.unknown_influence_for_volatile;

import java.util.concurrent.TimeUnit;

/**
 * @author chenglong.yu
 * created on 2020/10/19
 */
public class VolatileExample3 {
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
            try {
                /*
                修改：添加sleep
                sleep 会导致内存的刷新操作
                 */
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("程序结束,i=" + i);
    }
}
