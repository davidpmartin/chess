package client.pieces;

import client.PieceVisitor;
import client.pieces.abstracts.Knight;
import client.pieces.components.AssetPath;
import client.pieces.components.MoveSet;
import client.pieces.components.Position;

public class BlackKnight extends Knight {

    private AssetPath assetPath = new AssetPath("pieces/knight_b.png");
    private String colour;
    private String type;
    private Position position;
    private MoveSet moveSet;

    public BlackKnight(int rank, int file) {
        this.position = new Position(rank, file);
        this.colour = "black";
        this.type = "knight";
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


