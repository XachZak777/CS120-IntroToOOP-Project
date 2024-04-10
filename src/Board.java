public class Board {
    private final GamePiece[][] board = new GamePiece[4][4];

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
