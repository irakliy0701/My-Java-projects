package Game;

import static Statics_constants.Constants.ReadLine;
import static Statics_constants.Constants.rnd;
import static Statics_constants.Constants.TryParseIntChecked;

import Players.*;
import Field.GameField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


// Главный класс программы с мейном.
public class Program {
    public static void main(String[] args) {
        int[] arrData = new int[3];
        try {
            arrData = ReadData(args);
        } catch (IllegalArgumentException ex) {
            System.out.println();
            System.out.println(ex.getMessage());
            System.out.println("The program has finished. To start again you have to rerun it with right parameters.");
            System.exit(1);
        }
        int height = arrData[0], width = arrData[1], money = arrData[2];
        Human human = new Human();
        Bot bot = new Bot();
        GameField gf = new GameField(height, width, money, human, bot);
        int moveQueue = rnd.nextInt(2);
        MakeMoves(human, bot, gf, moveQueue, ChooseVersion());
        FinishOfTheProgram(human, bot);
    }

    /**
     * Метод, считывающий значение для выбора версии игры.
     *
     * @return Значение выбранной версии.
     */
    public static int ChooseVersion() {
        System.out.println("Choose version of the game:\n1 version (slow) - you'll be able to stop the game at any " +
                "moment you like.\n2 version (fast) - you'll just be asked questions when it is necessary and " +
                "won't be able to finish the game yourself.");
        String answer = ReadLine();
        while (!answer.equals("1") && !answer.equals("2")) {
            System.out.print("You have to input 1 or 2: ");
            answer = ReadLine();
        }
        return Integer.parseInt(answer);
    }

    /**
     * Метод для завершения программы в завсисимости от победителя.
     *
     * @param human Объект класса Human.
     * @param bot   Объект класса Bot.
     */
    public static void FinishOfTheProgram(Human human, Bot bot) {
        if (human.IsLooser()) {
            System.out.println("It's a pity but " + human.LooseMessage());
            System.out.println("I am sure you'll have better luck next time.");
        } else if (bot.IsLooser()) {
            System.out.println("CONGRATULATIONS!");
            System.out.println("You have one the game because " + bot.LooseMessage() + "\nThat was great!");
        } else {
            System.out.println("You've decided to leave the game. Let's take it as a draw.");
        }
    }

    /**
     * Метод для считывания данных из терминала(командной строки).
     *
     * @param args Аргументы терминала/командной строки.
     * @return Массив с корректными параметрами высоты, ширины и началной суммы денег.
     */
    public static int[] ReadData(String[] args) {
        List<String> listData = new ArrayList<>(0);
        listData.addAll(Arrays.asList(args));
        if (args.length > 3 || !TryParseIntChecked(listData))
            throw new IllegalArgumentException("You have to input 3 integer numbers divided by gap: <height> " +
                    "<width> <money>.\nWhere 6 <= height <= 30; 6 <= width <= 30; 500 <= money <= 15000.");
        if (listData.size() == 2) {
            System.out.println("No initial amount of money was entered. We'll assign a value of 1500$ to it.");
            ;
            listData.add("1500");
        } else if (listData.size() == 1) {
            System.out.println("The initial amount of money and width of the field were not entered." +
                    "\nWe'll assign a value of 1500 to the money and a value of 10 to the width.");
            listData.add("10");
            listData.add("1500");
        } else if (listData.size() == 0) {
            System.out.println("The initial amount of money width and height of the field were not entered." +
                    "\nWe'll assign a value of 1500 to the money and a value of 10 to the height and to the width.");
            listData.add("10");
            listData.add("10");
            listData.add("1500");
        }
        int[] arrData = new int[3];
        for (int i = 0; i < 3; ++i) {
            arrData[i] = Integer.parseInt(listData.get(i));
        }
        return arrData;
    }

    /**
     * Метод для осуществления ходов.
     *
     * @param human     Объект класса Human.
     * @param bot       Объект класса Bot.
     * @param gf        Объект класса GameField.
     * @param moveQueue Переменная очередности ходов.
     * @param ans       Переменная выбора версии игры.
     */
    public static void MakeMoves(Human human, Bot bot, GameField gf, int moveQueue, int ans) {
        System.out.println("Let's start!");
        if (ans == 1) { // Медленная версия.
            System.out.println("You can input just y/+ not to waste time for continuing.");
            while (!human.IsLooser() && !bot.IsLooser() && gf.Continuing()) {
                if (moveQueue == 0)
                    gf.MakeMove(human, bot);
                else
                    gf.MakeMove(bot, human);
                moveQueue = (moveQueue + 1) % 2;
            }
        } else { //Быстрая версия.
            while (!human.IsLooser() && !bot.IsLooser()) {
                if (moveQueue == 0)
                    gf.MakeMove(human, bot);
                else
                    gf.MakeMove(bot, human);
                moveQueue = (moveQueue + 1) % 2;
            }
        }
    }
}
