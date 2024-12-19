package Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;

import Cells.*;
import Players.*;

import static Statics_constants.Constants.*;

/**
 * @author <a href="mailto:mishklyar@edu.hse.ru"> Mike Shklyar</a>
 */

// Класс игрового поля.
public class GameField {
    // Список клеток игрового поля. (Делаем его одномерным для экономии памяти)
    private final List<Cell> listCells = new ArrayList<>(0);
    private final List<Cell> helpListCell = new ArrayList<>(0); // Вспомогательный список.
    private final int height, width; // Констатные поля со значениями высоты и ширины поля соответственно.

    // Конструктор для создания поля, задания нчального кол-ва денег игрокам и выводу начального поля.
    public GameField(int height, int width, int plMoney, Human human, Bot bot) {
        this.height = height;
        this.width = width;
        SetHeight(height);
        SetWidth(width);
        SetLength(2 * width + 2 * height - 4); // Число клеток в поле.
        human.SetMoney(plMoney); // Задаем деньги игроку.
        bot.SetMoney(plMoney); // Задаем деньги боту.
        InitializeField(); // Создаем поле.
        System.out.println("The starting playing field:");
        PrintList(); // Выводим начальное поле в консоль.
    }

    /**
     * Метод для создания игрового поля.
     */
    public void InitializeField() {
        double penaltyCoeff = GenerateCoefficients(0.01, 0.1); // Генерируем штрафной коэффициент.
        System.out.println("Penalty coefficient = " + String.format("%.3f", penaltyCoeff) + ".");
        double creditCoeff = GenerateCoefficients(0.002, 0.2); // Генерируем коэффициент маx кредитования.
        System.out.println("Credit coefficient in Bank = " + String.format("%.3f", creditCoeff) + ".");
        double debtCoeff = GenerateCoefficients(1.0, 3.0); // Генерируем коэффициент кредитования.
        System.out.println("Debt coefficient in Bank = " + String.format("%.3f", debtCoeff) + ".");

        for (int i = 0; i < 4; ++i) {
            int freeCells = width - 3; // Число клеток, доступных для такси и штрафов.
            if (i == 1 || i == 3)
                freeCells = height - 3;
            int taxiNumb, penaltyNumb;
            int[] arrDet = TaxiAndPenaltyDetection(freeCells);
            taxiNumb = arrDet[0];
            penaltyNumb = arrDet[1];
            freeCells = arrDet[2];
            for (int ind = 0; ind < taxiNumb; ++ind) {
                helpListCell.add(new Taxi());
            }
            for (int ind = 0; ind < penaltyNumb; ++ind) {
                helpListCell.add(new PenaltyCell(penaltyCoeff));
            }
            for (int ind = 0; ind < freeCells; ++ind) {
                helpListCell.add(new Shop());
            }
            Collections.shuffle(helpListCell);
            if (i == 0)
                listCells.add(new EmptyCell());
            listCells.add(new Bank(creditCoeff, debtCoeff));
            listCells.addAll(helpListCell);
            if (i != 3)
                listCells.add(new EmptyCell());
            helpListCell.clear();
        }
    }

    /**
     * Метод для опредения числа штрфных клеток и клеток такси на поле.
     *
     * @param freeCells Чисо доступных для такси/штафов клеток.
     * @return Массив со всеми необходимыми данными.
     */
    public int[] TaxiAndPenaltyDetection(int freeCells) {
        int taxiNumb, penaltyNumb;
        int[] arrRet = new int[3];
        if (height == 6) {
            if (rnd.nextInt(2) == 0) {
                taxiNumb = rnd.nextInt(3);
                penaltyNumb = rnd.nextInt(2);
            } else {
                taxiNumb = rnd.nextInt(2);
                penaltyNumb = rnd.nextInt(3);
            }
        } else {
            taxiNumb = rnd.nextInt(3);
            penaltyNumb = rnd.nextInt(3);
        }
        freeCells -= (taxiNumb + penaltyNumb);
        arrRet[0] = taxiNumb;
        arrRet[1] = penaltyNumb;
        arrRet[2] = freeCells;
        return arrRet;
    }

