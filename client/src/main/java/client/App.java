package client;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Main application class for our chess game
 */
public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    // Main loop
    public static void main(String[] args) {

        // Start game
        GameController.INSTANCE.startGame();
    }
}