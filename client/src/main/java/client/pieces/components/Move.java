package client.pieces.components;

/**
 * Move component representing a possible move on the board
 */
public class Move {

    private int rank;
    private int file;

    public Move(int rank, int file) {
        this.rank = rank;
        this.file = file;
    }

    /**
     * Returns the values of the move
     * @return
     */
    public int[] getValues() {
        int[] coords = {rank, file};
        return coords;
    }
}
