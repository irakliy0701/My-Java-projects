package Statics_constants;

import java.util.List;
import java.util.Scanner;
import java.util.Random;

//Класс со статическими методами, используемыми во множестве иных классов программы.
public class Constants {
    private static int height;
    private static int width;
    private static int length;

    public static Random rnd = new Random();

    /**
     * Метод для генерации вещественного значения.
     *
     * @param leftB  Граница генерации слева.
     * @param rightB Граница генерации справа.
     * @return Сгенерированное чисо.
     */
    public static int GenerateMoneyCoeff(double leftB, double rightB) {
        return (int) Math.round(rnd.nextDouble() * (rightB - leftB) + leftB);
    }

    /**
     * Метод для генерации целочисленного значения.
     *
     * @param leftB  Граница генерации слева.
     * @param rightB Граница генерации справа.
     * @return Сгенерированное чисо.
     */
    public static double GenerateCoefficients(double leftB, double rightB) {
        return rnd.nextDouble() * (rightB - leftB) + leftB;
    }

    /**
     * Метод для чтения строки из консоли.
     *
     * @return Строка из консоли.
     */
    public static String ReadLine() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    /**
     * Метод для установки значения высоты поля.
     *
     * @param height1 Значение высоты поля.
     */
    public static void SetHeight(int height1) {
        height = height1;
    }

    /**
     * Метод для установки значения ширины поля.
     *
     * @param width1 Значение ширины поля.
     */
    public static void SetWidth(int width1) {
        width = width1;
    }

    /**
     * Метод для установки значения количества всех элементов поля.
     *
     * @param length1 Количество всех элементов поля.
     */
    public static void SetLength(int length1) {
        length = length1;
    }

    /**
     * Метод для получения значения количества всех элементов поля.
     *
     * @return Количество всех элементов поля.
     */
    public static int GetLength() {
        return length;
    }

    /**
     * Метод для создания индекса ячейки в формате (X,Y) из формата A.
     *
     * @param i Индекс ячейки в формате A.
     * @return Индекс ячейки в формате (X,Y).
     */
    public static String MakeDoubleIndex(int i) {
        if (i < width)
            return ("(0 , " + i + ")");
        if (i < width + height - 1)
            return ("(" + (i - width + 1) + " , " + (width - 1) + ")");
        if (i < length - height + 1)
            return ("(" + (height - 1) + " , " + (length - height + 1 - i) + ")");
        return ("(" + (length - i) + " , 0)");
    }

    /**
     * Метод для получения числа типа int из числа типа double.
     *
     * @param num Число типа int.
     * @return Число типа double.
     */
    public static int MakeInt(double num) {
        return (int) Math.round(num);
    }

    /**
     * Метод для проверки возможности переформатирования из String в Int.
     *
     * @param value Строка на вход.
     * @return True если изменение типа возможно и false в ином случае.
     */
    public static boolean TryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Метод для проверки корректности значений элементов списка.
     *
     * @param list Список.
     * @return True, если все корректно и false  в ином случае.
     */
    public static boolean TryParseIntChecked(List<String> list) {
        try {
            for (int i = 0; i < list.size(); ++i) {
                if (i != 2) {
                    if (Integer.parseInt(list.get(i)) < 6 || Integer.parseInt(list.get(i)) > 30)
                        return false;
                } else {
                    if (Integer.parseInt(list.get(i)) < 500 || Integer.parseInt(list.get(i)) > 15000)
                        return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
