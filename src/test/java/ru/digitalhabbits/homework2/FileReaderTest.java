package ru.digitalhabbits.homework2;

import org.junit.jupiter.api.Test;
import ru.digitalhabbits.homework2.impl.FileReaderBatchIterator;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static com.google.common.io.Resources.getResource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileReaderTest {
    private final static Charset utf8 = StandardCharsets.UTF_8;

    @Test
    void testReadWithDifferentBufferSizes() {
        File file = getFile("testFileReader1.txt");
        try (FileReaderBatchIterator reader = new FileReaderBatchIterator(file, 1, utf8)) {
            String charLine;
            StringBuilder res = new StringBuilder();
            while ((charLine = reader.readBatch()) != null) {
                assertEquals(1, charLine.length());
                res.append(charLine);
            }
            String finalRead = res.toString();
            assertTrue(finalRead.contains("cdccfdbfeadebaee"));
            assertTrue(finalRead.contains("bdacffcecdaaafdc"));
            assertTrue(finalRead.contains("fdbdefbececbcbca"));
        }

        try (FileReaderBatchIterator reader = new FileReaderBatchIterator(file, 200, utf8)) {
            String charLine;
            StringBuilder res = new StringBuilder();
            while ((charLine = reader.readBatch()) != null) {
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
