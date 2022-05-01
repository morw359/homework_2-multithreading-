package ru.digitalhabbits.homework2;

import java.util.Map;

public interface ICounter extends AutoCloseable {
    Runnable createTask(String lines);
    Map<Character, Long> take() throws InterruptedException;
    void count(String lines);
    void endCountProcess() throws InterruptedException;
}
