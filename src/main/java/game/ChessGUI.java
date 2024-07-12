package main.java.game;

// Standard imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

// Custom imports
import main.java.pieces.Bishop;
import main.java.pieces.King;
import main.java.pieces.Knight;
import main.java.pieces.Pawn;
import main.java.pieces.Piece;
import main.java.pieces.PieceColour;
import main.java.pieces.PiecePosition;
import main.java.pieces.Queen;
import main.java.pieces.Rook;

public class ChessGUI extends JFrame {
    private final ChessSquareComponent[][] squares = new ChessSquareComponent[8][8];
    private final Gameplay game = new Gameplay();
    private final Map<Class<? extends Piece>, String> pieceMap = new HashMap<>() {
        {
            put(Pawn.class, "P");
            put(Rook.class, "R");
            put(Knight.class, "N");
            put(Bishop.class, "B");
            put(Queen.class, "Q");
            put(King.class, "K");
        }
    };

    public ChessGUI() {
        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));
        initialiseBoard();
        addGameResetOption();
        pack();
        setVisible(true);
    }

    private void initialiseBoard() {
        // Initialise each square of the board to be a component with a mouse listener
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                final int finalRow = row;
                final int finalCol = col;
                ChessSquareComponent square = new ChessSquareComponent(row, col);
                
                square.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleSquareClick(finalRow, finalCol);
                    }
                });

                add(square);
                squares[row][col] = square;
            }
        }

        // Draw the board
        refreshBoard();
    }

    private void refreshBoard() {
        // Iterate over every square, checking for piece presence
        // then use the map to update that square with the appropriate symbol
        Board board = game.getBoard();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece != null) {
                    // Piece found, retrieve appropriate symbol and colour
                    String symbol = pieceMap.get(piece.getClass());
                    Color colour = (piece.getColour() == PieceColour.WHITE) ? Color.WHITE : Color.BLUE;
                    
                    // Update squares grid
                    squares[row][col].setPieceSymbol(symbol, colour);
                } else {
                    // Empty square
                    squares[row][col].clearPieceSymbol();
                }
            }
        }
    }

    private void handleSquareClick(int row, int col) {
        // This method bridges user interactions with game logic
        // by determining whether a move has been made and then updating
        // the board
        boolean moveResult = game.handleSquareSelection(row, col);
        clearHighlights();

        // If a move was made, refresh and check game state
        if (moveResult) {
            refreshBoard();
            checkGameState();
            checkGameOver();
        } 
        // If no move was made but a piece was selected, highlight it's legal moves
        else if (game.isPieceSelected()) {
            highlightLegalMoves(new PiecePosition(row, col));
        }
        refreshBoard();
    }


    private void checkGameState() {
        // This method shows the user dialog based on game conditions
        PieceColour currentPlayer = game.getCurrentPlayerColour();
        boolean inCheck = game.isInCheck(currentPlayer);

        if (inCheck) {
            JOptionPane.showMessageDialog(this, currentPlayer + "is in check.");
        }
    }

    private void checkGameOver() {
        // Check if the game is over, displaying options to replay if so
        if (game.isCheckmate(game.getCurrentPlayerColour())) {
            int response = JOptionPane.showConfirmDialog(this, "Checkmate! Would you like to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                resetGame();
            } else {
                System.exit(0);
            }
        }
    }

    private void highlightLegalMoves(PiecePosition position) {
        // Provide visual indication to the user of which moves
        // can be made with selected piece
        List<PiecePosition> legalMoves = game.getLegalMovesForPiece(position);
        for (PiecePosition move : legalMoves) {
            squares[move.getRow()][move.getCol()].setBackground(Color.GREEN);
        }
    }

    private void clearHighlights() {
        // Clear the board of highlights by redrawing the checker pattern
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col].setBackground((row + col) % 2 == 0 ? Color.LIGHT_GRAY : Color.BLACK);
            }
        }
    }

    private void addGameResetOption() {
        // Give the user an option to reset the game
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem resetItem = new JMenuItem("Reset");

        resetItem.addActionListener(e -> resetGame());
        gameMenu.add(resetItem);
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }

    private void resetGame() {
        // Call reset game and refresh board
        game.resetGame();
        refreshBoard();
    }
}
