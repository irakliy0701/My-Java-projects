package Cells;

import Players.*;

import static Statics_constants.Constants.*;


// Класс, описывающий ячейку Shop, наследник Cell.
public class Shop extends Cell {
    private String name = "S"; // Поле с именем клетки.
    private int N; // Поле, хранящее знаенчие стоимости магазина.
    private int K; // Поле, хранящее значение компенсации. Выплачивается в случае остановки игрока на клетке соперника.
    private final double improvementCoeff; // Коэффициент для улучшения магазина.
    private final double compensationCoeff; // Коэффициент для увеличения компенсации.

    /**
     * Метод для получения имени клетки магазина. Нужен для отсутствия нарушения инкапсуляции.
     *
     * @return Имя клетки магазина.
     */
    @Override
    public String Name() {
        return name;
    }

    /**
     * Метод, в котором происходят события клетки Shop, в момент, когда на нее попадает игрок.
     *
     * @param player        Основной игрок, который сейчас на клетке.
     * @param passivePlayer Пассивный игрок, не осуществляющий ход в данный момент.
     */
    @Override
    public void ActionCell(Player player, Player passivePlayer) {
        if (player instanceof Human)
            System.out.println("You are in the shop cell " + MakeDoubleIndex(player.Index()) + ".");
        if (name.equals("S"))
            CaseOldName(player);
        else if (name.equals("M"))
            CaseNameM(player, passivePlayer);
        else
            CaseNameO(player, passivePlayer);
    }

    /**
     * Случай, когда Магазин не имеет владельца.
     *
     * @param player Основной игрок, то есть тот, кто сейчас делает ход.
     */
    public void CaseOldName(Player player) {
        if (player instanceof Human)
            System.out.print("This shop has no owner. ");
        if (player.Available(N)) { // Проверка платежеспособности игрока.
            System.out.println("Your balance is " + player.Money() + "$.");
            int res = player.AskQuestion("Would you like to buy the shop for " + N + "$?\n" +
                    "Input 'Yes' if you are eager to do it or 'No' otherwise.");
            if (res == 1) { // Случай, когда игрок решил купить магазин.
                if (player instanceof Human) {
                    name = "M";
                    System.out.println("Now this shop is yours!");
                } else {
                    name = "O";
                    System.out.println("Bot has bought the shop!");
                }
                player.SetSaveBalance(player.Money()); // Сохраняем баланс игрока.
                player.SetSpentMoney(player.SpentMoney() + N); // Записываем количество денег, потраченное игроком.
                player.SetMoney(player.Money() - N); // Изменяем баланс игрока.
            } else {
                if (player instanceof Bot)
                    System.out.println("Bot decided not to bye the shop.");
            }
        } else {
            if (player instanceof Human) {
                System.out.println("You can't afford buying this shop as it costs: " +
                        N + ", but you have just " + player.Money() + ".");
            }
        }
    }

    /**
     * Случай, когда владелец магазина - игрок. ( Рассматриваю отдельно, т.к. сообщения, выводящиеся в консоль,
     * различаются в зависимости от владельца)
     *
     * @param player        Основной игрок, который сейчас на клетке.
     * @param passivePlayer Пассивный игрок, не осуществляющий ход в данный момент.
     */
    public void CaseNameM(Player player, Player passivePlayer) {
        if (player instanceof Human) { // Случай, когда игрок попал на свою клетку магазина.
            System.out.print("This shop is yours. ");
            if (player.Available(MakeInt(N * improvementCoeff))) { // Проверка способности игрока улучшить магазин.
                System.out.println("Your balance is " + player.Money() + "$.");
                    int res = player.AskQuestion("Would you like to upgrade it for " +
                            MakeInt(N * improvementCoeff) + "$?\nIf you upgrade your opponent will pay " +
                            "you " + MakeInt(K + K * compensationCoeff) + "$ after using it instead of " +
                            K + "$.\nInput 'Yes' if you are eager to do it or 'No' otherwise.");
                if (res == 1) { // Случай, когда игрок решает улучшить магазин.
                    HumanUpdate(player);
                }
            } else {
                System.out.println("You can't afford upgrading this shop as it costs " +
                        MakeInt(N * improvementCoeff) + ", but you have just " + player.Money() + ".");
            }
        } else {
            if (player.Available(K)) {
                System.out.println("Bot pays you " + K + "$ as he uses your shop.");
                player.SetSaveBalance(player.Money()); // Сохраняем баланс.
                player.SetMoney(player.Money() - K); // Обновляем баланс.
                passivePlayer.SetSaveBalance(passivePlayer.Money()); // Сохраняем баланс.
                passivePlayer.SetMoney(passivePlayer.Money() + K); // Обновляем баланс.
            } else
                player.MakeLooser("bot has no money to pay your for the shop " +
                        "as its usage costs " + K + "$ but bot has just " + player.Money() + "$.");
        }
    }

