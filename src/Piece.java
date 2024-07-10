public abstract class Piece {
    protected Position position;
    protected PieceColour colour;

    public piece(PieceColour colour, Position position) {
        this.colour = colour;
        this.position = position;
    }

    public PieceColour getColour() {
        return this.colour;
    }

    public Position getPosition() {
        return this.position;
    }

    // Each piece type will have a different implementation of this method
    public abstract boolean isValidMove(Position newPosition, Piece[][] board);
}
