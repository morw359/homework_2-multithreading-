package ru.digitalhabbits.homework2.naive;

import ru.digitalhabbits.homework2.LetterCounter;

import java.util.HashMap;
import java.util.Map;

/**
 * Counter characters in given string
 */
public class LetterCounterNaiveImpl implements LetterCounter {

    private final static int a = 'a';
    private final static int z = 'z';

    public Map<Character, Long> count(String input) {
        Map<Character, Long> res = new HashMap<>();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            //если не [a-z]
            if (c < a || c > z) {
                continue;
            }
            res.compute(c, (character, val) -> val == null ? 1L : ++val);
        }
        return res;
    }
}
