package client.pieces;

import client.pieces.behaviours.PieceBehaviour;
import client.pieces.behaviours.QueenBehaviour;

/**
 * Abstract white queen
 */
public class WhiteQueen extends Piece {

    private PieceBehaviour behaviour = new QueenBehaviour();
    private String assetPath = "pieces/queen_w.png";


    public WhiteQueen() {
        super("white");
    }

    public String getColour() {
        return this.colour;
    }

    public String getAssetPath() {
        return assetPath;
    }

}
