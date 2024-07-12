package main.java.pieces;

public class Knight extends Piece {
    public Knight(PieceColour colour, PiecePosition position) {
        super(colour, position);
    }

    @Override
    public boolean isValidMove(PiecePosition newPosition, Piece[][] board) {
        // Cannot move to the square it is already on
        if (newPosition.equals(this.position)) {
            return false;
        }

        // Calculate difference between new pos and current pos
        int rowDiff = Math.abs(this.position.getRow() - newPosition.getRow());
        int colDiff = Math.abs(this.position.getCol() - newPosition.getCol());
    
        // Check for the 'L' that a knight can move in
        boolean isLMove = ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2));
        if (!isLMove) {
            return false;
        }
        
        // Ensure that target square is empty or has a piece of the opposite colour
        Piece targetPiece = board[newPosition.getRow()][newPosition.getCol()];
        if (targetPiece == null) {
            return true;
        } else {
            return targetPiece.getColour() != this.getColour();
        }

    }
}
