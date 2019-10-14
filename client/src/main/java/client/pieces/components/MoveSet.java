package client.pieces.components;

import java.util.ArrayList;

/**
 * Move Set component which contains a set of possible moves for a given piece
 */
public class MoveSet {

    private ArrayList<Move> moves;

    public MoveSet() {}

    /**
     * Returns the moves
     * @return
     */
    public ArrayList<Move> getMoves() {
        return moves;
    }

    /**
     * Clears the moves
     */
    public void clearMoves() {
        this.moves.clear();
    }

    /**
     * Adds a new move
     * @param rank
     * @param file
     */
    public void addMove(int rank, int file) {
        this.moves.add(new Move(rank, file));
    }

}
