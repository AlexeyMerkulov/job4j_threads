package ru.job4j.concurrent.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String nameFile;

    public Wget(String url, int speed, String nameFile) {
        this.url = url;
        this.speed = speed;
        this.nameFile = nameFile;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(nameFile)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long bytesWrited = 0;
            long startTime = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, speed)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                bytesWrited += bytesRead;
                if (bytesWrited >= speed) {
                    long deltaTime = System.currentTimeMillis() - startTime;
                    if (deltaTime < 1000) {
                        Thread.sleep(1000 - deltaTime);
                    }
                    bytesWrited = 0;
                    startTime = System.currentTimeMillis();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void validateArgsNum(int argsNum) {
        if (argsNum != 3) {
            throw new IllegalArgumentException("Введено неверное кол-во параметров" + System.lineSeparator()
                    + "1. Введите адрес ссылки для скачивания файла" + System.lineSeparator()
                    + "2. Введите желаемую скорость скачивания в байт/с" + System.lineSeparator()
                    + "3. Введите название файла для записи на диск");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Wget.validateArgsNum(args.length);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String nameOfFile = args[2];
        Thread wget = new Thread(new Wget(url, speed, nameOfFile));
        wget.start();
        wget.join();
    }
}