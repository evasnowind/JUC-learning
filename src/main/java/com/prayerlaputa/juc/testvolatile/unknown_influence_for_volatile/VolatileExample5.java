package com.prayerlaputa.juc.testvolatile.unknown_influence_for_volatile;

import java.util.concurrent.TimeUnit;

/**
 * @author chenglong.yu
 * created on 2020/10/19
 */
public class VolatileExample5 {

    private static boolean flag = false;
    private static Integer i = 0;
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
        }
        System.out.println("程序结束,i=" + i);
    }
}
