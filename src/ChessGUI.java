import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.util.HashMap;

public class ChessGUI extends JFrame {
    private final ChessSquareComponent[][] squares = new ChessSquareComponent[8][8];
    private final Gameplay game = new Gameplay();
    private final Map<Class<? extends Piece>, String> pieceMap = new HashMap<>() {
        {
            put(Pawn.class, "\u265F");
            put(Rook.class, "\u265C");
            put(Knight.class, "\u265E");
            put(Bishop.class, "\u265D");
            put(Queen.class, "\u265B");
            put(King.class, "\u265A");
        }
    };

    public ChessGUI() {
        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));
        initialiseBoard();
        pack();
        setVisible(true);
    }

    private void initialiseBoard() {
        // Initialise each square of the board with a component with a mouse listener
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
                    String symbol = pieceMap.get(piece.getClass());
                    Color colour = (piece.getColour() == PieceColour.WHITE) ? PieceColour.WHITE : PieceColour.BLACK;
                    squares[row][col].setPieceSymbol(symbol, colour);
                } else {
                    squares[row][col].clearPieceSymbol();
                }
            }
        }
    }

    private void handleSquareClick(int row, int col) {
        // This method bridges user interacions with game logic
        // by determining whether a move has been made and then updating
        // the board
        if (game.handleSquareSelection(row, col)) {
            refreshBoard();
            checkGameState();
        }
    }

    private void checkGameState() {
        // This method shows the user dialog based on game conditions
        PieceColour currentPlayer = game.getCurrentPlayerColour();
        boolean inCheck = game.isInCheck(currentPlayer);

        if (inCheck) {
            JOptionPane.showMessageDialog(this, currentPlayer + "is in check.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGUI::new);
    }
}
