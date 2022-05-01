package ru.digitalhabbits.homework2.impl;

import ru.digitalhabbits.homework2.FileLetterCounter;
import ru.digitalhabbits.homework2.ICounter;
import ru.digitalhabbits.homework2.IFileBatchReader;
import ru.digitalhabbits.homework2.IMergeRunner;
import ru.digitalhabbits.homework2.impl.factory.CounterCreator;
import ru.digitalhabbits.homework2.impl.factory.FileReaderCreator;
import ru.digitalhabbits.homework2.impl.factory.MergerCreator;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class AsyncFileLetterCounter implements FileLetterCounter {
    private static final int SYMBOL_NUMBER = 1584;
    private static final Charset DEFAULT_FILE_CHARSET = StandardCharsets.UTF_8;

    private final FileReaderCreator readerFactory;
    private final MergerCreator mergerCreator;
    private final CounterCreator counterCreator;

    public AsyncFileLetterCounter() {
        this.readerFactory = new FileReaderCreator();
        this.mergerCreator = new MergerCreator();
        this.counterCreator = new CounterCreator();
    }

    @Override
    public Map<Character, Long> count(File input) {
        try (IFileBatchReader fileReader = readerFactory.reader(input, SYMBOL_NUMBER, DEFAULT_FILE_CHARSET);
             ICounter counter = counterCreator.counter(Runtime.getRuntime().availableProcessors() - 1)) {
            //будет складывать суммы по отдельным кускам файла в отдельном потоке
            IMergeRunner merger = mergerCreator.mergerRunner(counter);
            merger.start();
            String largeString;
            while ((largeString = fileReader.readBatch()) != null) { //todo ask
                counter.count(largeString);
            }
            //дожидаемся counter
            counter.endCountProcess();
            //дожидаемся merger
            merger.join();
            return merger.getFinalCount();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
