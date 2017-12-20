package dm550.tictactoe;

import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;
/**
 * Created by Thor on 20-12-2017.
 */


public class ConnectFourBoard {
    private int size; // Size

    public int[][] board = new int[][]{
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0}, //Bare for at gøre det visuelt
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
    };
    public  ConnectFourBoard(){
        this.size = 7;
    }
    public boolean isFree(Coordinate c){
        if(this.board[c.getX()][c.getY()] == 0){
            return true;
        }
        else
            return false;
    }
    public int getPlayer(Coordinate c){
        if(this.board[c.getX()][c.getY()]== 0){
            return 0;
        }
        else
            return this.board[c.getX()][c.getY()];
    }
    public void addMove(Coordinate c, int player){
        try{
            for (int y = this.board.length -1; y < this.board[c.getX()].length; y--){
                if(this.board[c.getX()][y] == 0){
                    this.board[c.getX()][y] = player;
                    break;
                }
            }
        }
        catch (IllegalArgumentException e){
            Log.d(TAG, "Fejl ved addMove");
        }

    }
    public  boolean checkFull(int[][] board){
        int count = 0;
        for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
                if (board[x][y] != 0)
                    count = count + 1;
            }
        }
        if (count == size*size)
            return true;
        else
            return false;
    }
    public int checkWinning(int[][] thisBoard){
        int vundet = 0;
        for (int o = 2; o < thisBoard.length - 2; o++){
            for (int u = 2; u < thisBoard.length - 2; u++){
                if(checkSequence(o,u) == 1)
                    vundet = thisBoard[o][u];
                if(vundet != 0)
                    return vundet;
            }
            //if(checkVert(o,0) == 1)
                //vundet = thisBoard[o][0];
            //if(checkVert(o, thisBoard.length-1) == 1)
                //vundet = thisBoard[o][thisBoard.length-1];
            //if(checkHori(0,o) == 1)
                //vundet = thisBoard[0][o];
            //if(checkHori(thisBoard.length-1,o) == 1)
                //vundet = thisBoard[thisBoard.length-1][o];
        }
        vundet = checkDed();
        if (vundet != 0)
            return vundet;
        return 0;
    }
    private int checkHori(int dx, int dy){
        if(this.board[dx][dy] == this.board[dx+1][dy] && this.board[dx][dy] == this.board[dx-1][dy] && this.board[dx-2][dy] == this.board[dx][dy] && this.board[dx][dy] != 0) //side-til-side venstre
            return 1;
        if(this.board[dx][dy] == this.board[dx+1][dy] && this.board[dx][dy] == this.board[dx-1][dy] && this.board[dx+2][dy] == this.board[dx][dy] && this.board[dx][dy] != 0) //side-til-side højre
            return 1;
        else
            return 0;
    }
    private int checkVert(int dx, int dy){
        if(this.board[dx][dy] == this.board[dx][dy+1] && this.board[dx][dy] == this.board[dx][dy-1] && this.board[dx][dy-2] == this.board[dx][dy] && this.board[dx][dy] != 0) //side-til-side nederst
            return 1;
        if(this.board[dx][dy] == this.board[dx][dy+1] && this.board[dx][dy] == this.board[dx][dy-1] && this.board[dx][dy+2] == this.board[dx][dy] && this.board[dx][dy] != 0) //side-til-side øverst
            return 1;
        else
            return 0;
    }
    private int checkDed(){

        
/*        if(this.board[1][1] == this.board[0][1] && this.board[1][1] == this.board[1][2] &&this.board[1][1] == this.board[1][3] && this.board[1][1] != 0) //Dødt felt i vestre hjørne oppe
            return 1;
        if(this.board[1][1] == this.board[1][0] && this.board[1][1] == this.board[2][1] &&this.board[1][1] == this.board[3][1] && this.board[1][1] != 0) //Dødt felt i vestre hjørne oppe
            return 1;
        if(this.board[5][1] == this.board[5][0] && this.board[5][1] == this.board[5][2] &&this.board[5][1] == this.board[5][3] && this.board[5][1] != 0) //Dødt felt i højre hjørne oppe
            return 1;
        if(this.board[5][1] == this.board[6][1] && this.board[5][1] == this.board[4][1] &&this.board[5][1] == this.board[3][1] && this.board[5][1] != 0) //Dødt felt i højre hjørne oppe
            return 1;
        if(this.board[1][5] == this.board[0][5] && this.board[1][5] == this.board[2][5] &&this.board[1][5] == this.board[3][5] && this.board[1][5] != 0) //Dødt felt i vestre hjørne nede
            return 1;
        if(this.board[1][5] == this.board[1][6] && this.board[1][5] == this.board[1][4] &&this.board[1][5] == this.board[1][3] && this.board[1][5] != 0) //Dødt felt i vestre hjørne nede
            return 1;
        if(this.board[5][5] == this.board[5][6] && this.board[5][5] == this.board[5][4] &&this.board[5][5] == this.board[5][4] && this.board[5][5] != 0) //Dødt felt i højre hjørne nede
            return 1;
        if(this.board[5][5] == this.board[6][5] && this.board[5][5] == this.board[4][5] &&this.board[5][5] == this.board[3][5] && this.board[5][5] != 0) //Dødt felt i højre hjørne nede
            return 1;
        else
            return 0;*/

    }
    private int checkSequence (int dx, int dy){
        if(this.board[dx][dy] == this.board[dx+1][dy+1] && this.board[dx][dy] == this.board[dx-1][dy-1] && this.board[dx-2][dy-2] == this.board[dx][dy] && this.board[dx][dy] != 0) //på skrå nederste
            return 1;
        if(this.board[dx][dy] == this.board[dx+1][dy+1] && this.board[dx][dy] == this.board[dx-1][dy-1] && this.board[dx+2][dy+2] == this.board[dx][dy] && this.board[dx][dy] != 0) //på skrå øverste
            return 1;
        if(this.board[dx][dy] == this.board[dx+1][dy] && this.board[dx][dy] == this.board[dx-1][dy] && this.board[dx+2][dy] == this.board[dx][dy] && this.board[dx][dy] != 0) //side-til-side mest til højre
            return 1;
        if(this.board[dx][dy] == this.board[dx+1][dy] && this.board[dx][dy] == this.board[dx-1][dy] && this.board[dx-2][dy] == this.board[dx][dy] && this.board[dx][dy] != 0) //side-til-side mest til venstre
            return 1;
        if(this.board[dx][dy] == this.board[dx][dy+1] && this.board[dx][dy] == this.board[dx][dy-1] && this.board[dx][dy+2] == this.board[dx][dy] && this.board[dx][dy] != 0)//op-til-ned øverst
            return 1;
        if(this.board[dx][dy] == this.board[dx][dy+1] && this.board[dx][dy] == this.board[dx][dy-1] && this.board[dx][dy-2] == this.board[dx][dy] && this.board[dx][dy] != 0)//op-til-ned øverst
            return 1;
        if(this.board[dx][dy] == this.board[dx-1][dy+1] && this.board[dx][dy] == this.board[dx+1][dy-1] && this.board[dx-2][dy+2] == this.board[dx][dy] && this.board[dx][dy] != 0) // på modsat-skrå øverst
            return 1;
        if(this.board[dx][dy] == this.board[dx-1][dy+1] && this.board[dx][dy] == this.board[dx+1][dy-1] && this.board[dx+2][dy-2] == this.board[dx][dy] && this.board[dx][dy] != 0) // på modsat-skrå nederst
            return 1;
        else
            return 0;
    }
    public int getSize(){return this.size;}


}
