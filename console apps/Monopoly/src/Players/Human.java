package Players;

import static Statics_constants.Constants.ReadLine;
import static Statics_constants.Constants.TryParseInt;

// Класс, описывающий живого игрока. Наследник Player.
public class Human extends Player {

    @Override
    public int AskQuestion(String question) {
        System.out.println(question);
        String ans = ReadLine();
        while (!ans.equals("yes") && !ans.equals("Yes") && !ans.equals("no") && !ans.equals("No")) {
            System.out.print("You have to input Yes/yes or No/no: ");
            ans = ReadLine();
        }
        if (ans.equals("Yes") || ans.equals("yes"))
            return 1;
        else
            return 0;
    }

    @Override
    public int AskQuestionInt(String question, int rBorder) {
        System.out.println(question);
        String ans = ReadLine();
        while (!(TryParseInt(ans) && Integer.parseInt(ans) <= rBorder && Integer.parseInt(ans) > 0
                || ans.equals("no") || ans.equals("No"))) {
            System.out.print("You have to input the amount of money you want to loan or No/no " +
                    "amount can't be 0, less than 0 and more than " + rBorder + "$): ");
            ans = ReadLine();
        }
        if (ans.equals("No") || ans.equals("no"))
            return -1;
        else
            return Integer.parseInt(ans);
    }
}
