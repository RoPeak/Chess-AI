public class Bishop extends Piece {
    public Bishop(PieceColour colour, PiecePosition position) {
        super(colour, position);
    }

    @Override
    public boolean isValidMove(PiecePosition newPosition, Piece[][] board) {
        // Cannot move to the square it is already on
        if (newPosition.equals(this.position)) {
            return false;
        }
        
        // Calculate difference between new pos and current pos
        int rowDiff = Math.abs(position.getRow() - newPosition.getRow());
        int colDiff = Math.abs(position.getCol() - newPosition.getCol());

        // Check to see if move is non-diagonal
        if (rowDiff != colDiff) {
            return false;
        }

        // Determine which direction the bishop is moving
        int rowStep = newPosition.getRow() > position.getRow() ? 1 : -1;
        int colStep = newPosition.getCol() > position.getCol() ? 1 : -1;

        // Number of squares to check for obstructions
        int steps = rowDiff - 1;

        // Check to see if path is clear
        for (int i = 1; i <= steps; i++) {
            if (board[position.getRow() + i * rowStep][position.getCol() + i * colStep] != null) {
                // Piece in way
                return false;
            }
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
