package client;

import client.pieces.*;
import client.pieces.abstracts.*;
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

    private HashMap<Integer, Piece[]> boardMap;

    final Logger logger = LogManager.getLogger(Chessboard.class);
    final MoveSetUpdater moveSetUpdater = new MoveSetUpdater();

    public Chessboard() {

        // Initialize the piece data structure
        boardMap = new HashMap<>();
        final int size = 8;

        // For each file
        for (int i = 0; i < size; i++) {

            // BlackAlliance specials
            if (i == 0) {
                Piece[] pieceArr = {
                        new BlackRook(i, 0),
                        new BlackKnight(i, 1),
                        new BlackBishop(i, 2),
                        new BlackQueen(i, 3),
                        new BlackKing(i, 4),
                        new BlackBishop(i, 5),
                        new BlackKnight(i, 6),
                        new BlackRook(i, 7)
                };
                boardMap.put(i, pieceArr);
            }

            // BlackAlliance pawns
            else if (i == 1) {
                Piece[] pieceArr = {
                        new BlackPawn(i, 0),
                        new BlackPawn(i, 1),
                        new BlackPawn(i, 2),
                        new BlackPawn(i, 3),
                        new BlackPawn(i, 4),
                        new BlackPawn(i, 5),
                        new BlackPawn(i, 6),
                        new BlackPawn(i, 7)
                };
                boardMap.put(i, pieceArr);
            }

            // WhiteAlliance pawns
            else if (i == 6) {
                Piece[] pieceArr = {
                        new WhitePawn(i, 0),
                        new WhitePawn(i, 1),
                        new WhitePawn(i, 2),
                        new WhitePawn(i, 3),
                        new WhitePawn(i, 4),
                        new WhitePawn(i, 5),
                        new WhitePawn(i, 6),
                        new WhitePawn(i, 7)
                };
                boardMap.put(i, pieceArr);
            }

            // WhiteAlliance specials
            else if (i == 7) {
                Piece[] pieceArr = {
                        new WhiteRook(i, 0),
                        new WhiteKnight(i, 1),
                        new WhiteBishop(i, 2),
                        new WhiteQueen(i, 3),
                        new WhiteKing(i, 4),
                        new WhiteBishop(i, 5),
                        new WhiteKnight(i, 6),
                        new WhiteRook(i, 7)
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

                try {
                    // Search for the piece and add the asset if possible
                    Piece piece = boardMap.get(rank)[file];
                    Image img = new Image(piece.getAssetPath(), 80, 80, false, false);
                    ImageView imgView = new ImageView(img);
                    square.getChildren().add(imgView);
                    addDraggableListener(imgView);
                } catch (NullPointerException ex) { logger.debug(ex); }


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

                        // Get destination coordinates
                        int destRow = GridPane.getRowIndex(square);
                        int destCol = GridPane.getColumnIndex(square);

                        // Evaluate move
                        Piece selectedPiece = boardMap.get(srcRow)[srcCol];
                        if (isValidMove(selectedPiece, srcRow, srcCol, destRow, destCol))
                        {
                            logger.debug("Valid move!!");
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
                        else
                        {
                            logger.debug("Invalid move!!");
                            event.setDropCompleted(false);
                            event.consume();
                        }


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
     * Evaluates whether there is a path to the target position
     * @param srcRow
     * @param srcCol
     * @param destRow
     * @param destCol
     * @return
     */
    public Boolean isValidMove(Piece piece, int srcRow, int srcCol, int destRow, int destCol) {
        boolean result = false;

        switch(piece.getType())
        {

            /**
             * Diagonal
             * If abs(srcRow - destRow) == abs(srcCol - destRol)
             *  THEYRE DIAG BRO
             */

            case("bishop"):
                if (isDiagonal(srcRow, srcCol, destRow, destCol))
                {
                    //TODO: Pathing logic
                    result = true;
                }
                break;


            case("rook"):
                if (isStraight(srcRow, srcCol, destRow, destCol))
                {
                    //TODO: Pathing logic
                    result = true;
                }
                break;


            case("queen"):
                if (isDiagonal(srcRow, srcCol, destRow, destCol) || isStraight(srcRow, srcCol, destRow, destCol))
                {
                    result = true;
                }
                break;


            /**
             * Knight
             * Direction: NNW, NNE, ENE, ESE, SSE, SSW, WSE, SNW
             * Distance: 2, 1 OR 1, 2
             */
            case("knight"):
                if (
                        (destRow == srcRow - 2 && destCol == srcCol - 1) ||     // NNW
                        (destRow == srcRow - 1 && destCol == srcCol - 2) ||     // WNW
                        (destRow == srcRow + 1 && destCol == srcCol - 2) ||     // WSW
                        (destRow == srcRow + 2 && destCol == srcCol - 1) ||     // SWS
                        (destRow == srcRow + 2 && destCol == srcCol + 1) ||     // SSE
                        (destRow == srcRow + 1 && destCol == srcCol + 2) ||     // ESE
                        (destRow == srcRow - 1 && destCol == srcCol + 2) ||     // ENE
                        (destRow == srcRow - 2 && destCol == srcCol + 1)        // NNE
                )
                {
                    result = true;
                }
                break;


            /**
             * King
             * Direction: Any
             * Distance: 1
             * TODO: Special consideration for castling
             */
            case("king"):
                if (
                        (destRow == srcRow - 1 && destCol == srcCol) ||         // N
                        (destRow == srcRow - 1 && destCol == srcCol - 1) ||     // NW
                        (destCol == srcCol - 1 && destRow == srcRow) ||         // W
                        (destCol == srcCol - 1 && destRow == srcRow + 1) ||     // SW
                        (destRow == srcRow + 1 && destCol == srcCol) ||         // S
                        (destRow == srcRow + 1 && destCol == srcCol + 1) ||     // SE
                        (destCol == srcCol + 1 && destRow == srcRow) ||         // E
                        (destCol == srcCol + 1 && destRow == srcRow - 1)        // NE
                )
                {
                    result = true;
                }
                break;

            /**
             * Pawn
             * Direction: N (NE/NW when capturing)
             * Distance: 1 (2 on first move)
             */
            case("pawn"):
                result = true;
                break;
        }

        return result;
    }


    /**
     * Determine whether an enemy unit occupies a square
     * @param destRow
     * @param destCol
     * @return
     */
    public Boolean isEnemyInSpace(int srcRow, int srcCol, int destRow, int destCol) {

        String pieceColour = boardMap.get(srcRow)[srcCol].getColour();

        if (boardMap.get(destRow)[destCol] instanceof Piece )
        {
            if (boardMap.get(destRow)[destCol].getColour() != pieceColour)
            {
                return true;
            }
            else {
                return false;
            }
        }
        else
        {
            return false;
        }
    }


    /**
     * Determines if one square is at a diagonal of the other
     * @param srcRow
     * @param srcCol
     * @param destRow
     * @param destCol
     * @return
     */
    public Boolean isDiagonal(int srcRow, int srcCol, int destRow, int destCol) {

        if (Math.abs(srcRow - destRow) == Math.abs(srcCol - destCol))
        {
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * Determines if one square is in a straight line from the other
     * @param srcRow
     * @param srcCol
     * @param destRow
     * @param destCol
     * @return
     */
    public Boolean isStraight(int srcRow, int srcCol, int destRow, int destCol) {
        if (
                (srcRow == destRow && srcCol != destCol) ||
                (srcRow != destRow && srcCol == destCol)
        )
        {
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * Determines is there is a valid path from one square to another
     * @param srcRow
     * @param srcCol
     * @param destRow
     * @param destCol
     * @return
     */
    public Boolean isAPath(int srcRow, int srcCol, int destRow, int destCol) {
        return true;
    }

}
