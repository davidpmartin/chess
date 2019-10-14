package client.pieces.components;

/**
 * Position component to track position on the board (all 0 based integers for rank/file)
 */
public class Position {

    private int rank;
    private int file;

    public Position(int rank, int file) {
        this.rank = rank;
        this.file = file;
    }

    public int getRank() {
        return rank;
    }

    public int getFile() {
        return file;
    }
}
