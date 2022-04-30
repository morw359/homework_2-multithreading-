package ru.digitalhabbits.homework2;

import org.junit.jupiter.api.Test;
import ru.digitalhabbits.homework2.impl.counter.Counter;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class CounterTest {
    @Test
    void checkCountSymbols() throws InterruptedException {
        {
            String s = "asdfaglasg";
            Counter counter = new Counter();
            counter.createTask(s).run();
            Map<Character, Long> res = counter.take();//hm...
            assertThat(res).containsOnly(
                    entry('a', 3L),
                    entry('s', 2L),
                    entry('d', 1L),
                    entry('g', 2L),
                    entry('f', 1L),
                    entry('l', 1L)
            );
        }
        {
            String s2 = "0a;\n\n\n;b;c";
            Counter counter = new Counter();
            counter.createTask(s2).run();
            Map<Character, Long> res = counter.take();
            assertThat(res).containsOnly(
                    entry('a', 1L),
                    entry('b', 1L),
                    entry('c', 1L)
            );
        }
    }

    @Test
    void checkQueue() throws InterruptedException {
        Counter counter = new Counter();
        counter.createTask("abbccdd").run();
        Map<Character, Long> res = counter.take();//hm...
        assertThat(res != null).isTrue();
        counter.end();
        assertThat(counter.take() == null).isTrue();
    }
}
