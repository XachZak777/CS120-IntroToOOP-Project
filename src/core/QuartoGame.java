package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuartoGame {
    private List<AbstractPiece> pieces;
    private Board board;
    private Scanner scanner;
    private boolean tutorialMode;

    public QuartoGame(boolean tutorialMode) {
        this.tutorialMode = tutorialMode;
        this.pieces = initializePieces();
        this.board = new Board();
        this.scanner = new Scanner(System.in);
    }

    private List<AbstractPiece> initializePieces() {
        List<AbstractPiece> pieces = new ArrayList<>();
        for (Height height : Height.values()) {
            for (Fullness fullness : Fullness.values()) {
                for (Color color : Color.values()) {
                    for (Form form : Form.values()) {
                        pieces.add(new AbstractPiece(height, fullness, form, color) {});
                    }
                }
            }
        }
        return pieces;
    }

    private AbstractPiece selectPieceForOpponent(int currentPlayer) {
        System.out.println("Available Pieces:");
        for (int i = 0; i < pieces.size(); i++) {
            System.out.println((i + 1) + ": " + pieces.get(i));
        }
        System.out.println("Player " + currentPlayer + ", select a piece for your opponent:");
        int pieceIndex = scanner.nextInt() - 1;
        while (pieceIndex < 0 || pieceIndex >= pieces.size() || pieces.get(pieceIndex) == null) {
            System.out.println("Invalid selection. Please select a valid piece:");
            pieceIndex = scanner.nextInt() - 1;
        }
        return pieces.remove(pieceIndex);
    }

    private int[] selectPosition() {
        System.out.println("Select position (row[1-4] column[1-4]):");
        int row = scanner.nextInt() - 1;
        int col = scanner.nextInt() - 1;
        while (row < 0 || row >= 4 || col < 0 || col >= 4 || !board.isPositionEmpty(row, col)) {
            System.out.println("Invalid position. Please select a valid position:");
            row = scanner.nextInt() - 1;
            col = scanner.nextInt() - 1;
        }
        return new int[]{row, col};
    }
}