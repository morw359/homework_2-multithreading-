package ru.digitalhabbits.homework2.impl.factory;

import ru.digitalhabbits.homework2.IFileBatchReader;
import ru.digitalhabbits.homework2.impl.FileReaderBatchIterator;

import java.io.File;
import java.io.IOException;

public class FileReaderCreator {

    public static IFileBatchReader reader(File file, int batchSize) throws IOException {
        return new FileReaderBatchIterator(file, batchSize);
    }
}
