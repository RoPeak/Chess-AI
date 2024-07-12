public class King extends Piece {
    public King(PieceColour colour, PiecePosition position) {
        super(colour, position);
    }

    @Override
    public boolean isValidMove(PiecePosition newPosition, Piece[][] board) {
        // Calculate difference between new pos and current pos
        int rowDiff = Math.abs(position.getRow() - newPosition.getRow());
        int colDiff = Math.abs(position.getCol() - newPosition.getCol());

        // Kings can only move one square in any direction
        boolean isOneSquare = rowDiff <= 1 && colDiff <= 1 && !(rowDiff == 0 && colDiff == 0);
        if (!isOneSquare) {
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