    /**
     * Метод для вывода игрового поля в консоль. (осуществляется по строкам)
     */
    public void PrintList() {
        char[] cs = new char[(width - 2) * 2 + 1]; // Массив с пробелами для вывода определенных значений.
        Arrays.fill(cs, ' ');
        String sGaps = new String(cs);
        for (int ind = 2 * width + height - 2; ind < listCells.size(); ++ind) {
            helpListCell.add(listCells.get(ind));
        }
        helpListCell.add(helpListCell.get(0));
        Collections.reverse(helpListCell);
        for (int ind = width; ind < width + height - 1; ++ind) {
            helpListCell.add(listCells.get(ind));
        }
        for (int i = 0; i < height; i++) {
            if (i == 0) {
                for (int j = 0; j < width - 1; ++j) {
                    System.out.print(listCells.get(j).Name() + " ");
                }
                System.out.print(listCells.get(width - 1).Name() + "\n");
            } else if (i < height - 1) {
                System.out.println(helpListCell.get(i).Name() + sGaps + helpListCell.get(i + height - 2).Name());
            } else {
                helpListCell.clear();
                for (int ind = width + height - 2; ind < 2 * width + height - 2; ++ind) {
                    helpListCell.add(listCells.get(ind));
                }
                Collections.reverse(helpListCell);
                for (int j = 0; j < helpListCell.size() - 1; ++j) {
                    System.out.print(helpListCell.get(j).Name() + " ");
                }
                System.out.println(helpListCell.get(helpListCell.size() - 1).Name());
                helpListCell.clear();
            }
        }
    }

    /**
     * Осуществление хода.
     *
     * @param player        Основной игрок, делающий ход.
     * @param passivePlayer Пассивный игрок, не осуществляющий ход.
     */
    public void MakeMove(Player player, Player passivePlayer) {
        int cube1 = rnd.nextInt(6) + 1, cube2 = rnd.nextInt(6) + 1; // Кидаем кубики.
        if (player instanceof Human) {
            System.out.print("You are rolling dices. Score on the first dice: " + cube1 + ". " +
                    "Score on the second dice: " + cube2 + ". Total: " + (cube1 + cube2) + ".\nSo you " +
                    "are moving from the cell " + MakeDoubleIndex(player.Index()));
        } else {
            System.out.print("Bot is rolling dices. Score on the first dice: " + cube1 + ". " +
                    "Score on the second dice: " + cube2 + ". Total: " + (cube1 + cube2) + ".\nSo he " +
                    "is moving from the cell " + MakeDoubleIndex(player.Index()));
        }
        player.SetIndex((player.Index() + cube1 + cube2) % GetLength()); // Передвигаем игрока на сумму выпавших очков.
        System.out.println(" to the cell " + MakeDoubleIndex(player.Index()) + ".");
        while (listCells.get(player.Index()) instanceof Taxi) {
            listCells.get(player.Index()).ActionCell(player, passivePlayer);
        }
        listCells.get(player.Index()).ActionCell(player, passivePlayer); // Вызываем функцию клетки.
        System.out.println("Playing field:");
        PrintList();
        if (!player.IsLooser() && !passivePlayer.IsLooser())
            MakeFinish(player, passivePlayer);
        player.SetSaveBalance(-1);
        passivePlayer.SetSaveBalance(-1);
        System.out.println();
    }

    /**
     * Метод для возможности остановить игру после каждого хода. (для медленной версии игры)
     *
     * @return Было ли принято реение об остановке. (false, если да)
     */
    public boolean Continuing() {
        System.out.println("Would you like to continue?");
        String ans = ReadLine();
        while (!ans.equals("No") && !ans.equals("no") && !ans.equals("Yes") && !ans.equals("yes") &&
                !ans.equals("n") && !ans.equals("y") && !ans.equals("-") && !ans.equals("+")) {
            System.out.print("You have to input Yes/yes/y/+ or No/no/n/-: ");
            ans = ReadLine();
        }
        System.out.println();
        return !ans.equals("No") && !ans.equals("no") && !ans.equals("n") && !ans.equals("-");
    }

