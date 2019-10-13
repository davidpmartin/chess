package client;

import client.pieces.*;
import client.pieces.behaviours.PawnBehaviour;
import client.pieces.behaviours.PieceBehaviour;
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

import java.util.HashMap;

/**
 * Chessboard class
 *
 * Validation logic:
 *
 * For any given move::
 * if (pathIsClear) // Not required for knight, which hops
 * {
 *      if (destinationClear)
 *      {
 *          MOVE
 *      }
 *      else
 *      {
 *          if (occupiedByEnemy)
 *          {
 *              CAPTURE AND MOVE
 *          }
 *          else
 *          {
 *              INVALID MOVE (allied piece on space)
 *          }
 *      }
 * }
 * else
 * {
 *     INVALID MOVE (CANNOT EACH)
 * }
 */
public class Chessboard {

    private HashMap<Integer,Piece[]> boardMap;

    final Logger logger = LogManager.getLogger(Chessboard.class);

    public Chessboard() {

        // Initialize the piece data structure
        boardMap = new HashMap<>();
        final int size = 8;

        // For each file
        for (int i = 0; i < size; i++) {

            // Black specials
            if (i == 0) {
                Piece[] pieceArr = {
                        new BlackRook(),
                        new BlackKnight(),
                        new BlackBishop(),
                        new BlackQueen(),
                        new BlackKing(),
                        new BlackBishop(),
                        new BlackKnight(),
                        new BlackRook()
                };
                boardMap.put(i, pieceArr);
            }

            // Black pawns
            else if (i == 1) {
                Piece[] pieceArr = {
                        new BlackPawn(),
                        new BlackPawn(),
                        new BlackPawn(),
                        new BlackPawn(),
                        new BlackPawn(),
                        new BlackPawn(),
                        new BlackPawn(),
                        new BlackPawn()
                };
                boardMap.put(i, pieceArr);
            }

            // White pawns
            else if (i == 6) {
                Piece[] pieceArr = {
                        new WhitePawn(),
                        new WhitePawn(),
                        new WhitePawn(),
                        new WhitePawn(),
                        new WhitePawn(),
                        new WhitePawn(),
                        new WhitePawn(),
                        new WhitePawn()
                };
                boardMap.put(i, pieceArr);
            }

            // White specials
            else if (i == 7) {
                Piece[] pieceArr = {
                        new WhiteRook(),
                        new WhiteKnight(),
                        new WhiteBishop(),
                        new WhiteQueen(),
                        new WhiteKing(),
                        new WhiteBishop(),
                        new WhiteKnight(),
                        new WhiteRook()
                };
                boardMap.put(i, pieceArr);
            }

            // For everything else
            else {
                Piece[] pieceArr = new Piece[8];
                boardMap.put(i, pieceArr);
            }
        }
    }

    /**
     * Draws the board on application start
     * @param appGrid
     */
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
                    piece = boardMap.get(rank)[file];
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
                                GridPane.getRowIndex(square))[GridPane.getColumnIndex(square)].getClass());
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

                        // Get selected pane
                        StackPane srcPane = ((StackPane) ((ImageView) event.getGestureSource()).getParent());

                        // Get source coordinates and the selected piece
                        int srcRow = GridPane.getRowIndex(srcPane);
                        int srcCol = GridPane.getColumnIndex(srcPane);
                        Piece selectedPiece = boardMap.get(srcRow)[srcCol];

                        // Get destination coordinates
                        int destRow = GridPane.getRowIndex(square);
                        int destCol = GridPane.getColumnIndex(square);

                        // Evaluate move
                        isMoveValid(selectedPiece, srcRow, srcCol, destRow, destCol);

                        // Change position in boardMap
                        boardMap.get(destRow)[destCol] = selectedPiece;
                        boardMap.get(GridPane.getRowIndex(srcPane))[GridPane.getColumnIndex(srcPane)] = null;


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

    /**
     * Used for external access to the boardMap
     * @return
     */
    public HashMap<Integer, Piece[]> getBoardMap() {
        return boardMap;
    }


    /**
     * Adds the draggable listener to the piece ImageView object
     * @param imgView
     */
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

    /**
     * Determines the validity of a move given the piece, its old position, and new position
     * @param piece
     * @param srcRow
     * @param srcCol
     * @param destRow
     * @param destCol
     * @return
     */
    public Boolean isMoveValid(Piece piece, int srcRow, int srcCol, int destRow, int destCol) {

        boolean pathClear = false;
        boolean result = false;
        //TODO: LOGIC

        return result;
    }


    /**
     * Determined whether a space is occupied
     * @param destRow
     * @param destCol
     * @return
     */
    public Boolean isSpaceOccupied(int destRow, int destCol) {
        boolean result = false;


        return result;
    }


    /**
     * Determine whether an enimy unit occupies a square
     * @param destRow
     * @param destCol
     * @return
     */
    public Boolean isEnemyInSpace(int destRow, int destCol) {
        boolean result = false;


        return result;
    }
}
