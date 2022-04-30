package ru.digitalhabbits.homework2;

import java.util.Map;

public interface ICounter {
    Runnable createTask(String lines);
    Map<Character, Long> take() throws InterruptedException;
    void end();
}
