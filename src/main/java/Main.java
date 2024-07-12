package main.java;

// Standard imports
import javax.swing.SwingUtilities;

// Custom imports
import main.java.game.ChessGUI;

public class Main {
        public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGUI::new);
    }
}
