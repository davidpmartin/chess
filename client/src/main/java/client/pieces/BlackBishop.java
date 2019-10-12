package client.pieces;

import client.pieces.behaviours.BishopBehaviour;
import client.pieces.behaviours.PieceBehaviour;

/**
 * Abstract white bishop
 */
public class BlackBishop extends Piece {

    private PieceBehaviour behaviour = new BishopBehaviour();
    private String assetPath = "pieces/bishop_b.png";

    public BlackBishop() {
        super("black");
    }

    public String getColour() {
        return this.colour;
    }

    public String getAssetPath() {
        return assetPath;
    }

}