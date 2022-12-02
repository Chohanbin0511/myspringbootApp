package com.basic.myspringboot;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;

public class LambdaTest {

    @Test
    public void consumer() {
        //Immutable List
        List<String> strings = List.of("aa", "aacc", "rrr");

        // 1. Anonymous Inner class
        strings.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("s = " + s);
            }
        });

        // 2. Lambda Expression
        strings.forEach(val -> System.out.println(val));
        strings.forEach(System.out::println);
    }

    @Test @Disabled
    public void runnable() {
        // 1. Anonymous Inner class
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Annoymous Inner class");
            }
        });
        t1.start();
        // 2. Lambda Expression
        Thread t2 = new Thread(() -> System.out.println("Lambda Expression"));
        t2.start();
    }
}

/*
   class MyRunnable implements Runnable {
    run() {}
   }
   Thread t1 =new Thread(new MyRunnable());
 */
