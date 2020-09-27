package com.prayerlaputa.juc.part2_sync.delayqueue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author chenglong.yu
 * created on 2020/9/27
 */
public class DelayedQueueTest {
    public static void main(String[] args) throws InterruptedException {
        Item item1 = new Item("item1", 5, TimeUnit.SECONDS);
        Item item2 = new Item("item2",10, TimeUnit.SECONDS);
        Item item3 = new Item("item3",15, TimeUnit.SECONDS);
        DelayQueue<Item> queue = new DelayQueue<>();
        //若queue为空时调用take，将一直阻塞
        queue.put(item1);
        queue.put(item2);
        queue.put(item3);
        System.out.println("begin time:" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        for (int i = 0; i < 3; i++) {
            /*
            若获取不到过期元素，将一直阻塞，直到有元素过期。
             */
            Item take = queue.take();
            System.out.format("name:{%s}, time:{%s}\n",take.name, LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        }
    }
}

class Item implements Delayed {
    /* 触发时间*/
    private long time;
    String name;

    public Item(String name, long time, TimeUnit unit) {
        this.name = name;
        this.time = System.currentTimeMillis() + (time > 0? unit.toMillis(time): 0);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        /*
        延迟队列DelayQueue内部需要调用
         */
        return time - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        Item item = (Item) o;
        long diff = this.time - item.time;
        if (diff <= 0) {
            /*
             改成>=会造成问题
             因为DelayQueue内部使用优先级队列PriorityQueue先将
             所有元素排序，排序规则就是依据这个compareTo。
             DelayQueue要求这个优先级队列需要按优先级（此处为执行先后）从小到大排列，
             若compareTo返回与DelayQueue定义不一致，将导致返回结果不能达到延迟队列目标。
             */
            return -1;
        }else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return "Item{" +
                "time=" + time +
                ", name='" + name + '\'' +
                '}';
    }

}