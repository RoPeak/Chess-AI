public class Gameplay {
    private Board board;
    private boolean whiteTurn = true;

    public Gameplay() {
        this.board = new Board();
    }

    public boolean makeMove(PiecePosition start, PiecePosition end) {
        Piece movingPiece = board.getPiece(start.getRow(), start.getCol());
        
        // Ensure space is not empty or piece is wrong colour
        if (movingPiece == null || movingPiece.getColour() != (whiteTurn ? PieceColour.WHITE : PieceColour.BLACK)) {
            return false;
        }

        // Ensure desired move is valid for this given piece
        if (movingPiece.isValidMove(end, board.getBoard())) {
            // Move piece and swap turn
            board.movePiece(start, end);
            whiteTurn = !whiteTurn;

            return true;
        }

        // Incorrect move if no condition has been met
        return false;
    }

    private PiecePosition findKingPosition(PieceColour kingColour) {
        // Iterate through the board and find the given coloured king
        for (int row = 0; row < board.getBoard().length; row++) {
            for (int col = 0; col < board.getBoard()[row].length; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece instanceof King && piece.getColour() == kingColour) {
                    return new PiecePosition(row, col);
                }
            } 
        }
        throw new RuntimeException("King not on board");
    }

    private boolean isPositionOnBoard(PiecePosition position) {
        // Return whether the given position is a valid position in our 2D grid
        return position.getRow() >= 0 && position.getRow() < board.getBoard().length 
        && position.getCol() >= 0 && position.getCol() < board.getBoard()[0].length;
    }

    public boolean isInCheck(PieceColour kingColour) {
        // Iterate through each square, checking if that piece is checking the king
        PiecePosition kingPosition = findKingPosition(kingColour);
        for (int row = 0; row < board.getBoard().length; row++) {
            for (int col = 0; col < board.getBoard()[row].length; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece != null && piece.getColour() != kingColour) {
                    if (piece.isValidMove(kingPosition, board.getBoard())) {
                        // A piece of the opposite colour is able to capture the king (!)
                        return true;
                    }
                }
            }
        }
        // No piece checking the king was found
        return false;
    }

    private boolean wouldBeInCheckAfterMove(PieceColour kingColour, PiecePosition from, PiecePosition to) {
        // Simulate move
        Piece temp = board.getPiece(to.getRow(), to.getCol());
        board.setPiece(to.getRow(), to.getCol(), board.getPiece(from.getRow(), from.getCol()));
        board.setPiece(from.getRow(), from.getCol(), null);
        boolean inCheck = isInCheck(kingColour);

        // Undo move
        board.setPiece(from.getRow(), from.getCol(), board.getPiece(to.getRow(), to.getCol()));
        board.setPiece(to.getRow(), to.getCol(), temp);

        return inCheck;
    }

    public boolean isCheckmate(PieceColour kingColour) {
        if (!isInCheck(kingColour)) {
            // Not in check so not checkmate
            return false;
        }

        // Select the king
        PiecePosition kingPosition = findKingPosition(kingColour);
        King king = (King) board.getPiece(kingPosition.getRow(), kingPosition.getCol());

        // Attempt to find a move that gets the king out of check
        for (int rowOffSet = -1; rowOffSet <= 1; rowOffSet++) {
            for (int colOffSet = -1; colOffSet <= 1; colOffSet++) {
                if (rowOffSet == 0 && colOffSet == 0) {
                    // Skip current king position
                    continue;
                }
                
                // Check if this new move is valid and does not result in check
                PiecePosition newPosition = new PiecePosition(kingPosition.getRow() + rowOffSet, kingPosition.getCol() + colOffSet);
                if (isPositionOnBoard(newPosition) && king.isValidMove(newPosition, board.getBoard())
                    && !wouldBeInCheckAfterMove(kingColour, kingPosition, newPosition)) {
                        // Appropriate move found, so not checkmate
                        return false;
                }
            }
        }

        // No legal moves available, game over
        return true;
    }
}