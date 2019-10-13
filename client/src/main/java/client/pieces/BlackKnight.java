package client.pieces;

import client.pieces.behaviours.PieceBehaviour;
import client.pieces.behaviours.KnightBehaviour;

/**
 * Abstract black knight
 */
public class BlackKnight extends Piece {

    private PieceBehaviour behaviour = new KnightBehaviour();
    private String assetPath = "pieces/knight_b.png";

    public BlackKnight() {
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