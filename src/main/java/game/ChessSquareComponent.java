package game;

// Standard imports
import javax.swing.*;
import java.awt.*;

public class ChessSquareComponent extends JButton {
    private int row;
    private int col;

    public ChessSquareComponent(int row, int col) {
        this.row = row;
        this.col = col;
        initButton();
    }

    private void initButton() {
        // Set button size
        setPreferredSize(new Dimension(64, 64));

        // Colour the odd and even squares differently to
        // create checker pattern
        if ((row + col) % 2 == 0) {
            setBackground(Color.LIGHT_GRAY);
        } else {
            setBackground(Color.BLACK);
        }

        // Set alignment
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);

        // Set font
        setFont(new Font("Serif", Font.BOLD, 36));
    }

    public void setPieceSymbol(String symbol, Color colour) {
        this.setText(symbol);
        this.setForeground(colour);
    }

    public void clearPieceSymbol() {
        this.setText("");
    }
}
