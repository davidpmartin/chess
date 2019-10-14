package client.pieces;

import client.PieceVisitor;
import client.pieces.abstracts.Queen;
import client.pieces.components.AssetPath;
import client.pieces.components.MoveSet;
import client.pieces.components.Position;

public class WhiteQueen extends Queen {

    private AssetPath assetPath = new AssetPath("pieces/queen_w.png");
    private String colour;
    private String type;
    private Position position;
    private MoveSet moveSet;

    public WhiteQueen(int rank, int file) {
        this.position = new Position(rank, file);
        this.colour = "white";
        this.type = "queen";
    }

    @Override
    public void setMoveSet(MoveSet moveSet) {
        this.moveSet = moveSet;
    }

    @Override
    public String getAssetPath() {
        return assetPath.getAssetPath();
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public MoveSet getMoveSet() {
        return this.moveSet;
    }

    @Override
    public String getColour() {
        return this.colour;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public MoveSet acceptVisitor(PieceVisitor visitor) {
        return visitor.update(this);
    }
}
