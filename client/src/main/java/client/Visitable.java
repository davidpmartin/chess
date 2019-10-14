package client;

import client.pieces.components.MoveSet;

public interface Visitable {

    MoveSet acceptVisitor(PieceVisitor visitor);
}
