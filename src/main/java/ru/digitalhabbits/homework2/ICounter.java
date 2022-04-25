package ru.digitalhabbits.homework2;

import java.util.List;
import java.util.Map;

public interface ICounter {
    List<Runnable> createTasks(String lines);
    Map<Character, Long> take() throws InterruptedException;
    void end();
}
