package ru.digitalhabbits.homework2.impl.factory;

import ru.digitalhabbits.homework2.IFileBatchReader;
import ru.digitalhabbits.homework2.impl.FileReaderBatchIterator;

import java.io.File;
import java.nio.charset.Charset;

public class FileReaderCreator {

    public IFileBatchReader reader(File file, int batchSize, Charset charset) {
        return new FileReaderBatchIterator(file, batchSize, charset);
    }
}
