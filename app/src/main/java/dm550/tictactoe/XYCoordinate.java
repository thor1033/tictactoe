package dm550.tictactoe;

public class XYCoordinate implements Coordinate {

    /** variables specifying horizontal position on the board */
    private int x;

    /** variable specifying vertical positoin on the board */
    private int y;

    /** constructor creating a Coordinate from x and y values */
    public XYCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public boolean checkBoundaries(int xSize, int ySize) {
        if(this.getX() <= xSize-1 && this.getY() <= ySize-1)
            return true;
        else
            return false;
    }

    @Override
    public Coordinate shift(int dx, int dy) {
        x += dx;
        y += dy;
        return this;

    }
}
