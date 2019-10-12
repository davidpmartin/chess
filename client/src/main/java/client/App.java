package client;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Main application class for our chess game
 */
public class App extends Application {

    private Scene scene;

    final Logger logger = LogManager.getLogger(App.class);

    @Override
    public void start(Stage primaryStage) throws Exception {


        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setGridLinesVisible(true);
        root.autosize();

        scene = new Scene(root, 1000, 1000);
        scene.setCursor(Cursor.HAND);

        // Init chessboard object
        Chessboard chessboard = new Chessboard();
        chessboard.drawBoard(root);

        // Present app
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Main loop
    public static void main(String[] args) {
        launch();
    }
}