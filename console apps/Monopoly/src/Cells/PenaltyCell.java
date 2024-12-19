package Cells;

import Players.*;

import static Statics_constants.Constants.MakeInt;


// Класс, описывающий ячейку PenaltyCell. Наследник Cell.
public class PenaltyCell extends Cell {
    private final double penaltyCoeff; // Коэффициент штрафа.

    /**
     * Метод для получения имени клетки EmptyCell. Нужен для отсутствия нарушения инкапсуляции.
     *
     * @return Имя клетки EmptyCell.
     */
    @Override
    public String Name() {
        return "%";
    }

    /**
     * Метод, в котором происходят события клетки PenaltyCell, в момент, когда на нее попадает игрок.
     *
     * @param player        Основной игрок, который сейчас на клетке.
     * @param passivePlayer Пассивный игрок, не осуществляющий ход в данный момент.
     */
    @Override
    public void ActionCell(Player player, Player passivePlayer) {
        if (player instanceof Human)
            System.out.println("You must pay a fine of " + MakeInt(penaltyCoeff * player.Money()) + "$.");

        else
            System.out.println("Bot pays a fine of " + MakeInt(penaltyCoeff * player.Money()) + "$.");
        player.SetSaveBalance(player.Money()); // Запоминаем баланс игрока.
        player.SetMoney(player.Money() - MakeInt(penaltyCoeff * player.Money())); // Изменяем баланс игрока.
    }

    // Конструктор для задания коэффициента для штрафа.
    public PenaltyCell(double penaltyCoeff) {
        this.penaltyCoeff = penaltyCoeff;
    }
}

