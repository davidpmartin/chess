package client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum GameController {

    INSTANCE;

    private static final Logger logger = LogManager.getLogger(GameController.class);

    /**
     * Starts the game
     */
    public void startGame() {
        Chessboard chessboard = new Chessboard();
    }
}
