package Players;

import static Statics_constants.Constants.rnd;


// Класс, описывающий бота. Наследник Player.
public class Bot extends Player {

    /**
     * Метод для вывода вопроса в консоль и считывания ответа в формате String.
     *
     * @param question Текста вопроса.
     * @return Ответ на вопрос.
     */
    @Override
    public int AskQuestion(String question) {
        return rnd.nextInt(2);
    }

    /**
     * Метод для вывода вопроса в консоль и считывания ответа в формате Integer.
     *
     * @param question Текста вопроса.
     * @param rBorder  Гранца возможного ответа.
     * @return -3, случайное число. Нам не нужен ответ бота в данном методе.
     */
    @Override
    public int AskQuestionInt(String question, int rBorder) {
        return -3;
    }
}

