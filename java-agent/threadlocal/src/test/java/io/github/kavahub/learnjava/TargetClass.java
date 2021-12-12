package io.github.kavahub.learnjava;

import java.util.Random;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class TargetClass {
    public static void main(String[] args) {

        //线程一
        new Thread(() -> new TargetClass().method1()).start();

        //线程二
        new Thread(() -> {
            new TargetClass().method1();
        }).start();
    }


    public void method1() {
        try {
            Thread.sleep((new Random()).nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        method2();
    }

    public void method2() {
        try {
            Thread.sleep((new Random()).nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        method3();

        try {
            Thread.sleep((new Random()).nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void method3() {
        try {
            Thread.sleep((new Random()).nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
