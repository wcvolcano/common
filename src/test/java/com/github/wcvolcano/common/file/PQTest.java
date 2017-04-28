package com.github.wcvolcano.common.file;

import java.util.PriorityQueue;

/**
 * Created by canwen on 2017/4/6.
 */
public class PQTest {
    public static void main(String[] args) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i < 10; ++i) queue.add(i);
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }
}
