package client.pieces;

import client.pieces.behaviours.PieceBehaviour;
import client.pieces.behaviours.QueenBehaviour;

/**
 * Abstract black queen
 */
public class BlackQueen extends Piece {

    private PieceBehaviour behaviour = new QueenBehaviour();
    private String assetPath = "pieces/queen_b.png";

    public BlackQueen() {
        super("black");
    }

    public String getColour() {
        return this.colour;
    }

    public String getAssetPath() {
        return assetPath;
    }

}

