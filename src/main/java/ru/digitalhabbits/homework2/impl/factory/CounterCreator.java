package ru.digitalhabbits.homework2.impl.factory;

import ru.digitalhabbits.homework2.ICounter;
import ru.digitalhabbits.homework2.impl.counter.Counter;

public class CounterCreator {
    public static ICounter counter(int threadNumber) {
        return new Counter(threadNumber);
    }
}
