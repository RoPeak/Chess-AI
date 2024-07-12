package pieces;

public class Queen extends Piece {
    public Queen(PieceColour colour, PiecePosition position) {
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
        
        // Check for straight line movement
        boolean straightLine = this.position.getRow() == newPosition.getRow() || this.position.getCol() == newPosition.getCol();

        // Check for diagonal line movement
        boolean diagonalLine = rowDiff == colDiff;

        // Invalid if move is neither straight nor diagonal
        if (!straightLine && !diagonalLine) {
            return false;
        }

        // Determine direction of movement
        int rowDirection = Integer.compare(newPosition.getRow(), this.position.getRow());
        int colDirection = Integer.compare(newPosition.getCol(), this.position.getCol());

        // Check for obstructions
        int currentRow = this.position.getRow() + rowDirection;
        int currentCol = this.position.getCol() + colDirection;
        while (currentRow != newPosition.getRow() || currentCol != newPosition.getCol()) {
            if (board[currentRow][currentCol] != null) {
                // Piece is in the way
                return false;
            }
            currentRow += rowDirection;
            currentCol += colDirection;
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
