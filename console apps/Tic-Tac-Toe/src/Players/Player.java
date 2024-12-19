package Players;

public abstract class Player {
    private boolean looser = false;
    private boolean draw = false;
    private String looseMessage;
    private char playersign;

    public void MakeLooser(String looseMessage) {
        looser = true;
        this.looseMessage = looseMessage;
    }

    public boolean IsLooser() {
        return looser;
    }

    public String LooseMessage() {
        return looseMessage;
    }

    public void MakeDraw() {
        draw = true;
    }

    public boolean IsDraw() {
        return draw;
    }

    public abstract int AskQuestion(String question);
    public abstract int AskQuestionInt(String question);

    public char getPlayersign(){
        return playersign;
    }

    public void setPlayersign(char playersign){
        this.playersign = playersign;
    }
}
