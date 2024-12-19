package Cells;

import Players.*;

// Класс, описывающий ячейку EmptyCell. Наследник Cell.
public class EmptyCell extends Cell {

    /**
     * Метод для получения имени клетки EmptyCell. Нужен для отсутствия нарушения инкапсуляции.
     *
     * @return Имя клетки.
     */
    @Override
    public String Name() {
        return "E";
    }

    /**
     * Метод, в котором происходят события клетки EmptyCell, в момент, когда на нее попадает игрок.
     *
     * @param player        Основной игрок, который сейчас на клетке.
     * @param passivePlayer Пассивный игрок, не осуществляющий ход в данный момент.
     */
    @Override
    public void ActionCell(Player player, Player passivePlayer) {
        if (player instanceof Human) {
            System.out.println("Just relax there.");
        } else {
            System.out.println("Bot is relaxing.");
        }
    }
}
