package com.prayerlaputa.juc.part6_others.thread_group_and_deamon_thread;

import java.util.concurrent.Callable;

public class ThreadC implements Callable<String> {

    @Override
    public String call() throws Exception {
        Thread.sleep(500);
        System.out.println("这是线程C");
        return "线程C";
    }



}
