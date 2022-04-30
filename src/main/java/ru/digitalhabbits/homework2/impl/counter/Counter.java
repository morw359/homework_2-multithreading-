package ru.digitalhabbits.homework2.impl.counter;

import ru.digitalhabbits.homework2.ICounter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Counter implements ICounter {
    final BlockingQueue<Map<Character, Long>> countResult;
    private final static int a = 'a';
    private final static int z = 'z';

    public Counter() {
        this.countResult = new LinkedBlockingQueue<>();
    }

    public Runnable createTask(String lines) {
        return () -> {
            Map<Character, Long> res = new HashMap<>();
            for (int i = 0; i < lines.length(); i++) {
                char c = lines.charAt(i);
                //если не [a-z]
                if (c < a || c > z) {
                    continue;
                }
                res.compute(c, (character, val) -> val == null ? 1L : ++val);
            }
            countResult.add(res);
        };
    }

    public Map<Character, Long> take() throws InterruptedException {
        Map<Character, Long> take = countResult.take();
        if (take.get('\u0000') != null) {
            return null;
        } else return take;
    }

    public void end() {
        Map<Character, Long> terminalElement = new HashMap<>();
        terminalElement.put('\u0000', 1L);
        countResult.add(terminalElement);
    }
}
