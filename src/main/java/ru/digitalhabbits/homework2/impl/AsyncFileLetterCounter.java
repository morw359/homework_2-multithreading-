package ru.digitalhabbits.homework2.impl;

import ru.digitalhabbits.homework2.FileLetterCounter;
import ru.digitalhabbits.homework2.ICounter;
import ru.digitalhabbits.homework2.IFileBatchReader;
import ru.digitalhabbits.homework2.IMergeRunner;
import ru.digitalhabbits.homework2.impl.factory.CounterCreator;
import ru.digitalhabbits.homework2.impl.factory.FileReaderCreator;
import ru.digitalhabbits.homework2.impl.factory.MergerCreator;
import ru.digitalhabbits.homework2.util.ExceptionUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsyncFileLetterCounter implements FileLetterCounter {
    private static final int SYMBOL_NUMBER = 1584;
    private static final int COUNTERS_NUMBER = Runtime.getRuntime().availableProcessors() - 1;
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
        ExecutorService counterWorkers = Executors.newFixedThreadPool(COUNTERS_NUMBER);
        ICounter counter = counterCreator.counter();
        try (IFileBatchReader fileReader = readerFactory.reader(input, SYMBOL_NUMBER, DEFAULT_FILE_CHARSET)) {
            //будет складывать суммы по отдельным кускам файла в отдельном потоке
            IMergeRunner merger = mergerCreator.mergerRunner(counter);
            merger.start();
            String largeString;
            while ((largeString = fileReader.readBatch()) != null) {
                counterWorkers.execute(counter.createTask(largeString));
            }
            //дожидаемся пока counter точно посчитает все отдельные куски
            counterWorkers.shutdown();
            assert counterWorkers.awaitTermination(1, TimeUnit.DAYS);
            //можем давать сигнал counter что инпута больше не будет
            counter.end();
            //дожидаемся merger
            merger.join();
            return merger.getFinalCount();
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
            System.exit(1);
            return Collections.emptyMap();
        } finally {
            counterWorkers.shutdown();
        }
    }
}
