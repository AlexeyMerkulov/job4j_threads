package ru.job4j.concurrent.pool;

import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T element;

    public ParallelIndexSearch(T[] array, int from, int to, T element) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.element = element;
    }

    @Override
    protected Integer compute() {
        if (to - from < 10) {
            for (int i = from; i <= to; i++) {
                if (Objects.equals(element, array[i])) {
                    return i;
                }
            }
            return -1;
        }
        int mid = (from + to) / 2;
        ParallelIndexSearch<T> leftSearch = new ParallelIndexSearch<>(array, from, mid, element);
        ParallelIndexSearch<T> rightSearch = new ParallelIndexSearch<>(array, mid + 1, to, element);
        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        return Math.max(left, right);
    }

    public static <T> int search(T[] array, T element) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelIndexSearch<>(array, 0, array.length - 1, element));
    }
}