package client.pieces;

import client.pieces.behaviours.PieceBehaviour;
import client.pieces.behaviours.PawnBehaviour;

/**
 * Abstract white pawn
 */
public class WhitePawn extends Piece {

    private boolean hasDoubleMove = true;
    private PieceBehaviour behaviour = new PawnBehaviour();
    private String assetPath = "pieces/pawn_w.png";

    public WhitePawn() {
        super("white");
    }

    public String getColour() {
        return this.colour;
    }

    public String getAssetPath() { return assetPath; }

    public PieceBehaviour getBehaviour() {
        return behaviour;
    }

    public boolean getDoubleMove() {
        return hasDoubleMove;
    }
}