package client.pieces;

/**
 * Abstract class representing a chess piece
 */
public abstract class Piece {

    public String colour;
    public String assetPath;

    public Piece(){}

    public Piece(String colour) {
        this.colour = colour;
    };

    public String getAssetPath() {
        return assetPath;
    }
}
