package com.prayerlaputa.juc.part6_others.test_switch;

/**
 * @author chenglong.yu
 * created on 2020/10/13
 */
public class TestSwitch {
    public static void main(String[] args) {
        //当default在中间时,且看输出是什么？
        /*
        第一个switch，输出如下：

        print 1
        first default print
        print 3
        无非就是对于break的考察
         */
        int a = 1;
        switch (a) {
            case 2:
                System.out.println("print 2");
            case 1:
                System.out.println("print 1");
            default:
                System.out.println("first default print");
            case 3:
                System.out.println("print 3");
        }

        //当switch括号内的变量为String类型的外部参数时,且看输出是什么？
        /*
        java规范中规定，switch会先计算出switch(expr)表达式的值，若为null就会抛出NPE
         */
        String param = null;
        switch (param) {
            case "param":
                System.out.println("print param");
                break;
            case "String":
                System.out.println("print String");
                break;
            case "null":
                System.out.println("print null");
                break;
            default:
                System.out.println("second default print");
        }
    }
}
