package Statics_constants;

import java.util.Scanner;

public class Constants {
    public static String ReadLine() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    public static boolean TryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
