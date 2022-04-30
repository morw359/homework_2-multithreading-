package ru.digitalhabbits.homework2.impl.counter;

import ru.digitalhabbits.homework2.ICounter;
import ru.digitalhabbits.homework2.impl.ForkCounter;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Counter implements ICounter {
    private final int countFork;
    final BlockingQueue<Map<Character, Long>> countResult;

    public Counter(int countFork) {
        this.countResult = new LinkedBlockingQueue<>();
        this.countFork = countFork;
    }

    public List<Runnable> createTasks(String lines) {
        int size = lines.length();
        if (size == 0) return Collections.emptyList();
        int singleForkRNumber = size / countFork;
        if (singleForkRNumber == 0) {
            return List.of(new ForkCounter(countResult, 0, lines.length(), lines));
        }
        List<Runnable> res = new ArrayList<>();
        int beginInd = 0;
        for (int i = 1; i <= countFork - 1; i++) {
            res.add(new ForkCounter(countResult, beginInd, beginInd + singleForkRNumber, lines));
            beginInd += singleForkRNumber;
        }
        res.add(new ForkCounter(countResult, beginInd, lines.length(), lines));
        return res;
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
