package com.prayerlaputa.juc.part5_cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author chenglong.yu
 * created on 2020/12/20
 */
public class TestABA {


    private static AtomicInteger atomicInt = new AtomicInteger(100);
    private static AtomicStampedReference<Integer> atomicStampedRef =
            new AtomicStampedReference<Integer>(100, 0);

    public static void main(String[] args) throws InterruptedException {
        Thread intT1 = new Thread(new Runnable() {
            @Override
            public void run() {
                atomicInt.compareAndSet(100, 101);
                atomicInt.compareAndSet(101, 100);
            }
        });

        Thread intT2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /*
                    * 睡眠了1s，此时intT1已执行完，经历了：
                    * 100 -> 101 -> 100这个过程。
                    * 但由于只使用了AtomicInteger，此时intT2再进行CAS
                    * 是可以成功的。
                    * */
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean c3 = atomicInt.compareAndSet(100, 101);
                System.out.println(c3);        //true
            }
        });

        intT1.start();
        intT2.start();
        intT1.join();
        intT2.join();

        Thread refT1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                atomicStampedRef.compareAndSet(100, 101,
                        atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
                atomicStampedRef.compareAndSet(101, 100,
                        atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
            }
        });

        Thread refT2 = new Thread(new Runnable() {
            @Override
            public void run() {
                /*
                * refT2先获取stamp，然后看睡一下，保证refT1先执行完。
                * refT1做了 100 -> 101 -> 100操作，但此处使用了AtomicStampedReference
                * 该类实际上就是使用乐观锁中采用的加版本号的方式，每次更新时
                * 自己维护一个版本号，以避免ABA问题。
                * */
                int stamp = atomicStampedRef.getStamp();
                System.out.println("before sleep : stamp = " + stamp);    // stamp = 0
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("after sleep : stamp = " + atomicStampedRef.getStamp());//stamp = 1
                boolean c3 = atomicStampedRef.compareAndSet(100, 101, stamp, stamp + 1);
                System.out.println(c3);        //false
            }
        });

        refT1.start();
        refT2.start();
    }
}
