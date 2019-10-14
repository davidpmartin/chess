package client;

import client.pieces.*;
import client.pieces.components.MoveSet;

public class MoveSetUpdater implements PieceVisitor {


    // Move set updating
    @Override
    public MoveSet update(WhiteBishop bishop) {
        return bishop.getMoveSet();
    }

    @Override
    public MoveSet update(WhiteKing king) {
        return king.getMoveSet();
    }

    @Override
    public MoveSet update(WhiteKnight knight) {
        return knight.getMoveSet();
    }

    @Override
    public MoveSet update(WhitePawn pawn) {
        return pawn.getMoveSet();
    }

    @Override
    public MoveSet update(WhiteQueen queen) {
        return queen.getMoveSet();
    }

    @Override
    public MoveSet update(WhiteRook rook) {
        return rook.getMoveSet();
    }

    @Override
    public MoveSet update(BlackBishop bishop) {
        return bishop.getMoveSet();
    }

    @Override
    public MoveSet update(BlackKing king) {
        return king.getMoveSet();
    }

    @Override
    public MoveSet update(BlackKnight knight) {
        return knight.getMoveSet();
    }

    @Override
    public MoveSet update(BlackPawn pawn) {
        return pawn.getMoveSet();
    }

    @Override
    public MoveSet update(BlackQueen queen) {
        return queen.getMoveSet();
    }

    @Override
    public MoveSet update(BlackRook rook) {
        return rook.getMoveSet();
    }


    // Move set determination logic
    public void determineBishopMoveSet() {

    }
}
