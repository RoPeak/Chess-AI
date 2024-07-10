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

    private void initialiseBoard() {}

    private void refreshBoard() {}

    private void handleSquareClick(int row, int col) {}

    private void checkGameState() {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGUI::new);
    }
}
