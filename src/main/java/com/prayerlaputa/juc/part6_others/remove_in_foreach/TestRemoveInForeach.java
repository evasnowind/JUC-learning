package com.prayerlaputa.juc.part6_others.remove_in_foreach;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author chenglong.yu
 * created on 2020/10/13
 */
public class TestRemoveInForeach {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("abc");
        list.add("123");
        list.add("alpha");

//        for (String str : list) {
//            if ("abc".equals(str)) {
//                list.remove(str);
//            }
//        }
//
//        System.out.println(list);

        list = new CopyOnWriteArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        for (String str : list) {
            list.remove(str);
        }
        System.out.println(list);
    }
}
