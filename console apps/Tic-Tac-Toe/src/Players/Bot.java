package Players;

import java.util.Random;

public class Bot extends Player{
    @Override
    public int AskQuestion(String question) {
        return new Random().nextInt(9) + 1;
    }

    @Override
    public int AskQuestionInt(String question) {
        return 0;
    }
}
