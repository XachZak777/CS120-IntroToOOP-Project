package core;

import core.Attributes.*;
import core.Pieces.Piece;

public class Board {
    private final Piece[][] board;
    public final int boardSize = 4;

    public Board() {
        this.board = new Piece[boardSize][boardSize];
    }

    public boolean placePiece(int row, int col, Piece piece) {
        if (isPositionValid(row, col) && isPositionEmpty(row, col)) {
            board[row][col] = piece;
            return true;
        }
        return false;
    }

    public boolean isPositionEmpty (int row, int col) {
        return isPositionValid(row, col) && board[row][col] == null;
    }
 
    private boolean isPositionValid (int row, int col) {
        return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
    }

    public boolean checkWin () {
        return checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin();
    }

    private boolean checkRowsForWin () {
        for (int row = 0; row < 4; row++) {
            if (checkLineForWin(board[row][0], board[row][1], board[row][2], board[row][3])) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumnsForWin () {
        for (int col = 0; col < 4; col++) {
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
            if (allMatchAttribute(pieces, height)) return true;
        }
        for (Fullness fullness : Fullness.values()) {
            if (allMatchAttribute(pieces, fullness)) return true;
        }
        for (Form form : Form.values()) {
            if (allMatchAttribute(pieces, form)) return true;
        }
        for (Color color : Color.values()) {
            if (allMatchAttribute(pieces, color)) return true;
        }
        return false;
    }

    private <TAttribute extends Attribute> boolean allMatchAttribute (Piece[] pieces, TAttribute attribute) {
        for (Piece piece : pieces) {
            if (piece == null || !isEqual(piece, attribute)) {
                return false;
            }
        }
        return true;
    }

    private <TAttribute extends Attribute> boolean isEqual (Piece piece, TAttribute attribute) {
        return switch (attribute) {
            case Height height -> piece.getHeight() == height;
            case Fullness fullness -> piece.getFullness() == fullness;
            case Form form -> piece.getForm() == form;
            case Color color -> piece.getColor() == color;
            case null, default -> false;
        };
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                sb.append(board[i][j] == null ? ". " : "P ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
