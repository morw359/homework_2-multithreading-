package ru.digitalhabbits.homework2;

import org.junit.jupiter.api.Test;
import ru.digitalhabbits.homework2.impl.AsyncFileLetterCounter;
import ru.digitalhabbits.homework2.naive.AsyncFileLetterCounterNaive;

import java.io.File;
import java.time.Instant;
import java.util.Map;

import static com.google.common.io.Resources.getResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class E2ETests {

    @Test
    void async_file_letter_counting_should_return_predicted_count() {
        var file = getFile("test.txt");
        var counter = new AsyncFileLetterCounter();

        long time = Instant.now().toEpochMilli();
        Map<Character, Long> count = counter.count(file);
        time = Instant.now().toEpochMilli() - time;
        System.out.println("async " + time + " ms.");

        assertThat(count).containsOnly(
                entry('a', 2697L),
                entry('b', 2683L),
                entry('c', 2647L),
                entry('d', 2613L),
                entry('e', 2731L),
                entry('f', 2629L)
        );
    }

    @Test
    void sync_file_letter_counting_should_return_predicted_count() {
        var file = getFile("test.txt");
        var counter = new AsyncFileLetterCounterNaive();
        long time = Instant.now().toEpochMilli();
        Map<Character, Long> count = counter.count(file);
        time = Instant.now().toEpochMilli() - time;
        System.out.println("sync " + time + " ms.");
        assertThat(count).containsOnly(
                entry('a', 2697L),
                entry('b', 2683L),
                entry('c', 2647L),
                entry('d', 2613L),
                entry('e', 2731L),
                entry('f', 2629L)
        );
    }

    private File getFile(String name) {
        return new File(getResource(name).getPath());
    }
}
