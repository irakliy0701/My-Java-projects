package com.company;

public class Matrix {
    private final double[][] matrix;
    private final int rows, columns;

    // Конструктор
    public Matrix(int newRows, int newColumns) {
        rows = newRows;
        columns = newColumns;
        matrix = new double[newRows][newColumns];
    }

    // Метод для установки значения элемента матрицы
    public void set(int i, int j, double value) {
        matrix[i][j] = value;
    }

    // Метод для получения значения элемента матрицы
    public double get(int i, int j) {
        return matrix[i][j];
    }

    // Метод для преобразования матрицы в зубчатый двумерный массив
    public double[][] toJaggedArray() {
        double[][] jaggedArray = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(matrix[i], 0, jaggedArray[i], 0, columns);
        }
        return jaggedArray;
    }

    // Метод для получения числа строк в матрице
    public int getRows() {
        return rows;
    }

    // Метод для получения числа столбцов в матрице
    public int getColumns() {
        return columns;
    }
}
