package dm550.tictactoe;

/**
 * Created by Thor on 13-12-2017.
 */

public class move {
    public int[] index = new int[2];
    public int score;

    public move(int[] Index, int Score){
        this.index = Index;
        this.score = Score;
    }
}