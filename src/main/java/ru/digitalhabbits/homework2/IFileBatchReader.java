package ru.digitalhabbits.homework2;

public interface IFileBatchReader extends AutoCloseable {
    String readBatch();
}
