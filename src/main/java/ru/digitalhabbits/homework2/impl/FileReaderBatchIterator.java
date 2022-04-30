package ru.digitalhabbits.homework2.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.digitalhabbits.homework2.IFileBatchReader;
import ru.digitalhabbits.homework2.util.ExceptionUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

public class FileReaderBatchIterator implements IFileBatchReader {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final File file;
    private final int batchSize;
    private BufferedReader reader;
    private final Charset charset;
    private final char[] buffer;

    /**
     * @param batchSize - количество символов читаемых за раз
     */
    public FileReaderBatchIterator(File file, int batchSize, Charset charset) {
        this.file = file;
        this.batchSize = batchSize;
        this.charset = charset;
        this.buffer = new char[batchSize];
    }

    public String readBatch() {
        try {
            if (reader == null) {
                reader = new BufferedReader(new FileReader(file, charset), batchSize);
            }
            int read = reader.read(buffer);
            if (read < 1) return null;
            return new String(buffer, 0, read);
        } catch (IOException e) {
            System.out.println("file reading error: " + ExceptionUtils.getStackTrace(e));
            System.exit(1);
            return "";
        }
    }

    public void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                log.error(ExceptionUtils.getStackTrace(e));
            }
        }
    }
}
