package ru.digitalhabbits.homework2.naive;

import ru.digitalhabbits.homework2.FileLetterCounter;
import ru.digitalhabbits.homework2.FileReader;
import ru.digitalhabbits.homework2.LetterCountMerger;
import ru.digitalhabbits.homework2.LetterCounter;

import java.io.File;
import java.util.Collections;
import java.util.Map;

public class AsyncFileLetterCounterNaive implements FileLetterCounter {
    private final FileReader fileReader = new FileReaderNaiveImpl();
    private final LetterCounter letterCounter = new LetterCounterNaiveImpl();
    private final LetterCountMerger merger = new LetterCountMergerNaiveImpl();

    @Override
    public Map<Character, Long> count(File input) {
        return fileReader.readLines(input)
                .map(letterCounter::count)
                .reduce(merger::merge).orElse(Collections.emptyMap());
    }
}
