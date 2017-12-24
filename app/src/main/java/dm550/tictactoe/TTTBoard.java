package dm550.tictactoe;

import android.util.EventLog;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

/** represents a tic tac toe board of a given size */
public class TTTBoard {
    
    /** 2-dimensional array representing the board
     * coordinates are counted from top-left (0,0) to bottom-right (size-1, size-1)
     * board[x][y] == 0   signifies free at position (x,y)
     * board[x][y] == i   for i > 0 signifies that Player i made a move on (x,y)
     */
    public int[][] board = new int[][] {};
    public int[] bestMove = {1, 2}; //AiRecursive
    private int intRecursive = 0;
    private int recursiveResult = 0;
    private ArrayList<move> scoreBoard = new ArrayList<>();
    private int[][] samletScore = new int[3][3];

    private ArrayList<move> eksD = new ArrayList<>();
    
    /** size of the (quadratic) board */
    private int size;
    
    /** constructor for creating a copy of the board
     * not needed in Part 1 - can be viewed as an example 
     */
    public TTTBoard(TTTBoard original) {
        this.size = original.size;
        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                this.board[y][x] = original.board[y][x];
            }
        }
    }
    
    /** constructor for creating an empty board for a given number of players */
    public TTTBoard(int numPlayers) {
        this.size = numPlayers+1;
        this.board = new int[this.getSize()][this.getSize()];
    }
    
    /** checks whether the board is free at the given position */
    public boolean isFree(Coordinate c) {
        if(this.board[c.getX()][c.getY()] == 0)
            return true;
        else
            return false;
    }
    
    /** returns the players that made a move on (x,y) or 0 if the positon is free */
    public int getPlayer(Coordinate c) {
        if (this.board[c.getX()][c.getY()] == 0)
            return 0;
        else
            return this.board[c.getX()][c.getY()];
    }
    
    /** record that a given player made a move at the given position
     * checks that the given positions is on the board
     * checks that the player number is valid 
     */
    public void addMove(Coordinate c, int player, boolean aiOn) {
        if(c.getY() <= getSize()-1 && c.getX() <= getSize()-1){
              try{
                  if(player == 1)
                      this.board[c.getX()][c.getY()] = player;
                  else{
                      if(aiOn && player == 2){
                          eksD.clear();
                          minimax(0, 2, this.board);
                          int max = -100000;
                          int best = -1;
                          for (int i = 0; i < eksD.size(); i++){
                              Log.d(TAG, eksD.get(i).score + " x= " + eksD.get(i).index[0]+ " y= " + eksD.get(i).index[1]);
                              if(max < eksD.get(i).score && this.board[eksD.get(i).index[0]][eksD.get(i).index[1]] == 0){
                                  max = eksD.get(i).score;
                                  best = i;
                              }
                          }
                          //aiRecursive(this.board, player);
                          this.board[eksD.get(best).index[0]][eksD.get(best).index[1]] = player;
                          scoreBoard.clear();
                          samletScore = new int[3][3];
                      }
                      else
                          this.board[c.getX()][c.getY()] = player;


                  }
              }
              catch (IllegalArgumentException e){
                  Log.d(TAG, "Fejl ved add move");
              }
        }
    }

    /** returns true if, and only if, there are no more free positions on the board */
    public boolean checkFull(int[][] board) {
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

    /** returns 0 if no player has won (yet)
     * otherwise returns the number of the player that has three in a row
     */
    public int checkWinning(int[][] thisBoard) {
        int vundet = 0;
        for (int o = 1; o < thisBoard.length-1; o++){ //Tjekker alle felter bortset fra kanter
            for(int u = 1; u < thisBoard[o].length-1; u++){ //tjekker alle falter bortset fra kant
                if(checkSequence(o,u) == 1)
                    vundet = thisBoard[o][u];
                if(vundet != 0)
                    return vundet;
            }
            if(checkHori(o,0) == 1)
                vundet = thisBoard[o][0];
            if(checkHori(o, thisBoard.length-1) == 1)
                vundet = thisBoard[o][thisBoard.length-1];
            if(checkVert(0,o) == 1)
                vundet = thisBoard[0][o];
            if(checkVert(thisBoard.length-1,o) == 1)
                vundet = thisBoard[thisBoard.length-1][o];
        }

        if(vundet != 0)
            return vundet;
        return 0;
    }
    private int checkHori(int dx, int dy){//tjekker den vandret oppe og nede
        if(this.board[dx][dy] == this.board[dx+1][dy] && this.board[dx][dy] == this.board[dx-1][dy] && this.board[dx][dy] != 0) //side-til-side
            return 1;
        else
            return 0;
    }
    private int checkVert(int dx, int dy){ // Tjekker den lodret højre og venstre
        if(this.board[dx][dy] == this.board[dx][dy+1] && this.board[dx][dy] == this.board[dx][dy-1] && this.board[dx][dy] != 0) //side-til-side
            return 1;
        else
            return 0;
    }
    
    /** internal helper function checking one row, column, or diagonal */
    private int checkSequence(int dx, int dy) {
        if(this.board[dx][dy] == this.board[dx+1][dy+1] && this.board[dx][dy] == this.board[dx-1][dy-1] && this.board[dx][dy] != 0) //på skrå
            return 1;
        if(this.board[dx][dy] == this.board[dx+1][dy] && this.board[dx][dy] == this.board[dx-1][dy] && this.board[dx][dy] != 0) //side-til-side
            return 1;
        if(this.board[dx][dy] == this.board[dx][dy+1] && this.board[dx][dy] == this.board[dx][dy-1] && this.board[dx][dy] != 0)//op-til-ned
            return 1;
        if(this.board[dx][dy] == this.board[dx-1][dy+1] && this.board[dx][dy] == this.board[dx+1][dy-1] && this.board[dx][dy] != 0) // på modsat-skrå
            return 1;
        else
            return 0;
    }
    
    /** getter for size of the board */
    public int getSize() {
        return this.size;
    }
    
    /** pretty printing of the board
     * usefule for debugging purposes
     */
    public String toString() {
        String result = "";
        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                result += this.board[y][x]+" ";
            }
            result += "\n";
        }
        return result;
    }
    //AI+


    public int minimax(int depth, int player, int[][]aiBoard){

        List<Integer> scores = new ArrayList<>();
        ArrayList<int[]> availableSpots = new ArrayList<>();
        for (int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                if(aiBoard[x][y] == 0)
                    availableSpots.add(new int[]{x, y});
            }
        }

        if(checkWinning(aiBoard) == 2) return +10;
        else if (checkWinning(aiBoard) == 1) return -10;
        else if(availableSpots.size() == 0) return 0;

        for(int i = 0; i < availableSpots.size(); i++){
            int[] spot = availableSpots.get(i);
            if(player == 2){
                aiBoard[availableSpots.get(i)[0]][availableSpots.get(i)[1]] = player;
                int currentScore = minimax(depth + 1, 1, aiBoard);
                scores.add(currentScore);
                if(depth == 0){
                    int[] index = new int[]{availableSpots.get(i)[0], availableSpots.get(i)[1]};
                    eksD.add(new move(index, currentScore));
                }
            }
            else if(player == 1){
                aiBoard[availableSpots.get(i)[0]][availableSpots.get(i)[1]] = player;
                int currentScore = minimax(depth + 1, 1, aiBoard);
                scores.add(minimax(depth + 1, 2, aiBoard));
            }
            aiBoard[availableSpots.get(i)[0]][availableSpots.get(i)[1]] = 0;
        }
        return player == 2 ? returnMax(scores) : returnMin(scores);
    }
    public int returnMax(List<Integer> list){
        int min = -100000;
        int index = 0;
        for (int x = 0; x < list.size(); x++){

            if(list.get(x) > min ){
                min = list.get(x);
                index = x;
            }
        }
        return list.get(index);
    }
    public int returnMin(List<Integer> list){
        int min = 100000;
        int index = 0;
        for (int x = 0; x < list.size(); x++){
            if(list.get(x) < min ){
                min = list.get(x);
                index = x;
            }
        }
        return list.get(index);
    }






    /*public void aiRecursive(int[][] aiBoard, int spillerensTal){
        //int spillerTal = 1;
        //int aiTal = 2;
        //int result;
        int[] godtFelt = new int[2];

        //int[][] alleScoreBoards = new int[][]{};
        //ArrayList<int[][]> alleScoreBoards = new ArrayList<>();

        if (checkWinning(aiBoard) == 2)
            recursiveResult += 10;
        else if (checkWinning(aiBoard) == 1)
            recursiveResult += -10;
        else if (checkFull(aiBoard)){
            recursiveResult += 0;
        }

        for(int x = 0; x < aiBoard.length; x++){
            for(int y = 0; y < aiBoard[x].length; y++){
                if(aiBoard[x][y] == 0){
                    int[] plads = new int[]{x,y};
                    move nytMove = new move(plads, 0);
                    aiBoard[x][y] = spillerensTal;

                    if(spillerensTal == 2){
                        nytMove.score = recursiveResult;
                        aiRecursive(aiBoard, 1);
                    }
                    else{
                        nytMove.score = recursiveResult;
                        aiRecursive(aiBoard, 2);

                    }

                    scoreBoard.add(nytMove);
                    samletScore[x][y] += scoreBoard.get(scoreBoard.size()-1).score;
                    recursiveResult = 0;
                    aiBoard[x][y] = 0;
                }
            }
        }
        if(spillerensTal == 2){
            int bestScore = -100000;
            for(int x = 0; x < samletScore.length; x++)
                for(int y = 0; y < samletScore[x].length; y++){
                    if(samletScore[x][y] > bestScore){
                        bestScore = samletScore[x][y];
                        godtFelt[0] = x;
                        godtFelt[1] = y;
                    }
                }
        }
        else{
            int bestScore = 100000;
            for(int x = 0; x < samletScore.length; x++)
                for(int y = 0; y < samletScore[x].length; y++){
                    if(samletScore[x][y] < bestScore){
                        bestScore = samletScore[x][y];
                        godtFelt[0] = x;
                        godtFelt[1] = y;
                    }
                }
        }

        Log.d(TAG, samletScore[0][0] + " " + samletScore[0][1] + " " +samletScore[0][2]);
        Log.d(TAG, samletScore[1][0] + " " + samletScore[1][1] + " " +samletScore[1][2]);
        Log.d(TAG, samletScore[2][0] + " " + samletScore[2][1] + " " +samletScore[2][2]);
*//*
        if(spillerensTal == 2){ //Aiens tal er 2
            int bestScore = -10000;
            for (int x = 0; x < scoreBoard.size(); x++){
                    if(scoreBoard.get(x).score > bestScore && this.board[scoreBoard.get(x).index[0]][scoreBoard.get(x).index[1]] == 0){
                        bestScore = scoreBoard.get(x).score;
                        bestMove[0] = scoreBoard.get(x).index[0];
                        bestMove[1] = scoreBoard.get(x).index[1];
                    }
                }
            }
        else{
            int bestScore = 10000;
            for (int x = 0; x < scoreBoard.size(); x++){
                if(scoreBoard.get(x).score < bestScore && this.board[scoreBoard.get(x).index[0]][scoreBoard.get(x).index[1]] == 0){
                    bestScore = scoreBoard.get(x).score;
                    godtFelt[0] = scoreBoard.get(x).index[0];
                    godtFelt[1] = scoreBoard.get(x).index[1];
                }
            }
        }
*//*
        //Log.d(TAG, bestMove[0] + " " + bestMove[1]);
        bestMove[0] = godtFelt[0];
        bestMove[1] = godtFelt[1];
    }
    public void determineBestMove(int[][] scoreBoard, int spillerensTal){


    }*/
}

