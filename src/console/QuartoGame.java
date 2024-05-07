package console;

import core.Game;
import core.exceptions.InvalidPiecePlacementException;
import core.pieces.Piece;
import core.pieces.Pieces;

import java.util.List;
import java.util.Scanner;

/**
 * The QuartoGame class represents the main game logic for playing Quarto in the console.
 */
public class QuartoGame {
    /**
     * List of available pieces
     */
    private List<Piece> pieces;
    /**
     * The game board
     */
    private Game board;
    /**
     * Scanner for user input
     */
    private Scanner scanner;

    /**
     * Constructs a new QuartoGame instance.
     * Initializes the list of pieces, the game board, and the scanner for user input.
     */
    public QuartoGame () {
        this.pieces = Pieces.getAllPieces();
        this.board = new Game();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Allows player to select a piece to be played by the opponent.
     * @param currentPlayer The current player's turn (1 or 2)
     * @return The selected piece
     */
    private Piece selectPieceForOpponent (int currentPlayer) {
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

    /**
     * Allows player to select a position on the board to place a piece.
     * @return The selected position as an array [row, column]
     */
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

    /**
     * Starts the Quarto game.
     * Manages the game flow until a player wins or the game ends in a draw.
     */
    public void startGame() throws InvalidPiecePlacementException{
        System.out.println("Welcome to Quarto!");
        System.out.println("Let's start the game.");
    
        while (!board.checkWin() && !pieces.isEmpty()) {
            // Player 1's turn
            System.out.println(board); // Print the current game board
            System.out.println("Player 1's turn:");
            Piece pieceForOpponent = selectPieceForOpponent(1);
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
    
            // Player 2's turn
            System.out.println(board); // Print the current game board
            System.out.println("Player 2's turn:");
            pieceForOpponent = selectPieceForOpponent(2);
            position = selectPosition();
            board.placePiece(position[0], position[1], pieceForOpponent);
            if (board.checkWin()) {
                System.out.println("Player 2 wins!");
                break;
            }
        }
    
        // Check for draw after the loop ends
        if (!board.checkWin() && pieces.isEmpty()) {
            System.out.println("No more pieces to select. It's a draw!");
        }
    }    
}