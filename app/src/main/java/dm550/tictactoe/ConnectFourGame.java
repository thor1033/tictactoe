package dm550.tictactoe;

/**
 * Created by Thor on 20-12-2017.
 */

public class ConnectFourGame implements  Game {

    /** currently active player */
    private int currentPlayer;

    /** total number of players */
    private int numPlayers;

    /** the board we play on */
    private ConnectFourBoard board;

    /** the gui for board games */
    private UserInterface ui;

    private boolean aiOn; //Tjekker ai checkbox er på

    /** constructor that gets the number of players */
    public ConnectFourGame() {
        this.currentPlayer = 1;
        this.numPlayers = 2;
        this.board = new ConnectFourBoard();
    }

    @Override
    public String getTitle() {
        return this.numPlayers+"-way Tic Tac Toe";
    }

    @Override
    public void addMove(Coordinate pos) {
        this.board.addMove(pos, this.currentPlayer);
        if (this.currentPlayer == this.numPlayers) {
            this.currentPlayer = 1;
        } else {
            this.currentPlayer++;
        }
    }

    @Override
    public String getContent(Coordinate pos) {
        String result = "";
        int player = this.board.getPlayer(pos);
        if (player > 0) {
            result += player;
        }
        return result;
    }

    @Override
    public int getHorizontalSize() {
        return this.board.getSize();
    }

    @Override
    public int getVerticalSize() {
        return this.board.getSize();
    }

    @Override
    public void checkResult() {
        int winner = this.board.checkWinning(this.board.board);
        if (winner > 0) {
            this.ui.showResult("Player "+winner+" wins!");
        }
        else if (this.board.checkFull(this.board.board) && winner == 0 ) {
            this.ui.showResult("This is a DRAW!");
        }
    }

    @Override
    public boolean isFree(Coordinate pos) {
        return this.board.isFree(pos);
    }

    @Override
    public void setUserInterface(UserInterface ui) {
        this.ui = ui;

    }

    public String toString() {
        return "Board before Player "+this.currentPlayer+" of "+this.numPlayers+"'s turn:\n"+this.board.toString();
    }
}
