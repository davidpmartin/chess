package client;

import client.pieces.*;
import client.pieces.components.MoveSet;

public interface PieceVisitor {

    // For move validation
   MoveSet update(WhiteBishop bishop);
   MoveSet update(WhiteKing king);
   MoveSet update(WhiteKnight knight);
   MoveSet update(WhitePawn pawn);
   MoveSet update(WhiteQueen queen);
   MoveSet update(WhiteRook rook);

   MoveSet update(BlackBishop bishop);
   MoveSet update(BlackKing king);
   MoveSet update(BlackKnight knight);
   MoveSet update(BlackPawn pawn);
   MoveSet update(BlackQueen queen);
   MoveSet update(BlackRook rook);
}
