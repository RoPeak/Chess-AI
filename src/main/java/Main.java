// Standard imports
import javax.swing.SwingUtilities;

// Custom imports
import game.ChessGUI;

public class Main {
    public static void main(String[] args) {
    SwingUtilities.invokeLater(ChessGUI::new);
    }
}