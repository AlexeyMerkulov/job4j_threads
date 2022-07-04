package ru.job4j.concurrent.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            Sums element = new Sums();
            for (int j = 0; j < matrix.length; j++) {
                element.rowSum += matrix[i][j];
                element.colSum += matrix[j][i];
            }
            result[i] = element;
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] result = new Sums[matrix.length];
        Map<Integer, CompletableFuture<Sums>> map = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            map.put(i, getTask(matrix, i));
        }
        for (Integer key : map.keySet()) {
            result[key] = map.get(key).get();
        }
        return result;
    }

    private static CompletableFuture<Sums> getTask(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> {
            Sums element = new Sums();
            for (int j = 0; j < matrix.length; j++) {
                element.rowSum += matrix[index][j];
                element.colSum += matrix[j][index];
            }
            return element;
        });
    }
}