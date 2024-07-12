package main.java.pieces;

public abstract class Piece {
    protected PiecePosition position;
    protected PieceColour colour;

    public Piece(PieceColour colour, PiecePosition position) {
        this.colour = colour;
        this.position = position;
    }

    public PieceColour getColour() {
        return this.colour;
    }

    public PiecePosition getPosition() {
        return this.position;
    }

    public void setPosition(PiecePosition position) {
        this.position = position;
    }

    // Each piece type will have a different implementation of this method
    public abstract boolean isValidMove(PiecePosition newPosition, Piece[][] board);
}
