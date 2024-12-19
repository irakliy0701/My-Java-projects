package com.company;

import java.util.Random;

public class SupportFunctions {
    // Методы для печати элементов матрицы
    public static void printMatrix(Matrix A, StringBuilder view) {
        int dim = A.getRows();

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                view.append(A.get(i, j)).append("\t");
            }
            view.append("\n");
        }
        view.append("\n");
    }

    // Методы для печати элементов массива
    public static void printArray(double[] array, StringBuilder view) {
        for (double element : array) {
            view.append(element).append("\n");
        }
        view.append("\n");
    }

    public static void printArray(double[][] array, StringBuilder view) {
        for (double[] row : array) {
            for (double element : row) {
                view.append(element).append("\t");
            }
            view.append("\n");
        }
        view.append("\n");
    }

    // Метод для заполнения матрицы случайными числами
    public static void initializeMatrixRandomly(Matrix A, int value) {
        int dim = A.getRows();
        Random r = new Random();

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                A.set(i, j, r.nextDouble() * value);
            }
        }
    }

    public static void printCoords(double[] q, double[] tArray, StringBuilder dataInfo) {
        for (int i = 0; i < q.length; i++){
            dataInfo.append("q_"+ (i + 1)+ "(t_"+ (i + 1) + "=" + tArray[i] + ") = " + q[i]).append("\n");
        }
        dataInfo.append("\n");
    }
}
