package client;

import client.pieces.abstracts.Piece;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public enum GameController {

    INSTANCE;

    private static final Logger logger = LogManager.getLogger(GameController.class);

    private Chessboard chessboard;

    GameController() {}



    /**
     * Starts the game
     */
    public void startGame() {
        this.chessboard = new Chessboard();
    }

    public Chessboard getChessBoard() {
        return this.chessboard;
    }

    public HashMap<Integer, Piece[]> getChessboardMap() {
        return this.chessboard.getBoardMap();
    }
}
