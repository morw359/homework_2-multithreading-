package ru.digitalhabbits.homework2;

import org.junit.jupiter.api.Test;
import ru.digitalhabbits.homework2.impl.ForkCounter;
import ru.digitalhabbits.homework2.impl.counter.Counter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CounterTests {
    @Test
    void assertNumberOfTasks() {
        Counter counter = new Counter(3);
        List<Runnable> tasks = counter.createTasks("0123456789");
        assertEquals(3, tasks.size(), "expected number of tasks");
        {
            ForkCounter casted = (ForkCounter) tasks.get(0);
            assertEquals(0, casted.getBegin());
            assertEquals(3, casted.getEnd());
        }
        {
            ForkCounter casted = (ForkCounter) tasks.get(1);
            assertEquals(3, casted.getBegin());
            assertEquals(6, casted.getEnd());
        }
        {
            ForkCounter casted = (ForkCounter) tasks.get(2);
            assertEquals(6, casted.getBegin());
            assertEquals(10, casted.getEnd());
        }
    }

    @Test
    void assertNumberOfTasks2() {
        Counter counter = new Counter(1);
        List<Runnable> tasks = counter.createTasks("0123456789");
        assertEquals(1, tasks.size(), "expected number of tasks");
        {
            ForkCounter casted = (ForkCounter) tasks.get(0);
            assertEquals(0, casted.getBegin());
            assertEquals(10, casted.getEnd());
        }
    }

    @Test
    void assertNumberOfTasks3() {
        Counter counter = new Counter(5);
        List<Runnable> tasks = counter.createTasks("12");
        assertEquals(1, tasks.size(), "expected number of tasks");
        {
            ForkCounter casted = (ForkCounter) tasks.get(0);
            assertEquals(0, casted.getBegin());
            assertEquals(2, casted.getEnd());
        }
    }

    @Test
    void assertNumberOfTasksEdgeCases() {
        Counter counter = new Counter(5);
        List<Runnable> tasks = counter.createTasks("");
        assertEquals(0, tasks.size(), "expected number of tasks");
        assertThrows(NullPointerException.class, () -> counter.createTasks(null));
    }
}
