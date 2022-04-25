package ru.digitalhabbits.homework2.impl.factory;

import ru.digitalhabbits.homework2.ICounter;
import ru.digitalhabbits.homework2.impl.Counter;

public class CounterCreator {
    public ICounter counter() {
        return new Counter();
    }
}
