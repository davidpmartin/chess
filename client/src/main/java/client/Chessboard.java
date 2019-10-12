package client;

import client.pieces.*;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Chessboard class
 */
public class Chessboard {

    private HashMap<Integer, ArrayList<Piece>> boardMap;

    final Logger logger = LogManager.getLogger(Chessboard.class);

    public Chessboard() {

        // Initialize the piece data structure
        boardMap = new HashMap<>();
        final int size = 8;

        // For each file
        for (int i = 0; i < size; i++) {

            // Black specials
            if (i == 0) {
                boardMap.put(i, new ArrayList<>(8));
                boardMap.get(i).add(new BlackRook());
                boardMap.get(i).add(new BlackKnight());
                boardMap.get(i).add(new BlackBishop());
                boardMap.get(i).add(new BlackQueen());
                boardMap.get(i).add(new BlackKing());
                boardMap.get(i).add(new BlackBishop());
                boardMap.get(i).add(new BlackKnight());
                boardMap.get(i).add(new BlackRook());

            }

            // Black pawns
            else if (i == 1) {
                boardMap.put(i, new ArrayList<>(8));
                for (int p = 0; p < size; p++) {
                    boardMap.get(i).add(new BlackPawn());
                }
            }

            // White pawns
            else if (i == 6) {
                boardMap.put(i, new ArrayList<>(8));
                for (int p = 0; p < size; p++) {
                    boardMap.get(i).add(new WhitePawn());
                }
            }

            // White specials
            else if (i == 7) {
                boardMap.put(i, new ArrayList<>(8));
                boardMap.get(i).add(new WhiteRook());
                boardMap.get(i).add(new WhiteKnight());
                boardMap.get(i).add(new WhiteBishop());
                boardMap.get(i).add(new WhiteQueen());
                boardMap.get(i).add(new WhiteKing());
                boardMap.get(i).add(new WhiteBishop());
                boardMap.get(i).add(new WhiteKnight());
                boardMap.get(i).add(new WhiteRook());
            } else {
                boardMap.put(i, new ArrayList<>(8));
            }
        }
    }

    public void drawBoard(GridPane appGrid) {

        // Arrange board GUI
        final int size = 8;
        for (int rank = 0; rank < size; rank++) {
            for (int file = 0; file < size; file++) {
                StackPane square = new StackPane();
                String colour;
                if ((rank + file) % 2 == 0) {
                    colour = "#eddbc5";
                }
                else {
                    colour = "#b3856a";
                }
                square.setStyle("-fx-background-color: "+colour+";");
                square.setMaxSize(1000/8, 1000/8);
                square.setMinSize(1000/8, 1000/8);

                // Add a piece if exists
                try {
                    Piece piece;
                    piece = boardMap.get(rank).get(file);
                    Image img = new Image(piece.getAssetPath(), 80, 80, false, false);
                    ImageView imgView = new ImageView(img);
                    square.getChildren().add(imgView);
                    addDraggableListener(imgView);
                } catch (Exception ex) { }


                /**
                 * FOR DEBUGGING PURPOSES
                 */
                square.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        logger.debug("Row: {}, Col: {}", GridPane.getRowIndex(square), GridPane.getColumnIndex(square));
                        logger.debug("Piece: {}", boardMap.get(
                                GridPane.getRowIndex(square)).get(GridPane.getColumnIndex(square)).getClass());
                    }
                });

                square.setOnDragOver(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        logger.debug("Drag over detected!");
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                });

                square.setOnDragEntered(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        // TODO: Visual effect
                    }
                });

                square.setOnDragExited(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        // TODO: Visual effect remove
                    }
                });

                square.setOnDragDropped(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        logger.debug("Drop detected");
                        // TODO: Piece move logic

                        // Get selected piece
                        StackPane srcPane = ((StackPane) ((ImageView) event.getGestureSource()).getParent());
                        Piece selectedPiece = boardMap.get(
                                GridPane.getRowIndex(srcPane)).get(
                                        GridPane.getColumnIndex(srcPane)
                        );

                        // Get current coordinates
                        int droppedRow = GridPane.getRowIndex(square);
                        int droppedCol = GridPane.getColumnIndex(square);

                        // Change position in boardMap
                        boardMap.get(droppedRow).add(droppedCol, selectedPiece);
                        boardMap.get(GridPane.getRowIndex(srcPane)).remove(selectedPiece);

                        // Eval move

                        Dragboard db = event.getDragboard();
                        if (db.hasImage()) {
                            ImageView imgView = new ImageView(db.getImage());
                            addDraggableListener(imgView);
                            square.getChildren().add(imgView);
                        }
                        event.setDropCompleted(true);
                        event.consume();
                    }
                });

                appGrid.add(square, file, rank);
            }
        }


        for (int i = 0; i < size; i++) {
            appGrid.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            appGrid.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }
    }

    public void drawPieces(GridPane appGrid) {


    }

    public HashMap<Integer, ArrayList<Piece>> getBoardMap() {
        return boardMap;
    }

    public void addDraggableListener(ImageView imgView) {


        imgView.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logger.debug("Drag detected");
                ((Node) event.getSource()).setCursor(Cursor.HAND);

                Dragboard db = imgView.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent cbContent = new ClipboardContent();
                cbContent.putImage(imgView.getImage());
                db.setContent(cbContent);
                event.consume();
            }
        });

        imgView.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                logger.debug("Drag completed");
                if (event.getTransferMode() == TransferMode.MOVE) {
                    ((Pane) imgView.getParent()).getChildren().clear();
                }
            }
        });
    }
}
