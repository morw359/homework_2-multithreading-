package ru.digitalhabbits.homework2.impl;

import ru.digitalhabbits.homework2.ICounter;
import ru.digitalhabbits.homework2.IMergeRunner;
import ru.digitalhabbits.homework2.util.ExceptionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MergerRunner extends Thread implements IMergeRunner {

    private final ICounter counter;
    private Map<Character, Long> finalCount;

    public MergerRunner(ICounter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        try {
            Map<Character, Long> finalCount = new HashMap<>();
            Map<Character, Long> singleCountResult;
            /**
             * берем, пока не натыкаемся на терминальный null
             */
            while ((singleCountResult = counter.take()) != null) {
                finalCount = merge(finalCount, singleCountResult);
            }
            this.finalCount = finalCount;
        } catch (InterruptedException e) {
            System.out.println("unexpected interruption " + ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public Map<Character, Long> merge(Map<Character, Long> first, Map<Character, Long> second) {
        return Stream.concat(first.entrySet().stream(), second.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Long::sum));

    }

    public Map<Character, Long> getFinalCount() {
        return new HashMap<>(finalCount);
    }
}
