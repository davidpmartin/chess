package client.pieces;

import client.pieces.behaviours.PieceBehaviour;
import client.pieces.behaviours.RookBehaviour;

/**
 * Abstract black rook
 */
public class BlackRook extends Piece {

    private PieceBehaviour behaviour = new RookBehaviour();
    private String assetPath = "pieces/rook_b.png";

    public BlackRook() {
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

}