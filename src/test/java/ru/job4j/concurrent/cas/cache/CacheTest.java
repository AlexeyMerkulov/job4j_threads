package ru.job4j.concurrent.cas.cache;

import org.junit.Test;

import static org.junit.Assert.*;

public class CacheTest {
    @Test
    public void addInCache() {
        Cache cache = new Cache();
        assertTrue(cache.add(new Base(1, 1)));
    }

    @Test
    public void updateCache() {
        Cache cache = new Cache();
        cache.add(new Base(1, 1));
        cache.add(new Base(2, 2));
        Base base = new Base(1, 1);
        assertTrue(cache.update(base));
    }

    @Test
    public void noUpdateCache() {
        Cache cache = new Cache();
        cache.add(new Base(1, 1));
        cache.add(new Base(2, 2));
        Base base = new Base(3, 3);
        assertFalse(cache.update(base));
    }

    @Test(expected = OptimisticException.class)
    public void whenExceptionThrown() {
        Cache cache = new Cache();
        cache.add(new Base(1, 1));
        cache.add(new Base(2, 2));
        Base base = new Base(2, 3);
        cache.update(base);
    }
}