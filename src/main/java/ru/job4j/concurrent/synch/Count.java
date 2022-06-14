package ru.job4j.concurrent.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Count {
    @GuardedBy("this")
    private int value;

    public synchronized int increment() {
        return ++value;
    }
}
