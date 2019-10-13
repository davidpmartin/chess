package client.pieces;

import client.pieces.behaviours.PieceBehaviour;
import client.pieces.behaviours.PawnBehaviour;

/**
 * Abstract black pawn
 */
public class BlackPawn extends Piece {

    private boolean hasDoubleMove = true;
    private PieceBehaviour behaviour = new PawnBehaviour();
    private String assetPath = "pieces/pawn_b.png";

    public BlackPawn() {
        super("black");
    }

    public String getColour() {
        return this.colour;
    }

    public String getAssetPath() {
        return assetPath;
    }

    public PieceBehaviour getBehaviour() {
        return behaviour;
    }

    public boolean getDoubleMove() {
        return hasDoubleMove;
    }

}
