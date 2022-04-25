package ru.digitalhabbits.homework2;

/**
 * читает файл по кускам
 */
public interface IFileBatchReader extends AutoCloseable {
    String readBatch();
}
