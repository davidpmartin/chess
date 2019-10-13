package client.pieces;

import client.pieces.behaviours.PieceBehaviour;
import client.pieces.behaviours.KingBehaviour;

/**
 * Abstract white king
 */
public class WhiteKing extends Piece {

    private PieceBehaviour behaviour = new KingBehaviour();
    private String assetPath = "pieces/king_w.png";

    public WhiteKing() {
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
