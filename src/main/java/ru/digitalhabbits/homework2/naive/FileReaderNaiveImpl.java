package ru.digitalhabbits.homework2.naive;

import ru.digitalhabbits.homework2.FileReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

/**
 * Sequential file reader
 */
public class FileReaderNaiveImpl implements FileReader {

    public Stream<String> readLines(File file) {
        try {
            return Files.lines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
