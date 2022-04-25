package ru.digitalhabbits.homework2.naive;

import ru.digitalhabbits.homework2.LetterCountMerger;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Optional
 *
 * Merger for 2 maps
 * Given ([a=2,b=4] , [a=3,b=1,c=7])
 * will return [a=6,b=5,c=7]
 */
public class LetterCountMergerNaiveImpl implements LetterCountMerger {
    public Map<Character, Long> merge(Map<Character, Long> first, Map<Character, Long> second) {
        return Stream.concat(first.entrySet().stream(), second.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Long::sum));
    }
}
