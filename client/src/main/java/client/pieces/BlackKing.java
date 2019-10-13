package client.pieces;

import client.pieces.behaviours.PieceBehaviour;
import client.pieces.behaviours.KingBehaviour;

/**
 * Abstract black king
 */
public class BlackKing extends Piece {

    private PieceBehaviour behaviour = new KingBehaviour();
    private String assetPath = "pieces/king_b.png";

    public BlackKing() {
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