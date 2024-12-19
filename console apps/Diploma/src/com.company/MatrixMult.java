package com.company;

public class MatrixMult {
    // Метод для перемножения двух матриц (строка на столбец)
    // принимает матрицы по ссылке
    public static Matrix multiply(Matrix A, Matrix B) {
        int dim = A.getRows();
        Matrix result;

        // Проверка на возможность умножения только для квадратных матриц
        if (A.getRows() != A.getColumns() || B.getRows() != B.getColumns()) {
            System.out.println("It's possible to multiply only the square matrix!");
            return null;
        }

        // Проверка на одинаковую размерность матриц для умножения
        if ((A.getRows() != B.getColumns()) && (B.getRows() != A.getColumns())) {
            System.out.println("Matrices do not have the same dimension!");
            return null;
        }
        result = new Matrix(dim, dim);

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                double tmp = 0;
                for (int k = 0; k < dim; k++) {
                    tmp += A.get(i, k) * B.get(k, j);
                }
                result.set(i, j, tmp);
            }
        }
        return result;
    }

    // Метод для перемножения трех матриц
    public static Matrix multiply(Matrix A, Matrix B, Matrix C) {
        return multiply(multiply(A, B), C);
    }
}
