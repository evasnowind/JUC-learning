package com.prayerlaputa.juc.testvolatile.unknown_influence_for_volatile;

import java.util.concurrent.TimeUnit;

/**
 * @author chenglong.yu
 * created on 2020/10/19
 */
public class EffectiveJavaExample {
    private static boolean stopRequested;

    public static void main(String[] args) throws InterruptedException {
        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while(!stopRequested) {
                    i++;
                }
            }
        });
        backgroundThread.start();

        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;
    }
}
