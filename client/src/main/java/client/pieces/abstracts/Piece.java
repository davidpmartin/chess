package client.pieces.abstracts;

import client.Visitable;
import client.pieces.components.MoveSet;
import client.pieces.components.Position;

/**
 * Abstract class representing a chess piece
 */
public abstract class Piece implements Visitable {

    public abstract void setMoveSet(MoveSet moveSet);

    public abstract String getAssetPath();

    public abstract Position getPosition();

    public abstract MoveSet getMoveSet();

    public abstract String getColour();

    public abstract String getType();
}
