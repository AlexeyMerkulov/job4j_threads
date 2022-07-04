package ru.job4j.concurrent.pool;

import org.junit.Test;

import static org.junit.Assert.*;

public class RolColSumTest {

    @Test
    public void whenSyncSum() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] rsl = RolColSum.sum(matrix);
        assertEquals(rsl[0].getColSum(), 12);
        assertEquals(rsl[0].getRowSum(), 6);
        assertEquals(rsl[1].getColSum(), 15);
        assertEquals(rsl[1].getRowSum(), 15);
        assertEquals(rsl[2].getColSum(), 18);
        assertEquals(rsl[2].getRowSum(), 24);
    }

    @Test
    public void whenAsyncSum() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] rsl = RolColSum.sum(matrix);
        assertEquals(rsl[0].getColSum(), 12);
        assertEquals(rsl[0].getRowSum(), 6);
        assertEquals(rsl[1].getColSum(), 15);
        assertEquals(rsl[1].getRowSum(), 15);
        assertEquals(rsl[2].getColSum(), 18);
        assertEquals(rsl[2].getRowSum(), 24);
    }
}