    /**
     * Вывод информации после хода в консоль.
     *
     * @param player        Основной игрок, осуществляющий ход.
     * @param passivePlayer Пасиивный игрок.
     */
    public static void MakeFinish(Player player, Player passivePlayer) {
        if (player instanceof Human)
            FinishForHuman(player, passivePlayer);
        else
            FinishForBot(player, passivePlayer);
    }

    /**
     * Вывод в слуае, если основной игрок - человек.
     *
     * @param player        Человек.
     * @param passivePlayer Бот.
     */
    public static void FinishForHuman(Player player, Player passivePlayer) {
        System.out.println("Current place of the player: " + MakeDoubleIndex(player.Index()) + ".");
        System.out.println("Current place of the bot: " + MakeDoubleIndex(passivePlayer.Index()) + ".");
        if (player.SaveBalance() != -1 && player.SaveBalance() != player.Money()) {// Сравнение баланса до и после хода.
            if ((player.Money() == player.Money() && player.SaveBalance() == player.SaveBalance()))
                System.out.println("Your balance have changed from: " + player.SaveBalance() + "$ to " +
                        player.Money() + "$.");
            else
                System.out.println("Your balance have changed from: " + player.SaveBalance()
                        + "$ to " + player.Money() + "$.");
        } else {
            if (player.Money() == player.Money())
                System.out.println("Your balance remained unchanged: " + player.Money() + "$.");
            else
                System.out.println("Your balance remained unchanged: " + player.Money() + "$.");
        }
        if (passivePlayer.SaveBalance() != -1 && passivePlayer.SaveBalance() != passivePlayer.Money()) {
            if (passivePlayer.Money() == passivePlayer.Money() &&
                    passivePlayer.SaveBalance() == passivePlayer.SaveBalance())
                System.out.println("Bot's balance have changed from: " + passivePlayer.SaveBalance() +
                        "$ to " + passivePlayer.Money() + "$.");
            else
                System.out.println("Bot's balance have changed from: " +
                        passivePlayer.SaveBalance() + "$ to " + passivePlayer.Money() + "$.");
        }
    }

    /**
     * Вывод в слуае, если основной игрок - бот.
     *
     * @param player        Бот.
     * @param passivePlayer Человек.
     */
    public static void FinishForBot(Player player, Player passivePlayer) {
        System.out.println("Current place of the bot: " + MakeDoubleIndex(player.Index()) + ".");
        System.out.println("Current place of the player: " + MakeDoubleIndex(passivePlayer.Index()) + ".");
        if (player.SaveBalance() != -1 && player.SaveBalance() != player.Money()) {// Сравнение баланса до и после хода.
            if (player.Money() == player.Money() && player.SaveBalance() == player.SaveBalance())
                System.out.println("Bot's balance have changed from: " + player.SaveBalance() + "$ to " +
                        player.Money() + "$.");
            else
                System.out.println("Bot's balance have changed from: " + player.SaveBalance()
                        + "$ to " + player.Money() + "$.");
        } else {
            if (player.Money() == player.Money())
                System.out.println("Bot's balance remained unchanged: " + player.Money() + "$.");
            else
                System.out.println("Bot's balance remained unchanged: " + player.Money() + "$.");
        }
        if (passivePlayer.SaveBalance() != -1 && passivePlayer.SaveBalance() != passivePlayer.Money()) {
            if (passivePlayer.Money() == passivePlayer.Money() &&
                    passivePlayer.SaveBalance() == passivePlayer.SaveBalance())
                System.out.println("Your balance have changed from: " + passivePlayer.SaveBalance() +
                        "$ to " + passivePlayer.Money() + "$.");
            else
                System.out.println("Your balance have changed from: " + passivePlayer.SaveBalance()
                        + "$ to " + passivePlayer.Money() + "$.");
        }
    }
}
