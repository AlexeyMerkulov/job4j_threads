package ru.job4j.concurrent.pool;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParallelIndexSearchTest {

    @Test
    public void searchInteger() {
        Integer[] array = {2, 5, 7, 12, 29, 75, 78, 90, 4, 128, 50,
                0, 32, 22, 19, 78, 21, 14, 16, 77};
        int rsl = ParallelIndexSearch.search(array, 78);
        assertEquals(rsl, 15);
    }

    @Test
    public void searchString() {
        String[] array = {"Alena", "Petr", "Dima", "Evgeny"};
        int rsl = ParallelIndexSearch.search(array, "Dima");
        assertEquals(rsl, 2);
    }

    @Test
    public void searchNoneContains() {
        Integer[] array = {2, 5, 7, 12, 29, 75, 78, 90, 4, 128, 50,
                0, 32, 22, 19, 78, 21, 14, 16, 77};
        int rsl = ParallelIndexSearch.search(array, 25);
        assertEquals(rsl, -1);
    }
}