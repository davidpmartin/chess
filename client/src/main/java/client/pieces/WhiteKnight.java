package client.pieces;

import client.pieces.behaviours.PieceBehaviour;
import client.pieces.behaviours.KnightBehaviour;

/**
 * Abstract white knight
 */
public class WhiteKnight extends Piece {

    private PieceBehaviour behaviour = new KnightBehaviour();
    private String assetPath = "pieces/knight_w.png";


    public WhiteKnight() {
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
