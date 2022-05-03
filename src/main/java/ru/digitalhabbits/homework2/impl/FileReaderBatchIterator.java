package ru.digitalhabbits.homework2.impl;

import ru.digitalhabbits.homework2.IFileBatchReader;
import ru.digitalhabbits.homework2.util.ExceptionUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileReaderBatchIterator implements IFileBatchReader {
    private BufferedReader reader;
    private final char[] buffer;
    private int read;

    /**
     * @param batchSize - количество символов читаемых за раз
     */
    public FileReaderBatchIterator(File file, int batchSize) throws IOException {
        this.buffer = new char[batchSize];
        this.reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8), batchSize);
    }

    public boolean next() {
        try {
            this.read = reader.read(buffer);
            return read >= 1;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readBatch() {
        return new String(buffer, 0, read);
    }

    public void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println(ExceptionUtils.getStackTrace(e));
            }
        }
    }
}
