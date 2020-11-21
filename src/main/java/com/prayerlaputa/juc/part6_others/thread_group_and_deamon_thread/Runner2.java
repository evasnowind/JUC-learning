package com.prayerlaputa.juc.part6_others.thread_group_and_deamon_thread;

public class Runner2 implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("进入Runner2运行状态——————————" + i);
        }

        boolean result = Thread.currentThread().isInterrupted();
        // 重置状态
        boolean result1 = Thread.interrupted();
        boolean result3 = Thread.currentThread().isInterrupted();

        System.out.println("Runner2.run Thread.currentThread().isInterrupted() ===>" + result);
        System.out.println("Runner2.run Thread.interrupted() ===>" + result1);
        System.out.println("Runner2.run Thread.currentThread().isInterrupted() ===>" + result3);
        
        
    }
}
