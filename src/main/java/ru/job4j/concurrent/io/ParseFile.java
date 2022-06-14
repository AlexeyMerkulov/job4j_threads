package ru.job4j.concurrent.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String content(Predicate<Character> filter) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = in.read()) != -1) {
                char charData = (char) data;
                if (filter.test(charData)) {
                    sb.append(charData);
                }
            }
        }
        return sb.toString();
    }

    public String getContent() throws IOException {
        return content(x -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return content(x -> x < 0x80);
    }
}