package ru.digitalhabbits.homework2;

import org.junit.jupiter.api.Test;
import ru.digitalhabbits.homework2.impl.ForkCounter;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class ForkCounterTest {
    @Test
    void assertNumberOfTasks() throws InterruptedException {
        BlockingQueue<Map<Character, Long>> counterResult = new LinkedBlockingQueue<>();
        String s = "asdfaglasg";
        {
            ForkCounter counter = new ForkCounter(counterResult, 0, s.length(), s);
            counter.run();
            Map<Character, Long> res = counterResult.poll(1, TimeUnit.SECONDS);//hm...
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
            ForkCounter counter = new ForkCounter(counterResult, 1, s.length() - 1, s);
            counter.run();
            Map<Character, Long> res = counterResult.poll(1, TimeUnit.SECONDS);//hm...
            assertThat(res).containsOnly(
                    entry('a', 2L),
                    entry('s', 2L),
                    entry('d', 1L),
                    entry('g', 1L),
                    entry('f', 1L),
                    entry('l', 1L)
            );
        }
        {
            ForkCounter counter = new ForkCounter(counterResult, 3, 6, s);
            counter.run();
            Map<Character, Long> res = counterResult.poll(1, TimeUnit.SECONDS);//hm...
            assertThat(res).containsOnly(
                    entry('f', 1L),
                    entry('a', 1L),
                    entry('g', 1L)
            );
        }
        {
            String s2 = "0a;\n\n\n;b;c";
            ForkCounter counter = new ForkCounter(counterResult, 0, s2.length(), s2);
            counter.run();
            Map<Character, Long> res = counterResult.poll(1, TimeUnit.SECONDS);//hm...
            assertThat(res).containsOnly(
                    entry('a', 1L),
                    entry('b', 1L),
                    entry('c', 1L)
            );
        }
    }
}
