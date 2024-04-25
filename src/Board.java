public class Board {
    private final GamePiece[][] board = new GamePiece[4][4];

    public void placePiece(int row, int col, GamePiece piece) {
        if (row < 0 || row >= 4 || col < 0 || col >= 4) {
            throw new IllegalArgumentException("Invalid row or column: (" + row + ", " + col + ")");
        }
        board[row][col] = piece;
    }

    public boolean checkWin() {
        // Check rows, columns, and diagonals for common attributes
        for (int i = 0; i < 4; i++) {
            if (checkRowForWin(i) || checkColForWin(i)) {
                return true;
            }
        }
        // Check main diagonal
        if (checkMainDiagonalForWin()) {
            return true;
        }
        // Check secondary diagonal
        if (checkSecondaryDiagonalForWin()) {
            return true;
        }
        return false;
    }

    private boolean checkRowForWin(int row) {
        try {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] == null) {
                    return false;
                }
            }
            GamePiece basePiece = board[row][0];
            for (int col = 1; col < 4; col++) {
                if (!basePiece.sharesAttribute(board[row][col])) {
                    return false;
                }
            }
            return true;
        } catch (NullPointerException e) {
            return false; // Handle potential NullPointerException
        }
    }

    private boolean checkColForWin(int col) {
        try {
            for (int row = 0; row < 4; row++) {
                if (board[row][col] == null) {
                    return false;
                }
            }
            GamePiece basePiece = board[0][col];
            for (int row = 1; row < 4; row++) {
                if (!basePiece.sharesAttribute(board[row][col])) {
                    return false;
                }
            }
            return true;
        } catch (NullPointerException e) {
            return false; // Handle potential NullPointerException
        }
    }

    private boolean checkMainDiagonalForWin() {
        try {
            for (int i = 1; i < 4; i++) {
                if (board[i][i] == null || !board[i][i].sharesAttribute(board[0][0])) {
                    return false;
                }
            }
            return true;
        } catch (NullPointerException e) {
            return false; // Handle potential NullPointerException
        }
    }

    private boolean checkSecondaryDiagonalForWin() {
        try {
            for (int i = 1; i < 4; i++) {
                if (board[i][3 - i] == null || !board[i][3 - i].sharesAttribute(board[0][3])) {
                    return false;
                }
            }
            return true;
        } catch (NullPointerException e) {
            return false; // Handle potential NullPointerException
        }
    }

    public boolean isBoardFull() {
        for (GamePiece[] row : board) {
            for (GamePiece piece : row) {
                if (piece == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isPositionEmpty(int row, int col) {
        return board[row][col] == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (GamePiece[] pieces : board) {
            for (GamePiece piece : pieces) {
                sb.append(piece == null ? ". " : "P ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
