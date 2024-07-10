import javax.swing.text.Position;

public class Board {
    private Piece[][] board;

    public Board() {
        board = new Piece[8][8];
        setupPieces();
    }

    public void setupPieces() {
        // Initialise all piece placements
        // Rook placement
        board[0][0] = new Rook(PieceColour.BLACK, new Position(0, 0));
        board[0][7] = new Rook(PieceColour.BLACK, new Position(0, 7));
        board[7][0] = new Rook(PieceColour.WHITE, new Position(7, 0));
        board[7][7] = new Rook(PieceColour.BLACK, new Position(0, 0));

        // Knight placement
        board[0][1] = new Knight(PieceColour.BLACK, new Position(0, 1));
        board[0][6] = new Knight(PieceColour.BLACK, new Position(0, 6));
        board[7][1] = new Knight(PieceColour.WHITE, new Position(7, 1));
        board[7][6] = new Knight(PieceColour.BLACK, new Position(7, 6));

        // Bishop placement
        board[0][2] = new Bishop(PieceColour.BLACK, new Position(0, 2));
        board[0][5] = new Bishop(PieceColour.BLACK, new Position(0, 5));
        board[7][2] = new Bishop(PieceColour.WHITE, new Position(7, 2));
        board[7][5] = new Bishop(PieceColour.WHITE, new Position(7, 5));

        // Queen placement
        board[0][3] = new Queen(PieceColour.BLACK, new Position(0, 3));
        board[7][3] = new Queen(PieceColour.WHITE, new Position(7, 3));

        // King placement
        board[0][4] = new King(PieceColour.BLACK, new Position(0, 4));
        board[7][4] = new King(PieceColour.WHITE, new Position(7, 4));

        // Pawn placement
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(PieceColour.BLACK, new Position(1, i));
            board[6][i] = new Pawn(PieceColour.WHITE, new Position(6, i));
        }
    }
}