package com.prayerlaputa.juc.part6_others.thread_group_and_deamon_thread;

public class Runner1 implements Runnable {
   
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("进入Runner1运行状态——————————" + i);
        }
    }
}
