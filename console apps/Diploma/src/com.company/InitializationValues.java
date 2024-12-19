package com.company;

public class InitializationValues {
    public static Matrix initializeMatrixA(int dimension, double alpha, double theta, double s, double a) {
        Matrix result = new Matrix(dimension, dimension);

        result.set(0, 0, Math.cos(theta));
        result.set(0, 1, -Math.sin(theta) * Math.cos(alpha));
        result.set(0, 2, Math.sin(theta) * Math.sin(alpha));
        result.set(0, 3, a * Math.cos(theta));
        result.set(1, 0, Math.sin(theta));
        result.set(1, 1, Math.cos(theta) * Math.cos(alpha));
        result.set(1, 2, -Math.cos(theta) * Math.sin(alpha));
        result.set(1, 3, a * Math.sin(theta));
        result.set(2, 1, Math.sin(alpha));
        result.set(2, 2, Math.cos(alpha));

        result.set(2, 0, 0);
        result.set(2, 3, s);
        result.set(3, 0, 0);
        result.set(3, 1, 0);
        result.set(3, 2, 0);
        result.set(3, 3, 1);

        return result;
    }

    public static Matrix initializeMatrixR(int dimension, double x, double y, double z) {
        Matrix result = new Matrix(dimension, dimension);

        result.set(0, 0, x);
        result.set(1, 0, y);
        result.set(2, 0, z);
        result.set(3, 0, 1);

        return result;
    }

    public static Matrix initializeMatrixH(int dimension, double xc, double yc, double zc, double m, double[] R) {
        Matrix result = new Matrix(dimension, dimension);
        Matrix J = new Matrix(dimension - 1, dimension - 1);

        J.set(0, 0, m * (0.5 * (-R[0] + R[1] + R[2])));
        J.set(1, 1, m * (0.5 * (R[0] - R[1] + R[2])));
        J.set(2, 2, m * (0.5 * (R[0] + R[1] - R[2])));

        result.set(0, 0, J.get(0, 0));
        result.set(0, 3, m * xc);
        result.set(1, 1, J.get(1, 1));
        result.set(1, 3, m * yc);
        result.set(2, 2, J.get(2, 2));
        result.set(2, 3, m * zc);
        result.set(3, 0, m * xc);
        result.set(3, 1, m * yc);
        result.set(3, 2, m * zc);
        result.set(3, 3, m);

        return result;
    }

    public static Matrix getMatrixG(int dimension) {
        Matrix result = new Matrix(dimension, dimension);

        result.set(0, 2, -9.81);

        return result;
    }

    public static Matrix getMatrixOmega1(int dimension) {
        Matrix result = new Matrix(dimension, dimension);

        result.set(0, 1, -1);
        result.set(1, 0, 1);

        return result;
    }

    public static Matrix getMatrixOmega2(int dimension) {
        Matrix result = new Matrix(dimension, dimension);

        result.set(2, 3, 1);

        return result;
    }
}
