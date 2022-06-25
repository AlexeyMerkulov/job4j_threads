package ru.job4j.concurrent.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int current;
        int newValue;
        do {
            current = count.get();
            newValue = current + 1;
        } while (!count.compareAndSet(current, newValue));
    }

    public int get() {
        return count.get();
    }
}