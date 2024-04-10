public class Board {
    private final GamePiece[][] board = new GamePiece[4][4];

    public void placePiece(int row, int col, GamePiece piece) {
        board[row][col] = piece;
    }

    public boolean checkWin() {
        // Example: Check rows for common attributes
        for (int i = 0; i < 4; i++) {
            if (checkRowForWin(i)) return true;
        }
        // Extend checks for columns, diagonals, and 2x2 squares
        return false;
    }

    private boolean checkRowForWin(int row) {
        GamePiece basePiece = board[row][0];
        if (basePiece == null) return false;

        for (int col = 1; col < 4; col++) {
            if (board[row][col] == null || !basePiece.matches(board[row][col])) {
                return false;
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
