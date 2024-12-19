package com.company;

public class MatrixOperations {
    // Метод для транспонирования матрицы
    public static Matrix transposition(Matrix A) {
        int dim = A.getRows();
        Matrix result = new Matrix(dim, dim);

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                result.set(i, j, A.get(j, i));
            }
        }
        return result;
    }

    // Метод для вычисления следа матрицы (сумма диагональных элементов)
    public static double trace(Matrix A) {
        int dim = A.getRows();
        double result = 0;

        for (int i = 0; i < dim; i++) {
            result += A.get(i, i);
        }
        return result;
    }

    // Метод для LU-разложения матрицы
    private static double[][] LUdecomposition(Matrix A, int[] RowNum) {
        int dim = A.getRows();
        double[][] result = A.toJaggedArray();

        for (int i = 0; i < dim; i++) {
            RowNum[i] = i;
        }

        for (int i = 0; i < dim; i++) {
            double MaxValue = 0;
            int MaxNum = -1;
            for (int Row = i; Row < dim; Row++) {
                if (Math.abs(result[Row][i]) > MaxValue) {
                    MaxValue = Math.abs(result[Row][i]);
                    MaxNum = Row;
                }
            }

            if (MaxNum > i) {
                double[] tmpUnit = result[i];
                result[i] = result[MaxNum];
                result[MaxNum] = tmpUnit;

                int tmpMaxNum = RowNum[i];
                RowNum[i] = RowNum[MaxNum];
                RowNum[MaxNum] = tmpMaxNum;
            } else {
                if (MaxValue == 0) {
                    System.out.println("\nDeterminant = 0!\n");
                    return null;
                }
            }

            for (int j = i + 1; j < dim; j++) {
                result[j][i] /= result[i][i];
                for (int k = i + 1; k < dim; k++) {
                    result[j][k] -= result[j][i] * result[i][k];
                }
            }
        }
        return result;
    }

    // Метод для нахождения обратной матрицы с использованием LU-разложения с перестановкой строк
    public static Matrix inverse(Matrix A) {
        int dim = A.getRows();
        int[] RowNum = new int[dim];
        double[][] MatrixDecomposition, TmpResult;
        Matrix Result;

        // Если матрица вырожденная, то обратной к ней нет
        if (MatrixOperations.determinant(A) == 0) {
            System.out.println("Can't solve. Determinant = 0!");
            return null;
        }

        Result = new Matrix(dim, dim);
        TmpResult = new double[dim][dim];

        // Выполнение LU-разложения с перестановкой строк
        MatrixDecomposition = LUdecomposition(A, RowNum);

        // Подсчет элементов обратной матрицы
        for (int m = dim - 1; m >= 0; m--) {
            TmpResult[m][m] = 1;
            for (int j = dim - 1; j > m; j--)
                TmpResult[m][m] -= MatrixDecomposition[m][j] * TmpResult[j][m];
            TmpResult[m][m] /= MatrixDecomposition[m][m];
            for (int i = m - 1; i >= 0; i--) {
                for (int j = dim - 1; j > i; j--) {
                    TmpResult[i][m] -= MatrixDecomposition[i][j] * TmpResult[j][m];
                    TmpResult[m][i] -= MatrixDecomposition[j][i] * TmpResult[m][j];
                }
                TmpResult[i][m] /= MatrixDecomposition[i][i];
            }
        }

        // Перестановка столбцов в исходную комбинацию
        for (int i = 0; i < dim; i++) {
            int Temp = i;
            while (RowNum[Temp] != i)
                Temp++;

            for (int m = 0; m < dim; m++) {
                double Tmp = TmpResult[m][i];
                TmpResult[m][i] = TmpResult[m][Temp];
                TmpResult[m][Temp] = Tmp;
            }
            RowNum[Temp] = RowNum[i];
        }

        // Заполнение матрицы обратной матрицей
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                Result.set(i, j, TmpResult[i][j]);
            }
        }
        return Result;
    }

    // Метод для нахождения определителя матрицы с использованием метода Барейса
    public static double determinant(Matrix A) {
        int dim = A.getRows();
        double[][] MatrixCopy = A.toJaggedArray();

        // При больших матрицах следует изменить тип на long
        double Result = 1;
        int Sign = 1;

        // Проходит по всем строкам, исключая последнюю
        for (int i = 0; i < dim - 1; i++) {
            int MaxNum = i;
            double MaxValue = Math.abs(MatrixCopy[i][i]);

            // Нахождение строки с максимальным по модулю элементом
            for (int j = i + 1; j < dim; j++) {
                double Unit = Math.abs(MatrixCopy[j][i]);

                if (Unit > MaxValue) {
                    MaxNum = j;
                    MaxValue = Unit;
                }
            }

            // Перестановка строк, если нужна
            if (MaxNum > i) {
                double[] Tmp = MatrixCopy[i];
                MatrixCopy[i] = MatrixCopy[MaxNum];
                MatrixCopy[MaxNum] = Tmp;
                Sign++;
            } else {
                // Перестановка не нужна, идет проверка на ноль
                if (MaxValue == 0)
                    return MaxValue;
            }

            double Unit1 = MatrixCopy[i][i];

            // Текущая строка вычитается из всех последующих
            for (int j = i + 1; j < dim; j++) {
                double Unit2 = MatrixCopy[j][i];
                MatrixCopy[j][i] = 0;

                for (int n = i + 1; n < dim; n++) {
                    MatrixCopy[j][n] = (MatrixCopy[j][n] * Unit1 - MatrixCopy[i][n] * Unit2) / Result;
                }
            }
            Result = Unit1;
        }

        // Проверка на равенство нулю
        if (MatrixCopy[dim - 1][dim - 1] == 0) {
            System.out.println("Determinant = 0!");
            return MatrixCopy[dim - 1][dim - 1];
        }

        // Возвращение самого нижнего правого элемента матрицы - это определитель
        if (Sign % 2 == 0)
            return MatrixCopy[dim - 1][dim - 1];
        else
            return -MatrixCopy[dim - 1][dim - 1];
    }

}
