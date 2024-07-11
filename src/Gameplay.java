import javax.swing.text.Position;

public class Gameplay {
    private Board board;
    private boolean whiteTurn = true;
    private PiecePosition selectedPiecePosition;

    public Gameplay() {
        this.board = new Board();
    }

    public Board getBoard() {
        return this.board;
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

    public void resetGame() {
        // Re-initialise board and reset turn to white
        this.board = new Board();
        this.whiteTurn = true;
    }

    public PieceColour getCurrentPlayerColour() {
        // Return White or Black depending on who's turn it is
        return whiteTurn ? PieceColour.WHITE : PieceColour.BLACK;
    }

    public boolean isPieceSelected() {
        return selectedPiecePosition != null;
    }

    public boolean handleSquareSelection(int row, int col) {
        // If no piece is selected, this click will likely be to
        // try and select one - only if there is a piece on this square
        // AND it is the current player's colour
        if (selectedPiecePosition == null) {
            Piece selectedPiece = board.getPiece(row, col);
            if (selectedPiece != null && selectedPiece.getColour() == getCurrentPlayerColour()) {
                selectedPiecePosition = new PiecePosition(row, col);
                // Indicate that a piece has been selected but not moved
                return false;
            }
        } 
        // If a piece has been selected, this click will likely be to move it
        else {
            // Attempt to make move, reset the selected piece regardless of result,
            // then return success/failure
            boolean moveMade = makeMove(selectedPiecePosition, new PiecePosition(row, col));
            selectedPiecePosition = null; 
            return moveMade;
        }

        // Return false if no accepting condition was met
        return false;
    }
}