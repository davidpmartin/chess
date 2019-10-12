import client.Chessboard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Chessboast tests
 */
public class ChessBoardTest {

    Chessboard board = new Chessboard();

    @Test
    public void existsTest() {
        Assertions.assertNotNull(this.board);
    }

    @Test
    public void dimensionsTest() {
        Assertions.assertEquals(8, this.board.getBoardMap().size());
        for (int i = 0; i < 8; i++) {
            Assertions.assertNotNull(this.board.getBoardMap().get(i));
        }
    }
}
