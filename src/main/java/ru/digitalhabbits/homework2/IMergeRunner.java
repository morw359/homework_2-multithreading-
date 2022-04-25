package ru.digitalhabbits.homework2;

import java.util.Map;

public interface IMergeRunner extends Runnable, LetterCountMerger {
    Map<Character, Long> getFinalCount();
    void start();
    void join() throws InterruptedException;
}
