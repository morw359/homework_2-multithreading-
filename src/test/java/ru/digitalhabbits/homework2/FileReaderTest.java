package ru.digitalhabbits.homework2;

import org.junit.jupiter.api.Test;
import ru.digitalhabbits.homework2.impl.FileReaderBatchIterator;

import java.io.File;
import java.io.IOException;

import static com.google.common.io.Resources.getResource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileReaderTest {

    @Test
    void testReadWithDifferentBufferSizes() throws IOException {
        File file = getFile("testFileReader1.txt");
        try (FileReaderBatchIterator reader = new FileReaderBatchIterator(file, 1)) {
            String charLine;
            StringBuilder res = new StringBuilder();
            while (reader.next()) {
                charLine = reader.readBatch();
                assertEquals(1, charLine.length());
                res.append(charLine);
            }
            String finalRead = res.toString();
            assertTrue(finalRead.contains("cdccfdbfeadebaee"));
            assertTrue(finalRead.contains("bdacffcecdaaafdc"));
            assertTrue(finalRead.contains("fdbdefbececbcbca"));
        }

        try (FileReaderBatchIterator reader = new FileReaderBatchIterator(file, 200)) {
            String charLine;
            StringBuilder res = new StringBuilder();
            while (reader.next()) {
                charLine = reader.readBatch();
                res.append(charLine);
            }
            String finalRead = res.toString();
            assertTrue(finalRead.contains("cdccfdbfeadebaee"));
            assertTrue(finalRead.contains("bdacffcecdaaafdc"));
            assertTrue(finalRead.contains("fdbdefbececbcbca"));
        }

    }

    private File getFile(String name) {
        return new File(getResource(name).getPath());
    }
}
