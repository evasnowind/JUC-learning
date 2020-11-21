package com.prayerlaputa.juc.part6_others.test_synchronized_and_join;

import java.util.concurrent.TimeUnit;

/**
 * @author chenglong.yu
 * created on 2020/11/2
 */
public class Join {

    public static void main(String[] args) {
        Object oo = new Object();

        MyThread thread1 = new MyThread("thread1 -- ");
        thread1.setOo(oo);
        thread1.start();

        /*
        若此处的synchronized锁住的是oo对象，则输出如下、且程序无法结束：
        main -- 0
        main -- 1
        main -- 2
        main -- 3
        main -- 4
        main -- 5
        main -- 6
        main -- 7
        main -- 8
        main -- 9
        main -- 10
        main -- 11
        main -- 12
        main -- 13
        main -- 14
        main -- 15
        main -- 16
        main -- 17
        main -- 18
        main -- 19

        若是改为synchronized (thread1)，则可以正常执行完毕。
        原因在于：
        此处如果使用synchronized (oo)，即与MyThread使用同一把锁，那么一开始由于main先执行，锁住，导致thread1无法执行，
        接着，main线程执行到i=20时，main线程也由于调用join方法，在持有oo锁的情况下，挂起、等待thread1执行；
        然而thread1也在等待oo锁，也就无法正常执行，出现了死锁。

        当使用synchronized (thread1)时，就是main使用thread1锁，MyThread使用oo锁，两者互不影响。就会按照我们的预想执行。

         */
//        synchronized (oo) {
        synchronized (thread1) {
            for (int i = 0; i < 100; i++) {
                if (i == 20) {
                    try {
                        /*
                        此处使用Thread.join,程序意图是想在i=20时，主线程挂起、等子线程执行完。
                         */
                        thread1.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " -- " + i);
            }
        }
    }

}

class MyThread extends Thread {

    private String name;
    private Object oo;

    public void setOo(Object oo) {
        this.oo = oo;
    }

    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        synchronized (oo) {
            for (int i = 0; i < 100; i++) {
                System.out.println(name + i);
            }
        }
    }

}