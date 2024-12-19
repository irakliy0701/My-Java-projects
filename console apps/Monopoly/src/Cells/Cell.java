package Cells;

import Players.Player;

// Абстрактныйй класс, описывающий общие действия с клеткой.
public abstract class Cell {

    /**
     * Метод, в котором происходит осуществление действий любой клетки из игры.
     *
     * @param player        Основной игрок, который сейчас на клетке.
     * @param passivePlayer Пассивный игрок, не осуществляющий ход в данный момент.
     */
    public abstract void ActionCell(Player player, Player passivePlayer);

    /**
     * Метод для получения имени клетки. Нужен для отсутствия нарушения инкапсуляции.
     *
     * @return Имя клетки.
     */
    public abstract String Name();

}