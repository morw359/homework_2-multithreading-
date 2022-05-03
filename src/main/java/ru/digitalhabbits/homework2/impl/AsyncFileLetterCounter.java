package ru.digitalhabbits.homework2.impl;

import ru.digitalhabbits.homework2.FileLetterCounter;
import ru.digitalhabbits.homework2.ICounter;
import ru.digitalhabbits.homework2.IFileBatchReader;
import ru.digitalhabbits.homework2.IMergeRunner;
import ru.digitalhabbits.homework2.impl.factory.CounterCreator;
import ru.digitalhabbits.homework2.impl.factory.FileReaderCreator;
import ru.digitalhabbits.homework2.impl.factory.MergerCreator;

import java.io.File;
import java.util.Map;

public class AsyncFileLetterCounter implements FileLetterCounter {
    private static final int SYMBOL_NUMBER = 1584;

    public AsyncFileLetterCounter() {
    }

    @Override
    public Map<Character, Long> count(File input) {
        try (ICounter counter = CounterCreator.counter(Runtime.getRuntime().availableProcessors() - 1);
             IFileBatchReader fileReader = FileReaderCreator.reader(input, SYMBOL_NUMBER)) {
            //будет складывать суммы по отдельным кускам файла в отдельном потоке
            IMergeRunner merger = MergerCreator.mergerRunner(counter);
            merger.start();
            String largeString;
            while (fileReader.next()) {
                largeString = fileReader.readBatch();
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
