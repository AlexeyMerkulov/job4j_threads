package ru.job4j.concurrent.intro;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        int count = 0;
        char[] process = {'/', '|', '\\'};
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\rLoading ... " + process[count++]);
                if (count == process.length) {
                    count = 0;
                }
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        try {
            progress.start();
            Thread.sleep(2000);
            progress.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
