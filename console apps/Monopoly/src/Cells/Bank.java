package Cells;

import Players.*;

import static Statics_constants.Constants.MakeInt;


// Класс, описывающий ячейку банка. Наследник Cell.
public class Bank extends Cell {
    private final double creditCoeff; // Коэффициент для определения максимально возможной суммы в долг.
    private final double debtCoeff; // Коэффициент кредитования, задается по условию.

    /**
     * Метод для получения имени клетки банка. Нужен для отсутствия нарушения инкапсуляции.
     *
     * @return Имя Клетки банка.
     */
    @Override
    public String Name() {
        return "$";
    }

    /**
     * Метод, в котором происходят события клетки Bank, в момент, когда на нее попадает игрок.
     *
     * @param player        Основной игрок, стоящий на клетке.
     * @param passivePlayer Игрок, не осуществляющий ход в данный момент.
     */
    @Override
    public void ActionCell(Player player, Player passivePlayer) {
        if (player instanceof Human) {
            System.out.print("You are in the bank office. ");
            if (player.Debt() > 0)
                Debtor(player);
            else
                NewComer(player);
        } else
            System.out.println("Bot is in the bank office.");
    }

    /**
     * Метод, в котором рассматривается случай, когда игрок является должником банка.
     *
     * @param player Игрок, стоящий на клетке банка.
     */
    public void Debtor(Player player) {
        if (player.Available(player.Debt())) { // Проверка платежеспособности клиента.
            System.out.println("A loan of " + player.Debt() + "$ will be debited from your account.");
            player.SetMoney(player.Money() - player.Debt()); // Вычет долга.
            player.SetDebt(0);
        } else
            player.MakeLooser("you have no money to pay off your debts as your" +
                    " balance is " + player.Money() + "$ but you have to pay " +
                    player.Debt() + "$.");
    }

    /**
     * Метод в котором рассматривается случай, когда игрок не является должником банка.
     *
     * @param player Игрок, стоящий на клетке банка.
     */
    public void NewComer(Player player) {
        if (player.SpentMoney() != 0) { // Проверка платежеспособности клиента.
            int res = player.AskQuestionInt("Would you like to get a credit? You can take a loan for " +
                    "no more than " + MakeInt(creditCoeff * player.SpentMoney()) + "$. " +
                    "Input how many you want to get or 'No'.", MakeInt(creditCoeff * player.SpentMoney()));
            if (res != -1) { // Случай когда игрок согласился взять кредит.
                player.SetSaveBalance(player.Money()); // Запоминаем бывший баланс игрока.
                player.SetDebt(MakeInt(res * debtCoeff)); // Установка долга.
                player.SetMoney(player.Money() + res); // Обновляем баланс.
                System.out.println("You'll have to pay " + MakeInt(res * debtCoeff) + "$ " +
                        "the next time you go to the bank.");
            }
        } else {
            System.out.println("You have not bought any shops yet therefore credit is not available.");
        }
    }

    // Конструктор для задания коэффициентов кредитования.
    public Bank(double creditCoeff, double debtCoeff) {
        this.creditCoeff = creditCoeff;
        this.debtCoeff = debtCoeff;
    }
}