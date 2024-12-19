package com.company;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final int dim = 4, dim1 = 6;
    public Matrix MatrixAlpha = new Matrix(dim1, dim1), MatrixBeta = new Matrix(dim1, dim1),
            MatrixG, MatrixOmega1, MatrixOmega2;
    public Matrix[] AArray = new Matrix[dim1], RArray = new Matrix[dim1], HArray = new Matrix[dim1];
    public double[] QArray = new double[dim1], BackddqArray = new double[dim1], MassArray = new double[dim1],
            alphaArray = new double[dim1] , aArray = new double[dim1], sArray = new double[dim1],
            dtArray = new double[dim1], q = new double[dim1], dq = new double[dim1] ,
            ddq = new double[dim1], tArray = new double[dim1];
    public double[][] CMCoordinatesArray = new double[dim1][dim - 1], InertialRadiusArray = new double[dim1][dim - 1];
    public StringBuilder seqSolutionTaskInfo;
    public StringBuilder multiSolutionTaskInfo;
    public int numberOfThreads;
    public String version, zakonIzmenCoords;

    private void DataInitialize(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nВыберете версию алгоритма для тестирования: послед/паралл.\n" +
                "\"послед\" - для запуска последовательных алгоритмов решения прямой и обраной задач динамики.\n" +
                "\"паралл\" - для запуска многопоточного алгоритма решения прямой задачи динамики.");
        System.out.print("Введите значение версии: ");
        version = scanner.nextLine();

        if (version.equals("паралл")){
            System.out.print("\nВведите количество (целое число, начиная с 2) потоков, на котором будет запускаться параллельный алгоритм.");
            System.out.print("\nКоличество потоков = ");
            numberOfThreads = Integer.parseInt(scanner.nextLine());
        }

        System.out.print("\nВыберете значение закона изменения обобщенных координат: sin/cos \n" +
                "\"sin\" - для закона изменения по синусу (sin(t)).\n" +
                "\"cos\" - для закона изменения по косинусу (cos(t)).");
        System.out.print("\nВведите значение закона: ");
        zakonIzmenCoords = scanner.nextLine();

        System.out.println("\nВведите начальные значения (вещественные числа) времени для 6 обобщенных координат:");
        System.out.print("t_1 = ");
        tArray[0] = scanner.nextDouble();
        System.out.print("t_2 = ");
        tArray[1] = scanner.nextDouble();
        System.out.print("t_3 = ");
        tArray[2] = scanner.nextDouble();
        System.out.print("t_4 = ");
        tArray[3] = scanner.nextDouble();
        System.out.print("t_5 = ");
        tArray[4] = scanner.nextDouble();
        System.out.print("t_6 = ");
        tArray[5] = scanner.nextDouble();

        System.out.println("\nЗадание подробных характеристик робота-манипулятора.");
        System.out.println("Введите значения (вещественные числа) масс (в кг) для 6 звеньев манипулятора:");
        System.out.print("m_1 = ");
        MassArray[0] = scanner.nextDouble();
        System.out.print("m_2 = ");
        MassArray[1] = scanner.nextDouble();
        System.out.print("m_3 = ");
        MassArray[2] = scanner.nextDouble();
        System.out.print("m_4 = ");
        MassArray[3] = scanner.nextDouble();
        System.out.print("m_5 = ");
        MassArray[4] = scanner.nextDouble();
        System.out.print("m_6 = ");
        MassArray[5] = scanner.nextDouble();

        System.out.println("\nВведите значения (вещественные числа) центров масс (в метрах) по X, Y, Z для 6 звеньев манипулятора:");
        System.out.print("x_1 = ");
        CMCoordinatesArray[0][0] = scanner.nextDouble();
        System.out.print("y_1 = ");
        CMCoordinatesArray[0][1] = scanner.nextDouble();
        System.out.print("z_1 = ");
        CMCoordinatesArray[0][2] = scanner.nextDouble();

        System.out.print("x_2 = ");
        CMCoordinatesArray[1][0] = scanner.nextDouble();
        System.out.print("y_2 = ");
        CMCoordinatesArray[1][1] = scanner.nextDouble();
        System.out.print("z_2 = ");
        CMCoordinatesArray[1][2] = scanner.nextDouble();

        System.out.print("x_3 = ");
        CMCoordinatesArray[2][0] = scanner.nextDouble();
        System.out.print("y_3 = ");
        CMCoordinatesArray[2][1] = scanner.nextDouble();
        System.out.print("z_3 = ");
        CMCoordinatesArray[2][2] = scanner.nextDouble();

        System.out.print("x_4 = ");
        CMCoordinatesArray[3][0] = scanner.nextDouble();
        System.out.print("y_4 = ");
        CMCoordinatesArray[3][1] = scanner.nextDouble();
        System.out.print("z_4 = ");
        CMCoordinatesArray[3][2] = scanner.nextDouble();

        System.out.print("x_5 = ");
        CMCoordinatesArray[4][0] = scanner.nextDouble();
        System.out.print("y_5 = ");
        CMCoordinatesArray[4][1] = scanner.nextDouble();
        System.out.print("z_5 = ");
        CMCoordinatesArray[4][2] = scanner.nextDouble();

        System.out.print("x_6 = ");
        CMCoordinatesArray[5][0] = scanner.nextDouble();
        System.out.print("y_6 = ");
        CMCoordinatesArray[5][1] = scanner.nextDouble();
        System.out.print("z_6 = ");
        CMCoordinatesArray[5][2] = scanner.nextDouble();

        System.out.println("\nВведите значения (вещественные числа) радиусов инерции (в метрах) по X, Y, Z для 6 звеньев манипулятора:");
        System.out.print("k_1(x) = ");
        InertialRadiusArray[0][0] = scanner.nextDouble();
        System.out.print("k_1(y) = ");
        InertialRadiusArray[0][1] = scanner.nextDouble();
        System.out.print("k_1(z) = ");
        InertialRadiusArray[0][2] = scanner.nextDouble();

        System.out.print("k_2(x) = ");
        InertialRadiusArray[1][0] = scanner.nextDouble();
        System.out.print("k_2(y) = ");
        InertialRadiusArray[1][1] = scanner.nextDouble();
        System.out.print("k_2(z) = ");
        InertialRadiusArray[1][2] = scanner.nextDouble();

        System.out.print("k_3(x) = ");
        InertialRadiusArray[2][0] = scanner.nextDouble();
        System.out.print("k_3(y) = ");
        InertialRadiusArray[2][1] = scanner.nextDouble();
        System.out.print("k_3(z) = ");
        InertialRadiusArray[2][2] = scanner.nextDouble();

        System.out.print("k_4(x) = ");
        InertialRadiusArray[3][0] = scanner.nextDouble();
        System.out.print("k_4(y) = ");
        InertialRadiusArray[3][1] = scanner.nextDouble();
        System.out.print("k_4(z) = ");
        InertialRadiusArray[3][2] = scanner.nextDouble();

        System.out.print("k_5(x) = ");
        InertialRadiusArray[4][0] = scanner.nextDouble();
        System.out.print("k_5(y) = ");
        InertialRadiusArray[4][1] = scanner.nextDouble();
        System.out.print("k_5(z) = ");
        InertialRadiusArray[4][2] = scanner.nextDouble();

        System.out.print("k_6(x) = ");
        InertialRadiusArray[5][0] = scanner.nextDouble();
        System.out.print("k_6(y) = ");
        InertialRadiusArray[5][1] = scanner.nextDouble();
        System.out.print("k_6(z) = ");
        InertialRadiusArray[5][2] = scanner.nextDouble();

        System.out.println("\nВведите значения (вещественные числа) углов \u03B1 (в градусах) для 6 звеньев манипулятора:");
        System.out.print("\u03B1_1 = ");
        alphaArray[0] = scanner.nextDouble();
        System.out.print("\u03B1_2 = ");
        alphaArray[1] = scanner.nextDouble();
        System.out.print("\u03B1_3 = ");
        alphaArray[2] = scanner.nextDouble();
        System.out.print("\u03B1_4 = ");
        alphaArray[3] = scanner.nextDouble();
        System.out.print("\u03B1_5 = ");
        alphaArray[4] = scanner.nextDouble();
        System.out.print("\u03B1_6 = ");
        alphaArray[5] = scanner.nextDouble();

        scanner.close();

        if (zakonIzmenCoords.equals("sin")){
            for (int i = 0; i< dim1; i++){
                q[i] = Math.sin(tArray[i]);
            }
            for (int i = 0; i < dim1; i++)
            {
                dq[i] = Math.cos(tArray[i]);
                ddq[i] = -Math.sin(tArray[i]);
            }
        } else if (zakonIzmenCoords.equals("cos")){
            for (int i = 0; i< dim1; i++){
                q[i] = Math.cos(tArray[i]);
            }
            for (int i = 0; i < dim1; i++)
            {
                dq[i] = -Math.sin(tArray[i]);
                ddq[i] = -Math.cos(tArray[i]);
            }
        }
        sArray = new double[] { 4.044, 3.052, 0, 0, 0, 2.152 };
        aArray = new double[] { -3.006, 1.015, 0, -1.176, 2.072, 0 };
        dtArray = new double[] { 0.05, 0.04, 0.03, 0.02, 0.01, 0.07 };

        // Инициализация  матриц
        MatrixG = InitializationValues.getMatrixG(dim);
        MatrixOmega1 = InitializationValues.getMatrixOmega1(dim);
        MatrixOmega2 = InitializationValues.getMatrixOmega2(dim);

        for (int i = 0; i < dim1; i++)
        {
            AArray[i] = InitializationValues.initializeMatrixA(
                    dim, alphaArray[i], q[i], sArray[i], aArray[i]);
        }

        for (int i = 0; i < dim1; i++)
        {
            RArray[i] = InitializationValues.initializeMatrixR(
                    dim, CMCoordinatesArray[i][0], CMCoordinatesArray[i][1], CMCoordinatesArray[i][2]);
        }

        for (int i = 0; i < dim1; i++)
        {
            HArray[i] = InitializationValues.initializeMatrixH(dim, CMCoordinatesArray[i][0],
                    CMCoordinatesArray[i][1], CMCoordinatesArray[i][2], MassArray[i], InertialRadiusArray[i]);
        }

        StringBuilder dataInfo = new StringBuilder("\n**** Начальные параметры для закона изменения " + zakonIzmenCoords +
                "(t) ****\n");
        dataInfo.append("Обобщенные координаты: \n");
        SupportFunctions.printCoords(q, tArray, dataInfo);
        dataInfo.append("Обобщенные скорости: \n");
        SupportFunctions.printArray(dq, dataInfo);
        dataInfo.append("Обобщенные ускорения: \n");
        SupportFunctions.printArray(ddq, dataInfo);
        System.out.println(dataInfo);
    }

    // Запуск последовательной версии алгоритма решения прямой задачи динамики
    private void sequentialAlgorithmRun(){
        seqSolutionTaskInfo = new StringBuilder("**** Результаты выполнения последовательного алгоритма ****\n");

        // Выполнение ПЗД:
        seqSolutionTaskInfo.append("\nРешение прямой задачи");
        seqSolutionTaskInfo.append("\nОбобщенные силы: \n");

        //long t0 = System.nanoTime();

        QArray = ComputationalAlgorithms.seqDirectTaskSolution(MatrixAlpha, MatrixBeta, dq, ddq,
                MassArray, MatrixG, MatrixOmega1, MatrixOmega2, AArray, RArray, HArray);

        //long t1 = System.nanoTime();
        //long dif = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        //System.out.printf("\n\nSequential pure algorithm took: %d ms\n\n", dif);

        SupportFunctions.printArray(QArray, seqSolutionTaskInfo);

        // Выполнение ОЗД:
        seqSolutionTaskInfo.append("\nРешение обратной задачи");
        BackddqArray = ComputationalAlgorithms.backwardTaskSolution(MatrixAlpha, MatrixBeta,
                tArray, dq, dtArray,  seqSolutionTaskInfo);
        System.out.println(seqSolutionTaskInfo);
    }

    // Запуск многопоточной версии алгоритма решения прямой задачи динамики
    private void multithreadedAlgorithmRun(){
        multiSolutionTaskInfo = new StringBuilder("**** Результаты выполнения многопоточного алгоритма ****\n");

        // Выполнение ПЗД:
        multiSolutionTaskInfo.append("\nРешение прямой задачи");
        multiSolutionTaskInfo.append("\nОбобщенные силы: \n");

        //long t0 = System.nanoTime();

        QArray = ComputationalAlgorithms.multiDirectTaskSolution(numberOfThreads, MatrixAlpha, MatrixBeta, dq, ddq,
                MassArray, MatrixG, MatrixOmega1, MatrixOmega2, AArray, RArray, HArray);

        //long t1 = System.nanoTime();
        //long dif = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        //System.out.printf("\n\nMultithreaded pure algorithm took: %d ms\n\n", dif);

        SupportFunctions.printArray(QArray, multiSolutionTaskInfo);

        System.out.println(multiSolutionTaskInfo);
    }

    public static void main(String[] args) {
        Main myMain = new Main();
        myMain.DataInitialize();

        if (myMain.version.equals("послед")){
            myMain.sequentialAlgorithmRun();
        } else if (myMain.version.equals("паралл")){
            myMain.multithreadedAlgorithmRun();
        }
    }
}

