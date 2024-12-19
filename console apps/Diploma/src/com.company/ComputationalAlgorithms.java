package com.company;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ComputationalAlgorithms {

    // Алгоритм решения прямой задачи динамики
    public static double[] seqDirectTaskSolution(Matrix Alpha, Matrix Beta, double[] DqArray,
                                              double[] DDqArray, double[] MassArray, Matrix G, Matrix Omega1,
                                              Matrix Omega2, Matrix[] A, Matrix[] R, Matrix[] H) {
        Matrix Tmp;
        int dim1 = 6;
        double[] Result = new double[dim1];

        Matrix[][] U = new Matrix[dim1][dim1];
        Matrix[][][] U1 = new Matrix[dim1][dim1][dim1];

        // Вычисление матриц (Uij) преобразования координат от базовой системы к исходной системе координат.
        // Для поступательного типа звеньев
        U[0][0] = MatrixMult.multiply(MatrixMult.multiply(Omega2, A[0]), A[0]);
        U[1][0] = MatrixMult.multiply(MatrixMult.multiply(Omega2, A[0]), A[1]);
        U[1][1] = MatrixMult.multiply(MatrixMult.multiply(A[0], Omega2), A[1]);

        U[2][0] = MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(Omega2, A[0]), A[1]), A[2]);
        U[2][1] = MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(A[0], Omega2), A[1]), A[2]);
        U[2][2] = MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(A[0], A[1]), Omega2), A[2]);

        U[3][0] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(Omega2, A[0]), A[1]), A[2]), A[3]);
        U[3][1] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega2), A[1]), A[2]), A[3]);
        U[3][2] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], A[1]), Omega2), A[2]), A[3]);
        U[3][3] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], A[1]), A[2]), Omega2), A[3]);

        U[4][0] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(Omega2, A[0]), A[1]), A[2]), A[3]), A[4]);
        U[4][1] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(A[0], Omega2), A[1]), A[2]), A[3]), A[4]);
        U[4][2] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(A[0], A[1]), Omega2), A[2]), A[3]), A[4]);
        U[4][3] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(A[0], A[1]), A[2]), Omega2), A[3]), A[4]);

        U[4][4] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(A[0], A[1]), A[2]), A[3]), Omega2), A[4]);

        U[5][0] = MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(Omega2, A[0]), A[1]), A[2]), A[3]), A[4]), A[5]);
        U[5][1] = MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega2), A[1]), A[2]), A[3]), A[4]), A[5]);
        U[5][2] = MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], A[1]), Omega2), A[2]), A[3]), A[4]), A[5]);
        U[5][3] = MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], A[1]), A[2]), Omega2), A[3]), A[4]), A[5]);
        U[5][4] = MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], A[1]), A[2]), A[3]), Omega2), A[4]), A[5]);
        U[5][5] = MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], A[1]), A[2]), A[3]), Omega2), A[4]), A[5]);

        // Вычисление матриц (Uijk) преобразования координат от базовой системы к исходной системе координат.
        // Для вращательного типа звеньев
        U1[0][0][0] = U1[1][0][0] = U1[2][0][0] = U1[3][0][0] = U1[4][0][0] = U1[5][0][0] = MatrixMult.multiply(
                MatrixMult.multiply(A[0], Omega1), Omega1);
        U1[1][0][1] = U1[2][0][1] = U1[3][0][1] = U1[4][0][1] = U1[5][0][1] = MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), Omega1), A[0]);
        U1[2][0][2] = U1[3][0][2] = U1[4][0][2] = U1[5][0][2] = MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), Omega1), A[1]);
        U1[3][0][3] = U1[4][0][3] = U1[5][0][3] = MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), Omega1), A[2]);
        U1[4][0][4] = U1[5][0][4] = MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), Omega1), A[3]);
        U1[5][0][5] = MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), Omega1), A[4]);

        U1[1][1][0] = U1[2][1][0] = U1[3][1][0] = U1[4][1][0] = U1[5][1][0] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(A[0], Omega1), Omega1), A[0]);
        U1[1][1][1] = U1[2][1][1] = U1[3][1][1] = U1[4][1][1] = U1[5][1][1] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[0]), Omega1), A[0]);
        U1[2][1][2] = U1[3][1][2] = U1[4][1][2] = U1[5][1][2] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[0]), Omega1), A[1]);
        U1[3][1][3] = U1[4][1][3] = U1[5][1][3] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[0]), Omega1), A[2]);
        U1[4][1][4] = U1[5][1][4] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[0]), Omega1), A[3]);
        U1[5][1][5] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[0]), Omega1), A[4]);

        U1[2][2][0] = U1[3][2][0] = U1[4][2][0] = U1[5][2][0] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(A[0], Omega1), Omega1), A[1]);
        U1[2][2][1] = U1[3][2][1] = U1[4][2][1] = U1[5][2][1] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[0]), Omega1), A[1]);
        U1[2][2][2] = U1[3][2][2] = U1[4][2][2] = U1[5][2][2] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[1]), Omega1), A[1]);
        U1[3][2][3] = U1[4][2][3] = U1[5][2][3] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[1]), Omega1), A[2]);
        U1[4][2][4] = U1[5][2][4] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[1]), Omega1), A[3]);
        U1[5][2][5] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[1]), Omega1), A[4]);

        U1[3][3][0] = U1[4][3][0] = U1[5][3][0] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(A[0], Omega1), Omega1), A[2]);
        U1[3][3][1] = U1[4][3][1] = U1[5][3][1] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[0]), Omega1), A[2]);
        U1[3][3][2] = U1[4][3][2] = U1[5][3][2] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[1]), Omega1), A[2]);
        U1[3][3][3] = U1[4][3][3] = U1[5][3][3] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[2]), Omega1), A[2]);
        U1[4][3][4] = U1[5][3][4] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[2]), Omega1), A[3]);
        U1[5][3][5] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[2]), Omega1), A[4]);

        U1[4][4][0] = U1[5][4][0] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(A[0], Omega1), Omega1), A[3]);
        U1[4][4][1] = U1[5][4][1] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[0]), Omega1), A[3]);
        U1[4][4][2] = U1[5][4][2] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[1]), Omega1), A[3]);
        U1[4][4][3] = U1[5][4][3] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[2]), Omega1), A[3]);
        U1[4][4][4] = U1[5][4][4] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[3]), Omega1), A[3]);
        U1[5][4][5] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[3]), Omega1), A[4]);

        U1[5][5][0] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(A[0], Omega1), Omega1), A[3]);
        U1[5][5][1] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[0]), Omega1), A[4]);
        U1[5][5][2] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[1]), Omega1), A[4]);
        U1[5][5][3] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[2]), Omega1), A[4]);
        U1[5][5][4] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[3]), Omega1), A[4]);
        U1[5][5][5] = MatrixMult.multiply(MatrixMult.multiply(
                MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[4]), Omega1), A[4]);

        // Алгоритм вычисления 6 обобщенных сил Q и формирование матриц Alpha и Beta
        double TmpS1 = 0, S1 = 0, S2 = 0, S3 = 0;
        double[] Unit = new double[dim1];

        // Вычисление левой части Уравнения Лагранжа II рода
        for (int s = 0; s < dim1; s++) {
            S1 = 0;
            S2 = 0;
            S3 = 0;

            for (int k = 0; k < dim1; k++) {Unit[k] = 0;}
            for (int i = s; i < dim1; i++) {
                for (int j = 0; j <= i; j++) {
                    TmpS1 = MatrixOperations.trace(MatrixMult.multiply(
                            U[i][s], H[i], MatrixOperations.transposition(U[i][j])));
                    switch (j) {
                        case 0:
                            Unit[0] += TmpS1;
                            break;
                        case 1:
                            Unit[1] += TmpS1;
                            break;
                        case 2:
                            Unit[2] += TmpS1;
                            break;
                        case 3:
                            Unit[3] += TmpS1;
                            break;
                        case 4:
                            Unit[4] += TmpS1;
                            break;
                        case 5:
                            Unit[5] += TmpS1;
                            break;
                    }
                }
                TmpS1 = 0;
            }

            // Получение первой составляющей левой части Уравнения Лагранжа II рода
            for (int k = 0; k < dim1; k++) {
                Alpha.set(s, k, Unit[k]);
                Unit[k] *= DDqArray[k];
                S1 += Unit[k];
            }

            // Получение второй составляющей левой части Уравнения Лагранжа II рода
            for (int l = s; l < dim1; l++) {
                for (int m = 0; m <= l; m++) {
                    for (int n = 0; n <= l; n++) {
                        //double[][] tmpMult = MatrixMult.multiply(U[l][s], H[l], MatrixOperations.transposition(U1[l][m][n]));
                        //S2 += MatrixOperations.trace(tmpMult) * (DqArray[m]) * (DqArray[n]);
                        S2 += MatrixOperations.trace(MatrixMult.multiply(U[l][s],
                                H[l], MatrixOperations.transposition(U1[l][m][n]))) * (DqArray[m]) * (DqArray[n]);
                    }
                }
            }

            // Получение третьей составляющей левой части Уравнения Лагранжа II рода
            for (int p = s; p < dim1; p++) {
                //Matrix Tmp = G.multiply(U[p][s]).multiply(R[p]);
                Tmp = MatrixMult.multiply(G, U[p][s], R[p]);
                S3 += Tmp.get(0, 0) * MassArray[p];
            }
            Result[s] = S1 + S2 - S3;
            Beta.set(s, 0, Result[s] - S2 + S3);
        }
        return Result;
    }

    public static double[] multiDirectTaskSolution(int numberOfThreads, Matrix Alpha, Matrix Beta, double[] DqArray,
                                                     double[] DDqArray, double[] MassArray, Matrix G, Matrix Omega1,
                                                     Matrix Omega2, Matrix[] A, Matrix[] R, Matrix[] H) {
        int dim1 = 6;
        double[] Result = new double[dim1];

        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        executor.submit(() -> {
            Matrix Tmp;
            Matrix[][] U = new Matrix[dim1][dim1];
            Matrix[][][] U1 = new Matrix[dim1][dim1][dim1];

            // Вычисление матриц (Uij) преобразования координат от базовой системы к исходной системе координат.
            // Для поступательного типа звеньев
            U[0][0] = MatrixMult.multiply(MatrixMult.multiply(Omega2, A[0]), A[0]);
            U[1][0] = MatrixMult.multiply(MatrixMult.multiply(Omega2, A[0]), A[1]);
            U[1][1] = MatrixMult.multiply(MatrixMult.multiply(A[0], Omega2), A[1]);

            U[2][0] = MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(Omega2, A[0]), A[1]), A[2]);
            U[2][1] = MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(A[0], Omega2), A[1]), A[2]);
            U[2][2] = MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(A[0], A[1]), Omega2), A[2]);

            U[3][0] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(Omega2, A[0]), A[1]), A[2]), A[3]);
            U[3][1] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega2), A[1]), A[2]), A[3]);
            U[3][2] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], A[1]), Omega2), A[2]), A[3]);
            U[3][3] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], A[1]), A[2]), Omega2), A[3]);

            U[4][0] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(Omega2, A[0]), A[1]), A[2]), A[3]), A[4]);
            U[4][1] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(A[0], Omega2), A[1]), A[2]), A[3]), A[4]);
            U[4][2] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(A[0], A[1]), Omega2), A[2]), A[3]), A[4]);
            U[4][3] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(A[0], A[1]), A[2]), Omega2), A[3]), A[4]);

            U[4][4] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(A[0], A[1]), A[2]), A[3]), Omega2), A[4]);

            U[5][0] = MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(Omega2, A[0]), A[1]), A[2]), A[3]), A[4]), A[5]);
            U[5][1] = MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega2), A[1]), A[2]), A[3]), A[4]), A[5]);
            U[5][2] = MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], A[1]), Omega2), A[2]), A[3]), A[4]), A[5]);
            U[5][3] = MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], A[1]), A[2]), Omega2), A[3]), A[4]), A[5]);
            U[5][4] = MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], A[1]), A[2]), A[3]), Omega2), A[4]), A[5]);
            U[5][5] = MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], A[1]), A[2]), A[3]), Omega2), A[4]), A[5]);

            // Вычисление матриц (Uijk) преобразования координат от базовой системы к исходной системе координат.
            // Для вращательного типа звеньев
            U1[0][0][0] = U1[1][0][0] = U1[2][0][0] = U1[3][0][0] = U1[4][0][0] = U1[5][0][0] = MatrixMult.multiply(
                    MatrixMult.multiply(A[0], Omega1), Omega1);
            U1[1][0][1] = U1[2][0][1] = U1[3][0][1] = U1[4][0][1] = U1[5][0][1] = MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), Omega1), A[0]);
            U1[2][0][2] = U1[3][0][2] = U1[4][0][2] = U1[5][0][2] = MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), Omega1), A[1]);
            U1[3][0][3] = U1[4][0][3] = U1[5][0][3] = MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), Omega1), A[2]);
            U1[4][0][4] = U1[5][0][4] = MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), Omega1), A[3]);
            U1[5][0][5] = MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), Omega1), A[4]);

            U1[1][1][0] = U1[2][1][0] = U1[3][1][0] = U1[4][1][0] = U1[5][1][0] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(A[0], Omega1), Omega1), A[0]);
            U1[1][1][1] = U1[2][1][1] = U1[3][1][1] = U1[4][1][1] = U1[5][1][1] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[0]), Omega1), A[0]);
            U1[2][1][2] = U1[3][1][2] = U1[4][1][2] = U1[5][1][2] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[0]), Omega1), A[1]);
            U1[3][1][3] = U1[4][1][3] = U1[5][1][3] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[0]), Omega1), A[2]);
            U1[4][1][4] = U1[5][1][4] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[0]), Omega1), A[3]);
            U1[5][1][5] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[0]), Omega1), A[4]);

            U1[2][2][0] = U1[3][2][0] = U1[4][2][0] = U1[5][2][0] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(A[0], Omega1), Omega1), A[1]);
            U1[2][2][1] = U1[3][2][1] = U1[4][2][1] = U1[5][2][1] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[0]), Omega1), A[1]);
            U1[2][2][2] = U1[3][2][2] = U1[4][2][2] = U1[5][2][2] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[1]), Omega1), A[1]);
            U1[3][2][3] = U1[4][2][3] = U1[5][2][3] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[1]), Omega1), A[2]);
            U1[4][2][4] = U1[5][2][4] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[1]), Omega1), A[3]);
            U1[5][2][5] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[1]), Omega1), A[4]);

            U1[3][3][0] = U1[4][3][0] = U1[5][3][0] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(A[0], Omega1), Omega1), A[2]);
            U1[3][3][1] = U1[4][3][1] = U1[5][3][1] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[0]), Omega1), A[2]);
            U1[3][3][2] = U1[4][3][2] = U1[5][3][2] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[1]), Omega1), A[2]);
            U1[3][3][3] = U1[4][3][3] = U1[5][3][3] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[2]), Omega1), A[2]);
            U1[4][3][4] = U1[5][3][4] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[2]), Omega1), A[3]);
            U1[5][3][5] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[2]), Omega1), A[4]);

            U1[4][4][0] = U1[5][4][0] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(A[0], Omega1), Omega1), A[3]);
            U1[4][4][1] = U1[5][4][1] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[0]), Omega1), A[3]);
            U1[4][4][2] = U1[5][4][2] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[1]), Omega1), A[3]);
            U1[4][4][3] = U1[5][4][3] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[2]), Omega1), A[3]);
            U1[4][4][4] = U1[5][4][4] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[3]), Omega1), A[3]);
            U1[5][4][5] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[3]), Omega1), A[4]);

            U1[5][5][0] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(A[0], Omega1), Omega1), A[3]);
            U1[5][5][1] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[0]), Omega1), A[4]);
            U1[5][5][2] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[1]), Omega1), A[4]);
            U1[5][5][3] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[2]), Omega1), A[4]);
            U1[5][5][4] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[3]), Omega1), A[4]);
            U1[5][5][5] = MatrixMult.multiply(MatrixMult.multiply(
                    MatrixMult.multiply(MatrixMult.multiply(A[0], Omega1), A[4]), Omega1), A[4]);

            // Алгоритм вычисления 6 обобщенных сил Q и формирование матриц Alpha и Beta
            double TmpS1 = 0, S1 = 0, S2 = 0, S3 = 0;
            double[] Unit = new double[dim1];

            // Вычисление левой части Уравнения Лагранжа II рода
            for (int s = 0; s < dim1; s++) {
                S1 = 0;
                S2 = 0;
                S3 = 0;

                //for (int k = 0; k < dim1; k++) {Unit[k] = 0;}
                Unit[0] = 0;
                Unit[1] = 0;
                Unit[2] = 0;
                Unit[3] = 0;
                Unit[4] = 0;
                Unit[5] = 0;

                for (int i = s; i < dim1; i++) {
                    for (int j = 0; j <= i; j++) {
                        TmpS1 = MatrixOperations.trace(MatrixMult.multiply(
                                U[i][s], H[i], MatrixOperations.transposition(U[i][j])));
                        switch (j) {
                            case 0:
                                Unit[0] += TmpS1;
                                break;
                            case 1:
                                Unit[1] += TmpS1;
                                break;
                            case 2:
                                Unit[2] += TmpS1;
                                break;
                            case 3:
                                Unit[3] += TmpS1;
                                break;
                            case 4:
                                Unit[4] += TmpS1;
                                break;
                            case 5:
                                Unit[5] += TmpS1;
                                break;
                        }
                    }
                    TmpS1 = 0;
                }

                Alpha.set(s, 0, Unit[0]);
                Unit[0] *= DDqArray[0];
                S1 += Unit[0];
                Alpha.set(s, 1, Unit[1]);
                Unit[1] *= DDqArray[1];
                S1 += Unit[1];
                Alpha.set(s, 2, Unit[2]);
                Unit[2] *= DDqArray[2];
                S1 += Unit[2];
                Alpha.set(s, 3, Unit[3]);
                Unit[3] *= DDqArray[3];
                S1 += Unit[3];
                Alpha.set(s, 4, Unit[4]);
                Unit[4] *= DDqArray[4];
                S1 += Unit[4];
                Alpha.set(s, 5, Unit[5]);
                Unit[5] *= DDqArray[5];
                S1 += Unit[5];

                // Получение второй составляющей левой части Уравнения Лагранжа II рода
                for (int l = s; l < dim1; l++) {
                    for (int m = 0; m <= l; m++) {
                        for (int n = 0; n <= l; n++) {
                            S2 += MatrixOperations.trace(MatrixMult.multiply(U[l][s],
                                    H[l], MatrixOperations.transposition(U1[l][m][n]))) * (DqArray[m]) * (DqArray[n]);
                        }
                    }
                }

                // Получение третьей составляющей левой части Уравнения Лагранжа II рода
                for (int p = s; p < dim1; p++) {
                    Tmp = MatrixMult.multiply(G, U[p][s], R[p]);
                    S3 += Tmp.get(0, 0) * MassArray[p];
                }
                Result[s] = S1 + S2 - S3;
                Beta.set(s, 0, Result[s] - S2 + S3);
            }
        });
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result;
    }

    // Алгоритм решения обратной задачи динамики
    public static double[] backwardTaskSolution(Matrix Alpha, Matrix Beta,
                                                double[] qArray, double[] DqArray, double[] DtArray, StringBuilder OutInfo) {

        double[] Result;
        Matrix InverseMatrixAlpha, TmpResult;

        int dim = Alpha.getRows();
        int count = DtArray.length;
        Result = new double[dim];

        // Алгоритм вычисления Ddq (обобщенного ускорения), где q - обобщенная координата
        InverseMatrixAlpha = MatrixOperations.inverse(Alpha);
        TmpResult = MatrixMult.multiply(InverseMatrixAlpha, Beta);

        for (int i = 0; i < dim; i++) {
            Result[i] = TmpResult.get(i, 0);
        }
        OutInfo.append("\nОбобщенные ускорения: \n");
        SupportFunctions.printArray(Result, OutInfo);

        // Нахождение закона движения (Алгоритм Эйлера)
        // Движение равноускоренное
        double[] TmpqArray1 = qArray;
        double[] TmpDqArray1 = DqArray;
        double[] TmpqArray2 = new double[dim];
        double[] TmpDqArray2 = new double[dim];

        OutInfo.append("Прохождение манипулятором 6 точек\n");
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < dim; j++) {
                TmpqArray2[j] = TmpqArray1[j] + TmpDqArray1[j] * DtArray[i];
                TmpDqArray2[j] = TmpDqArray1[j] + Result[j] * DtArray[i];
            }
            // Вывод информации о приращениях обобщенных координат
            OutInfo.append("----------------------------------------------");
            OutInfo.append("\ndt = ").append(DtArray[i]).append("\nq:\n");
            for (int k = 0; k < dim; k++) OutInfo.append(TmpqArray2[k]).append("\n");
            OutInfo.append("\ndq:\n");
            for (int k = 0; k < dim; k++) OutInfo.append(TmpDqArray2[k]).append("\n");
            //OutInfo.append("----------------------------------------------");

            TmpqArray1 = TmpqArray2;
            TmpDqArray1 = TmpDqArray2;
        }
        return Result;
    }
}
