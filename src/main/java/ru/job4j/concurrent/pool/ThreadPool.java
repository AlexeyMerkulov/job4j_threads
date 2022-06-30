package ru.job4j.concurrent.pool;

import ru.job4j.concurrent.wait.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool(int maxNumOfTasks) {
        tasks = new SimpleBlockingQueue<>(maxNumOfTasks);
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            threads.add(thread);
            thread.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool(10);
        for (int i = 1; i <= 10; i++) {
            int j = i;
            Runnable work = () -> System.out.printf("Work %d is done" + System.lineSeparator(), j);
            pool.work(work);
        }
        Thread.sleep(1000);
        pool.shutdown();
    }
}