    /**
     * Метод описывающий действия в случае желания игрока улучшить свой магазин.
     *
     * @param player Игрок (не бот), стоящий на клетке.
     */
    public void HumanUpdate(Player player) {
        System.out.print("You've upgraded the shop successfully. ");
        System.out.println("Now you shop's price is " + MakeInt(N + N * improvementCoeff) + "$.");
        player.SetSaveBalance(player.Money()); // Сохраняем баланс.
        player.SetMoney(player.Money() - MakeInt(N * improvementCoeff)); // Обновляем баланс.
        // Обнрвляем количество потраченных на покупку/улучшение магазинов денег.
        player.SetSpentMoney(player.SpentMoney() + MakeInt(N * improvementCoeff));
        N += MakeInt(improvementCoeff * N); // Обновляем стоимость.
        K += MakeInt(compensationCoeff * K); // Обновляем компенсацию.
    }

    /**
     * Случай, когда магазин принадлежит боту. (Аналогичные действия, но с иными сообщениями о происходящем в консоль)
     *
     * @param player        Основной игрок, который сейчас на клетке.
     * @param passivePlayer Пассивный игрок, не осуществляющий ход в данный момент.
     */
    public void CaseNameO(Player player, Player passivePlayer) {
        if (player instanceof Bot) {
            if (player.Available(MakeInt(N * improvementCoeff))) {
                int res = player.AskQuestion("Would you like to upgrade it for " +
                        MakeInt(N * improvementCoeff) + " $?\n" +
                        "Input ‘Yes’ if you are eager to do it or ‘No’ otherwise");
                if (res == 1) {
                    BotUpdate(player);
                } else
                    System.out.println("Bot have decided not to upgrade the shop.");
            } else
                System.out.println("Your opponent has no money to upgrade the shop.");
        } else {
            if (player.Available(K)) {
                System.out.println("You pay bot " + K + "$ as you use his shop.");
                player.SetSaveBalance(player.Money());
                player.SetMoney(player.Money() - K);
                passivePlayer.SetSaveBalance(passivePlayer.Money());
                passivePlayer.SetMoney(passivePlayer.Money() + K);
            } else
                player.MakeLooser("you have no money to pay your opponent for his shop" +
                        " as its usage costs " + K + "$ but you have just " + player.Money() + "$.");
        }
    }

    /**
     * Метод описывающий действия в случае желания игрока улучшить свой магазин.
     *
     * @param player Бот, осуществляющий ход.
     */
    public void BotUpdate(Player player) {
        player.SetSpentMoney(player.SpentMoney() + MakeInt(N * improvementCoeff));
        System.out.print("Bot has upgraded the shop for " + (N * improvementCoeff) + "$.");
        System.out.println("You will pay your opponent " + (K * compensationCoeff) + "$ after getting here.");
        player.SetSaveBalance(player.Money());
        player.SetMoney(player.Money() - MakeInt(N * improvementCoeff));
        N += MakeInt(improvementCoeff * N);
        K += MakeInt(compensationCoeff * K);
    }

    /*
        Конструктор для задания начальных стоимости и компенсации магазина, а также
        коэффициентов увеличения стоимости и компенсации магазина. (Задается в конструкторе для уникальности)
     */
    public Shop() {
        N = GenerateMoneyCoeff(50, 500);
        K = GenerateMoneyCoeff(0.5 * N, 0.9 * N);
        improvementCoeff = GenerateCoefficients(0.1, 2);
        compensationCoeff = GenerateCoefficients(0.1, 1);
    }
}
