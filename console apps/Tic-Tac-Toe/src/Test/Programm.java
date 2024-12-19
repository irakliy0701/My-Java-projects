package Test;

import Players.*;
import Board.Board;
import static Statics_constants.Constants.*;

public class Programm {
    public static void main(String[] args) {
        System.out.println("\nThis is our initial raw board:\n");
        Board board = new Board();
        MakeMoves(board, ChooseVersion());
    }

    public static int ChooseVersion() {
        System.out.print("Choose version of the game:\n1 version - you'll play against PC"+
                "\n2 version - you'll play against another user (or yourself)\nversion = ");
        String answer = ReadLine();
        while (!answer.equals("1") && !answer.equals("2")) {
            System.out.print("You have to input 1 or 2: ");
            answer = ReadLine();
        }
        return Integer.parseInt(answer);
    }

    public static void MakeMoves(Board board, int version_ans) {
        Human human = new Human();
        System.out.println("\nLet's start!");
        int moveQueue = human.AskQuestion("Would you like to start first ?");
        int counter = 0;
        if (version_ans == 1) { // версия против бота
            Bot bot = new Bot();
            int symbol = human.AskQuestionInt("\nChoose the symbol that you'll be playing with:\n1 - you'll play with(as) X"
                    +"\n2 - you'll play with(as) O");
            while (symbol != 1 && symbol != 2){
                symbol = human.AskQuestionInt("You have to input 1 or 2 !");
            }
            if (symbol == 1){
                human.setPlayersign('X');
                bot.setPlayersign('O');
            }
            else{
                human.setPlayersign('O');
                bot.setPlayersign('X');
            }
            System.out.println("\nYou can input just y/+ not to waste time for continuing.");
            while (!human.IsLooser() && !bot.IsLooser() && !human.IsDraw() && !bot.IsDraw() && board.Continuing()) {
                if (moveQueue == 1) {
                    board.MakeMove(human, bot, counter, version_ans);
                    counter++;
                }
                else {
                    board.MakeMove(bot, human, counter, version_ans);
                    counter++;
                }
                moveQueue = (moveQueue + 1) % 2;
            }
            finishOfProgram(human, bot);
        }
        else { // версия против другого игрока.
            Human human2 = new Human();
            int symbol = human.AskQuestionInt("Choose the symbol that you'll be playing with:\n1 - you'll play with(as) X"
                    +"\n2 - you'll play with(as) O");
            while (symbol != 1 && symbol != 2){
                symbol = human.AskQuestionInt("Choose the symbol that you'll be playing with:\n1 - you'll play with(as) X"
                        +"\n2 - you'll play with(as) O");
            }
            if (symbol == 1){
                human.setPlayersign('X');
                System.out.println("Then the other player will play with \"O\"");
                human2.setPlayersign('O');
            }
            else {
                human.setPlayersign('O');
                System.out.println("Then the other player will play with \"X\"");
                human2.setPlayersign('X');
            }
            System.out.println("You can input just y/+ not to waste time for continuing.");
            while (!human.IsLooser() && !human2.IsLooser() && !human.IsDraw() && !human2.IsDraw() && board.Continuing()) {
                if (moveQueue == 1) {
                    board.MakeMove(human, human2, counter, version_ans);
                    counter++;
                }
                else{
                    board.MakeMove(human2, human, counter, version_ans);
                    counter++;
                }
                moveQueue = (moveQueue + 1) % 2;
            }
            finishOfProgram(human, human2);
        }
    }

    public static void finishOfProgram(Human human, Player player) {
        if (human.IsLooser()) {
            System.out.println(human.LooseMessage());
        } else if (player.IsLooser()) {
            System.out.println(player.LooseMessage());
        }
        else if (human.IsDraw() || player.IsDraw()){
            System.out.println("The game ended in tie");
        }
        else {
            System.out.println("You've decided to leave the game. Let's take it as a draw.");
        }
    }
}
