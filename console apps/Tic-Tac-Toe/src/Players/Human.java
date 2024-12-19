package Players;

import static Statics_constants.Constants.ReadLine;
import static Statics_constants.Constants.TryParseInt;

public class Human extends Player{
    @Override
    public int AskQuestion(String question) {
        System.out.println(question);
        System.out.print("You have to input Yes/yes or No/no: ");
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
    public int AskQuestionInt(String question) {
        System.out.println(question);
        System.out.print("You have to input a correct number: ");
        String ans = ReadLine();
        while (!(TryParseInt(ans))) {
            System.out.print("You have to input a correct number: ");
            ans = ReadLine();
        }
        return Integer.parseInt(ans);
    }
}
