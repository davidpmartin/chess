package client.pieces;

import client.pieces.behaviours.PieceBehaviour;
import client.pieces.behaviours.RookBehaviour;

/**
 * Abstract white rook
 */
public class WhiteRook extends Piece {

    private PieceBehaviour behaviour = new RookBehaviour();
    private String assetPath = "pieces/rook_w.png";

    public WhiteRook() {
        super("white");
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
