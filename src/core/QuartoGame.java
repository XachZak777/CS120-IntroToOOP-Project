package core;

import core.Attributes.Color;
import core.Attributes.Form;
import core.Attributes.Fullness;
import core.Attributes.Height;
import core.Pieces.AbstractPiece;
import core.Pieces.Piece;
import core.Pieces.Pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuartoGame {
    private List<AbstractPiece> pieces;
    private Board board;
    private Scanner scanner;

    public QuartoGame () {
        this.pieces = Pieces.getAllPieces();
        this.board = new Board();
        this.scanner = new Scanner(System.in);
    }

    private AbstractPiece selectPieceForOpponent (int currentPlayer) {
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

    public void startGame() {
        System.out.println("Welcome to Quarto!");
        System.out.println("Let's start the game.");

        while (!board.checkWin() && !pieces.isEmpty()) {
            System.out.println(board); // Print the current game board
            // Player 1's turn
            System.out.println("Player 1's turn:");
            AbstractPiece pieceForOpponent = selectPieceForOpponent(1);
            int[] position = selectPosition();
            board.placePiece(position[0], position[1], pieceForOpponent);
            if (board.checkWin()) {
                System.out.println("Player 1 wins!");
                break;
            }
            if (pieces.isEmpty()) {
                System.out.println("No more pieces to select. It's a draw!");
                break;
            }

            System.out.println(board); // Print the current game board
            // Player 2's turn
            System.out.println("Player 2's turn:");
            pieceForOpponent = selectPieceForOpponent(2);
            position = selectPosition();
            board.placePiece(position[0], position[1], pieceForOpponent);
            if (board.checkWin()) {
                System.out.println("Player 2 wins!");
                break;
            }
            if (pieces.isEmpty()) {
                System.out.println("No more pieces to select. It's a draw!");
                break;
            }
        }
    }

    public void showGameRules () {
        System.out.println("Game Rules:");
        System.out.println();
        System.out.println("Here are the basic rules:" + "\n" + "1. Players take turns placing pieces on the board."  + "\n" +
        "2. Once a piece is placed on the board, it cannot be moved or removed." +  "\n" + "3. The player who places a piece chooses which piece the opponent will play next." + "\n" +
        "4. The game continues until a player forms a line of four pieces with a common attribute (e.g., four tall pieces, four dark pieces, etc.)." + "\n" +
        "5. If the board is filled without any player achieving a winning line, the game ends in a draw.");
    }
}