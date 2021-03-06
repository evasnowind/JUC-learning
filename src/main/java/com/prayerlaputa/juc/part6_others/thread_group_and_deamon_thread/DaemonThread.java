package com.prayerlaputa.juc.part6_others.thread_group_and_deamon_thread;

public class DaemonThread {
    
    public static void main(String[] args) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Thread t = Thread.currentThread();
                System.out.println("当前线程:" + t.getName());
            }
        };
        Thread thread = new Thread(task);
        thread.setName("test-thread-1");
        thread.setDaemon(false);
        thread.start();
    }
    
    
}
