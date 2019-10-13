package client.pieces;

import client.pieces.behaviours.PieceBehaviour;

/**
 * Abstract class representing a chess piece
 */
public abstract class Piece {

    public String colour;
    public String assetPath;
    public PieceBehaviour behaviour;

    public Piece(){}

    public Piece(String colour) {
        this.colour = colour;
    };

    public String getColour() { return this.colour; }

    public String getAssetPath() {
        return assetPath;
    }

    public PieceBehaviour getBehaviour() {
        return behaviour;
    }
}
