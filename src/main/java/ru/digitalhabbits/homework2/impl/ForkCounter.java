package ru.digitalhabbits.homework2.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class ForkCounter implements Runnable {
    private final int begin;
    private final int end;
    private final String lines;
    private final static int a = 'a';
    private final static int z = 'z';
    private final BlockingQueue<Map<Character, Long>> counts;

    public ForkCounter(BlockingQueue<Map<Character, Long>> counts, int begin, int end, String lines) {
        this.counts = counts;
        this.begin = begin;
        this.end = end;
        this.lines = lines;
    }

    public void run() {
        Map<Character, Long> res = new HashMap<>();

        for (int i = begin; i < end; i++) {
            char c = lines.charAt(i);
            //если не [a-z]
            if (c < a || c > z) {
                continue;
            }
            res.compute(c, (character, val) -> val == null ? 1L : ++val);
        }
        counts.add(res);
    }
}
