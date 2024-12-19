package Cells;

import Players.*;

import static Statics_constants.Constants.*;


// Класс, описывающий ячейку такси. Наследник Cell.
public class Taxi extends Cell {
    /**
     * Метод для получения имени клетки такси. Нужен для отсутствия нарушения инкапсуляции.
     *
     * @return Имя Клетки такси.
     */
    @Override
    public String Name() {
        return "T";
    }

    /**
     * @param player        Основной игрок, который сейчас на клетке.
     * @param passivePlayer Пассивный игрок, не осуществляющий ход в данный момент.
     */
    @Override
    public void ActionCell(Player player, Player passivePlayer) {
        // Переменная, хранящая дистанцию, на которую перевозит такси. (Генерируем тут для постоянной уникальности)
        int taxiDistance = rnd.nextInt(3) + 3;
        player.SetIndex((player.Index() + taxiDistance) % GetLength()); // Перемещение игрока.
        if (player instanceof Human)
            System.out.println("You are in the taxi cell and will be shifted forward " +
                    "by " + taxiDistance + " cells to the cell " + MakeDoubleIndex(player.Index()) + ".");
        else
            System.out.println("Bot is in the taxi cell and will be " +
                    "shifted forward by " + taxiDistance + " cells.");
    }
}
