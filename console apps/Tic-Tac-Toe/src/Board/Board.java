package Board;

import Players.*;
import java.util.Arrays;
import static Statics_constants.Constants.ReadLine;

public class Board {
    private final char[][] board = new char[3][3];
    public Board(){
        initalize();
        printBoard();
    }

    private void initalize(){
        for (char[] chars : board) {
            Arrays.fill(chars, ' ');
        }
    }

    public void initalizePositions(){
        int pos = 49;
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                board[i][j] = (char) pos;
                pos++;
            }
        }
    }

    public void printBoard(){
        for (char[] chars : board) {
            int j = 0;
            System.out.println(chars[j] + "|" + chars[j + 1] + "|" + chars[j + 2]);
            System.out.println("-+-+-");
        }
        System.out.println();
    }

    private boolean isSpaceAvailable(int pos){
        switch (pos) {
            case 1:
                return board[0][0] == ' ';
            case 2:
                return board[0][1] == ' ';
            case 3:
                return board[0][2] == ' ';
            case 4:
                return board[1][0] == ' ';
            case 5:
                return board[1][1] == ' ';
            case 6:
                return board[1][2] == ' ';
            case 7:
                return board[2][0] == ' ';
            case 8:
                return board[2][1] == ' ';
            case 9:
                return board[2][2] == ' ';
            default:
                return false;
        }
    }

    public boolean Insert(int ans, char sign){
        if (isSpaceAvailable(ans)){
            switch (ans) {
                case 1:
                    board[0][0] = sign;
                    break;
                case 2:
                    board[0][1] = sign;
                    break;
                case 3:
                    board[0][2] = sign;
                    break;
                case 4:
                    board[1][0] = sign;
                    break;
                case 5:
                    board[1][1] = sign;
                    break;
                case 6:
                    board[1][2] = sign;
                    break;
                case 7:
                    board[2][0] = sign;
                    break;
                case 8:
                    board[2][1] = sign;
                    break;
                case 9:
                    board[2][2] = sign;
                    break;
            }
            return true;
        }
        return false;
    }

    public void MakeMove(Player player, Player passiveplayer, int counter, int version_ans) {
        if (player instanceof Human){
            // Надо задать вопрос каким знаком хочешь быть и куда ставить
            System.out.println("\nHere are available positions, where you can put your symbol:\n");
            if (counter == 0) {
                initalizePositions();
                printBoard();
                initalize();
            }
            else printBoard();
            int ans = player.AskQuestionInt("Where would you like to put your symbol (" + player.getPlayersign()
                    + ") ? Type in 1-9");
            while (!Insert(ans, player.getPlayersign()) || ans < 1 || ans > 9){
                System.out.println("The position " + ans +" is already occupied or your input is incorrect, try again.");
                ans = player.AskQuestionInt("Where would you like to put your symbol (" + player.getPlayersign()
                        + ") ? Type in 1-9");
            }
            isGameFinished(player, passiveplayer, version_ans);
        }
        else{
            int ans = player.AskQuestion("");
            while (!Insert(ans, player.getPlayersign())){
                ans = player.AskQuestion("");
            }
            System.out.println("\nBot puts it's symbol (" + player.getPlayersign() + ") in position " + ans);
            isGameFinished(player, passiveplayer, version_ans);
        }
        System.out.println("Playing board:\n");
        printBoard();
    }

    private void isGameFinished(Player player, Player passiveplayer, int version_ans) {
        if (player instanceof Human){
            if (CheckForWin(player.getPlayersign())){
                if (version_ans == 1){
                    passiveplayer.MakeLooser("You win, bot has lost. Congrats!!!");
                }
                else{
                    passiveplayer.MakeLooser("Player who played with " +
                            player.getPlayersign() + " wins. Congrats!!!");
                }
                return;
            }
        }
        else{
            if (CheckForWin(player.getPlayersign())){
                passiveplayer.MakeLooser("Bot wins, you lost. That's unfortunate.");
                return;
            }
        }
        // Check for Draw
        for (char[] chars : board) {
            for (char aChar : chars) {
                if (aChar == ' ') {
                    return;
                }
            }
        }
        player.MakeDraw();
        passiveplayer.MakeDraw();
        printBoard();
    }

    private boolean CheckForWin(char playersign) {
        return (board[0][0] == playersign && board[0][1] == playersign && board[0][2] == playersign) ||
                (board[1][0] == playersign && board[1][1] == playersign && board[1][2] == playersign) ||
                (board[2][0] == playersign && board[2][1] == playersign && board[2][2] == playersign) ||

                (board[0][0] == playersign && board[1][0] == playersign && board[2][0] == playersign) ||
                (board[0][1] == playersign && board[1][1] == playersign && board[2][1] == playersign) ||
                (board[0][2] == playersign && board[1][2] == playersign && board[2][2] == playersign) ||

                (board[0][0] == playersign && board[1][1] == playersign && board[2][2] == playersign) ||
                (board[0][2] == playersign && board[1][1] == playersign && board[2][0] == playersign);
    }


    public boolean Continuing() {
        System.out.println("Would you like to continue?");
        String ans = ReadLine();
        while (!ans.equals("No") && !ans.equals("no") && !ans.equals("Yes") && !ans.equals("yes") &&
                !ans.equals("n") && !ans.equals("y") && !ans.equals("-") && !ans.equals("+")) {
            System.out.print("You have to input Yes/yes/y/+ or No/no/n/-: ");
            ans = ReadLine();
        }
        return !ans.equals("No") && !ans.equals("no") && !ans.equals("n") && !ans.equals("-");
    }
}
