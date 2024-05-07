package core;

import core.attributes.*;
import core.exceptions.InvalidPiecePlacementException;
import core.pieces.Piece;

/**
 * The Game class represents the game logic for playing Quarto.
 */
public class Game {
    
    /**
     * The Turn enum represents the player's turn in the game.
     */
    public enum Turn {
        /**
         * Player 1's turn
         */
        PLAYER_1("Player 1"),
        /**
         * Player 2's turn
         */
        PLAYER_2("Player 2");

        private final String name;

        Turn(String s) {
            name = s;
        }

        public String toString() {
            return name;
        }
    }

    /**
     * The game board
     */
    private final Piece[][] board;
    /**
     * The size of the game board
     */
    public final int BOARD_SIZE = 4;
    /**
     * The current turn in the game
     */
    private Turn turn;

    /**
     * Constructs a new Game instance with an empty game board and sets the initial turn to Player 1.
     */
    public Game() {
        this.board = new Piece[BOARD_SIZE][BOARD_SIZE];
        this.turn = Turn.PLAYER_1;
    }

    /**
    * Places a piece on the game board at the specified position.
    * @param row The row index of the position
    * @param col The column index of the position
    * @param piece The piece to be placed
    * @return True if the piece was successfully placed, otherwise false
    * @throws InvalidPiecePlacementException
    */
    public boolean placePiece(int row, int col, Piece piece) throws InvalidPiecePlacementException {
        // Check if the position is within valid board limits
        if (!isPositionValid(row, col)) {
            throw new InvalidPiecePlacementException("Position out of bounds: (" + row + ", " + col + ")");
        }

        // Check if the position is already occupied
        if (!isPositionEmpty(row, col)) {
            throw new InvalidPiecePlacementException("Position already occupied: (" + row + ", " + col + ")");
        }

        // If valid, place the piece and switch turns
        board[row][col] = piece;
        toggleTurn();
        return true;
    }

    /**
     * Checks if a position on the game board is empty.
     * @param row The row index of the position
     * @param col The column index of the position
     * @return True if the position is empty, otherwise false
     */
    public boolean isPositionEmpty(int row, int col) {
        return isPositionValid(row, col) && board[row][col] == null;
    }

    /**
     * Checks if a player has won the game.
     * @return True if a player has won, otherwise false
     */
    public boolean checkWin() {
        return checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin(); // Player 1 or Player 2 wins
    }

    /**
     * Checks if the game has ended in a draw.
     * @return True if the game has ended in a draw, otherwise false
     */
    public boolean checkDraw() {
        // Check if the board is full (no empty positions)
        for (Piece[] row : board) {
            for (Piece piece : row) {
                if (piece == null) {
                    return false; // Board is not full, not a draw
                }
            }
        }
        return true; // Board is full, it's a draw
    }

    /**
     * Gets the current turn in the game.
     * @return The current turn
     */
    public Turn getTurn() {
        return turn;
    }

    /**
     * Toggles the turn between Player 1 and Player 2.
     */
    private void toggleTurn() {
        switch (turn) {
            case PLAYER_1 -> turn = Turn.PLAYER_2;
            case PLAYER_2 -> turn = Turn.PLAYER_1;
        }
    }

    /**
     * Checks if a position on the game board is valid.
     * @param row The row index of the position
     * @param col The column index of the position
     * @return True if the position is valid, otherwise false
     */
    private boolean isPositionValid(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
    }

    /**
     * Helper methods for checking win conditions.
     */
    private boolean checkRowsForWin() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            if (checkLineForWin(board[row])) return true;
        }
        return false;
    }

    private boolean checkColumnsForWin() {
        for (int col = 0; col < BOARD_SIZE; col++) {
            if (checkLineForWin(board[0][col], board[1][col], board[2][col], board[3][col])) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonalsForWin () {
        return checkLineForWin(board[0][0], board[1][1], board[2][2], board[3][3]) ||
               checkLineForWin(board[0][3], board[1][2], board[2][1], board[3][0]);
    }

    private boolean checkLineForWin(Piece... pieces) {
        if (pieces[0] == null) return false; // Can't have a line win if the first piece is null
        // Check each attribute type for matches
        for (Height height : Height.values()) {
            if (Piece.allPiecesMatch(pieces, height)) return true;
        }
        for (Fullness fullness : Fullness.values()) {
            if (Piece.allPiecesMatch(pieces, fullness)) return true;
        }
        for (Form form : Form.values()) {
            if (Piece.allPiecesMatch(pieces, form)) return true;
        }
        for (Color color : Color.values()) {
            if (Piece.allPiecesMatch(pieces, color)) return true;
        }
        return false;
    }



    /**
     * Generates a string representation of the game board.
     * @return A string representation of the game board
     */
    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                sb.append(board[i][j] == null ? ". " : "P ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Resets the game by clearing the game board and setting the turn to Player 1.
     */
    public void reset() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = null;
            }
        }
        // Reset turn to PLAYER_1
        turn = Turn.PLAYER_1;
    }
}
