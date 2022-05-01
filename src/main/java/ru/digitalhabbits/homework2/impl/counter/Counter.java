package ru.digitalhabbits.homework2.impl.counter;

import ru.digitalhabbits.homework2.ICounter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Counter implements ICounter {
    private static final int COUNTERS_NUMBER = Runtime.getRuntime().availableProcessors() - 1;
    final BlockingQueue<Map<Character, Long>> countResult;
    final ExecutorService counterWorkers;

    public Counter() {
        this.countResult = new LinkedBlockingQueue<>();
        this.counterWorkers = Executors.newFixedThreadPool(COUNTERS_NUMBER);
    }

    public Counter(int threadsNumber) {
        this.countResult = new LinkedBlockingQueue<>();
        this.counterWorkers = Executors.newFixedThreadPool(threadsNumber);
    }

    public Runnable createTask(String lines) {
        return () -> {
            Map<Character, Long> res = new HashMap<>();
            for (int i = 0; i < lines.length(); i++) {
                char c = lines.charAt(i);
                //если не [a-z]
                if (c < 'a' || c > 'z') {
                    continue;
                }
                res.compute(c, (character, val) -> val == null ? 1L : ++val);
            }
            countResult.add(res);
        };
    }

    @Override
    public void count(String lines) {
        Runnable task = createTask(lines);
        this.counterWorkers.execute(task);
    }

    @Override
    public void endCountProcess() throws InterruptedException {
        counterWorkers.shutdown();
        assert counterWorkers.awaitTermination(1, TimeUnit.DAYS); //todo ask
        end(); //todo ask
    }

    @Override
    public void close() throws Exception {
        counterWorkers.shutdown();
    }

    public Map<Character, Long> take() throws InterruptedException {
        Map<Character, Long> take = countResult.take();
        if (take.get('\u0000') != null) {
            return null;
        } else return take;
    }

    private void end() {
        Map<Character, Long> terminalElement = new HashMap<>();
        terminalElement.put('\u0000', 1L);
        countResult.add(terminalElement);
    }
}
