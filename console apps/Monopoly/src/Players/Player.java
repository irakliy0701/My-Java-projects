package Players;

//Абстрактный класс, описывающий игрока.
public abstract class Player {
    private int money;
    private boolean looser = false;
    private int debt = 0;
    private String looseMessage;
    private int spentMoney = 0;
    private int index = 0;
    private int saveBalance = -1;

    /**
     * Метод для получения баланса игрока.
     *
     * @return Значение баланса игрока.
     */
    public int Money() {
        return money;
    }

    /**
     * Метод для установки баланса игрока.
     *
     * @param sum Значение баланса игрока.
     */
    public void SetMoney(int sum) {
        money = sum;
    }

    /**
     * Метод для получения значения баланса игрока до хода.
     *
     * @return Баланс игрока до хода.
     */
    public int SaveBalance() {
        return saveBalance;
    }

    /**
     * Метод для устанвовки значения баланса игрока до хода.
     *
     * @param sum Баланс игрока до хода.
     */
    public void SetSaveBalance(int sum) {
        saveBalance = sum;
    }

    /**
     * Метод для установки значения поля looser на true и поля looseMessage на причину поражения, если игрок проиграл.
     *
     * @param looseMessage Причина поражения.
     */
    public void MakeLooser(String looseMessage) {
        looser = true;
        this.looseMessage = looseMessage;
    }

    /**
     * Метод для проверки отсутствия факта проигрыша игрока.
     *
     * @return True, если игрок проиграл и false в ином случае.
     */
    public boolean IsLooser() {
        return looser;
    }

    /**
     * Метод для чтения причины поражения игрока.
     *
     * @return Причина поражения игрока.
     */
    public String LooseMessage() {
        return looseMessage;
    }

    /**
     * Метод для получения значения долга игрока перед банком.
     *
     * @return Долг игрока перед банком.
     */
    public int Debt() {
        return debt;
    }

    /**
     * Метод для установки значения долга игрока перед банком.
     *
     * @param sum Значегние для установки долга игрока перед банком.
     */
    public void SetDebt(int sum) {
        debt = sum;
    }

    /**
     * Метод для получения значения потраченных на покупку/улучшение магазинов денег.
     *
     * @return Потраченные на покупку/улучшение магазинов деньги.
     */
    public int SpentMoney() {
        return spentMoney;
    }

    /**
     * Метод для установки значения потраченных на покупку/улучшение магазинов денег.
     *
     * @param sum Число для установки.
     */
    public void SetSpentMoney(int sum) {
        spentMoney = sum;
    }

    /**
     * Метод для получения текущего индекса игрока.
     *
     * @return Индекс.
     */
    public int Index() {
        return index;
    }

    /**
     * Метод для задания положения игрока на поле.
     *
     * @param ind Индекс текущей клетки.
     */
    public void SetIndex(int ind) {
        index = ind;
    }

    /**
     * Метод для проверки платежеспособности игрока.
     *
     * @param sum Баланс игрока.
     * @return True, если может заплатить и false в ином случае.
     */
    public boolean Available(double sum) {
        return money - sum >= 0;
    }

    /**
     * Метод для вывода вопроса в консоль и считывания ответа в формате String.
     *
     * @param question Текста вопроса.
     * @return Ответ на вопрос.
     */
    public abstract int AskQuestion(String question);

    /**
     * Метод для вывода вопроса в консоль и считывания ответа в формате Integer.
     *
     * @param question Текста вопроса.
     * @param rBorder  Гранца возможного ответа.
     * @return Ответ на вопрос.
     */
    public abstract int AskQuestionInt(String question, int rBorder);
}