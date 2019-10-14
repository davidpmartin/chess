package client.pieces.abstracts;

/**
 * Abstract pawn
 */
public abstract class Pawn extends Piece {

    public abstract void disableDoubleMove();

    public abstract boolean getDoubleMoveStatus();
}