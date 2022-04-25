package ru.digitalhabbits.homework2.impl.factory;

import ru.digitalhabbits.homework2.ICounter;
import ru.digitalhabbits.homework2.IMergeRunner;
import ru.digitalhabbits.homework2.impl.MergerRunner;

public class MergerCreator {
    public IMergeRunner mergerRunner(ICounter counter) {
        return new MergerRunner(counter);
    }
}
