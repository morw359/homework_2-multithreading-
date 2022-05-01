package ru.digitalhabbits.homework2.impl.counter;

import org.junit.jupiter.api.Test;
import ru.digitalhabbits.homework2.impl.MergerRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MergerRunnerTests {
    @Test
    void testMerge() throws InterruptedException {
        MergerRunner merger = new MergerRunner(null);
        Map<Character, Long> m1 = new HashMap<>();
        m1.put('a', 1L);
        m1.put('b', 2L);
        m1.put('c', 3L);
        m1.put('d', 4L);
        Map<Character, Long> m2 = new HashMap<>();
        m2.put('a', 1L);
        m2.put('b', 2L);
        m2.put('c', 3L);
        m2.put('d', 4L);
        m2.put('e', 123L);
        Map<Character, Long> m3 = new HashMap<>();
        Map<Character, Long> finalRes = merger.merge(merger.merge(m1, m2), m3);
        assertThat(finalRes).containsOnly(
                entry('a', 2L),
                entry('b', 4L),
                entry('c', 6L),
                entry('d', 8L),
                entry('e', 123L)
        );
        m3.put('a', null);
        assertThrows(NullPointerException.class, () -> merger.merge(finalRes, m3));
    }

    //мб есть какое-нибудь ограничение на время теста на такие случаи, чтобы не висели при багах
    @Test
    void testWithCounter() throws Exception {
        try (Counter c = new Counter()) {
            MergerRunner merger = new MergerRunner(c);
            Map<Character, Long> m1 = new HashMap<>();
            m1.put('a', 1L);
            m1.put('b', 2L);
            m1.put('c', 3L);
            m1.put('d', 4L);
            Map<Character, Long> m2 = new HashMap<>();
            m2.put('a', 1L);
            m2.put('b', 2L);
            m2.put('c', 3L);
            m2.put('d', 4L);
            m2.put('e', 123L);
            Map<Character, Long> m3 = new HashMap<>();
            merger.start();
            c.countResult.add(m1);
            c.countResult.add(m2);
            c.countResult.add(m3);
            c.endCountProcess();
            merger.join();
            assertThat(merger.getFinalCount()).containsOnly(
                    entry('a', 2L),
                    entry('b', 4L),
                    entry('c', 6L),
                    entry('d', 8L),
                    entry('e', 123L)
            );
        }
    }


}
