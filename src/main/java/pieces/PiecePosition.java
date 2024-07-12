package pieces;

public class PiecePosition {
    private int row;
    private int col;

    public PiecePosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }
}
