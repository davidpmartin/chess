package client.pieces;

import client.pieces.behaviours.BishopBehaviour;
import client.pieces.behaviours.PieceBehaviour;

/**
 * Abstract white bishop
 */
public class WhiteBishop extends Piece {

    private PieceBehaviour behaviour = new BishopBehaviour();
    private String assetPath = "pieces/bishop_w.png";

    public WhiteBishop() {
        super("white");
    }

    public String getColour() {
        return this.colour;
    }

    public String getAssetPath() {
        return assetPath;
    }

}
