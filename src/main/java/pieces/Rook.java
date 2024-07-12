package main.java.pieces;

public class Rook extends Piece {
    public Rook(PieceColour colour, PiecePosition position) {
        super(colour, position);
    }

    @Override
    public boolean isValidMove(PiecePosition newPosition, Piece[][] board) {
        // Cannot move to the square it is already on
        if (newPosition.equals(this.position)) {
            return false;
        }
        
        // Horizontal move (row stays the same)
        if (position.getRow() == newPosition.getRow()) {
            // Determine beginning and end points for this move
            int columnStart = Math.min(position.getCol(), newPosition.getCol()) + 1;
            int columnEnd = Math.max(position.getCol(), newPosition.getCol());
            
            // Ensure that the path is clear
            for (int column = columnStart; column < columnEnd; column++) {
                if (board[position.getRow()][column] != null) {
                    // Piece is in the way
                    return false;
                }
            }
        } 
        // Vertical move (column stays the same)
        else if (position.getCol() == newPosition.getCol()) {
            // Determine beginning and end points for this move
            int rowStart = Math.min(position.getRow(), newPosition.getRow());
            int rowEnd = Math.max(position.getRow(), newPosition.getRow());

            // Ensure that the path is clear
            for (int row = rowStart; row < rowEnd; row++) {
                if (board[row][position.getCol()] != null) {
                    // Piece is in the way
                    return false;
                }
            }
        }
        // If the above conditions are not met, then the move is not a straight line
        else {
            return false;
        }

        // Check to see if this is a capture move
        Piece targetPiece = board[newPosition.getRow()][newPosition.getCol()];
        if (targetPiece == null) {
            // Destination is free, move is valid
            return true;
        } else if (targetPiece.getColour() != this.getColour()) {
            // Destination has a piece of opposite colour, move is valid
            return true;
        }

        // Invalid move if no other conditions have been met
        return false;
    }
}
