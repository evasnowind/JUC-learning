package com.prayerlaputa.juc.part6_others.thread_group_and_deamon_thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author chenglong.yu
 * created on 2020/11/19
 */
public class ActiveThreadNum {

    public static void main(String[] args) {
        /*
        注意此处threadInfos、以及Thread.activeCount()，会依据程序所跑环境的不同，会包含不同数量的线程。
        比如，在Windows+IDEA中运行：
threadInfo:
[6]Monitor Ctrl-Break
[5]Attach Listener
[4]Signal Dispatcher
[3]Finalizer
[2]Reference Handler
[1]main

thread active count=2
current thread list:
java.lang.ThreadGroup[name=main,maxpri=10]
    Thread[main,5,main]
    Thread[Monitor Ctrl-Break,5,main]

        在windows命令行/windows git bash中运行都是：
threadInfo:
[5]Attach Listener
[4]Signal Dispatcher
[3]Finalizer
[2]Reference Handler
[1]main

thread active count=1
current thread list:
java.lang.ThreadGroup[name=main,maxpri=10]
    Thread[main,5,main]

        在centos 7命令行执行是：
[4]Signal Dispatcher
[3]Finalizer
[2]Reference Handler
[1]main

thread active count=1
current thread list:
java.lang.ThreadGroup[name=main,maxpri=10]
    Thread[main,5,main]

        所以如果要使用线程个数做条件判断时，一定要慎重。
         */

        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        System.out.println("threadInfo:");
        for (ThreadInfo info : threadInfos) {
            System.out.println("[" + info.getThreadId() + "]" + info.getThreadName());
        }

        System.out.println();
        System.out.println("thread active count=" + Thread.activeCount());
        System.out.println("current thread list:");
        Thread.currentThread().getThreadGroup().list();
    }
}
