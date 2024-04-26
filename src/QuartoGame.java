import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuartoGame {
    private List<GamePiece> pieces;
    private Board board;
    private Scanner scanner;
    private int lastRow;
    private int lastCol;
    private boolean tutorialMode;

    public QuartoGame(boolean tutorialMode) {
        initializePieces();
        this.board = new Board();
        this.scanner = new Scanner(System.in);
        this.tutorialMode = tutorialMode;
    }

    private void initializePieces() {
        pieces = new ArrayList<>();
        // Initialize all combinations of pieces
        for (Attribute tall : new Attribute[]{Attribute.TALL, Attribute.SHORT}) {
            for (Attribute solid : new Attribute[]{Attribute.SOLID, Attribute.HOLLOW}) {
                for (Attribute square : new Attribute[]{Attribute.SQUARE, Attribute.ROUND}) {
                    for (Attribute dark : new Attribute[]{Attribute.DARK, Attribute.LIGHT}) {
                        pieces.add(new Piece(tall, solid, square, dark));
                    }
                }
            }
        }
    }

    private GamePiece selectPieceForOpponent(int currentPlayer) {
        System.out.println("Available Pieces:");
        for (int i = 0; i < pieces.size(); i++) {
            System.out.println(i + 1 + ": " + pieces.get(i));
        }
        System.out.println("Player " + currentPlayer + ", select a piece for your opponent:");
        int pieceIndex = scanner.nextInt() - 1;
        while (pieceIndex < 0 || pieceIndex >= pieces.size()) {
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

    private void startTutorialGame() {
        System.out.println("Welcome to the Quarto tutorial!");
        System.out.println("In Quarto, players take turns selecting a piece and placing it on the board.");
        System.out.println("The objective is to create a row, column, or diagonal with four pieces that share at least one attribute.");
        System.out.println("Let's start the tutorial game...");

        int currentPlayer = 1;
        while (true) {
            System.out.println(board);
            GamePiece selectedPiece = selectPieceForOpponent(currentPlayer);
            int[] position = selectPosition();
            board.placePiece(position[0], position[1], selectedPiece);
            lastRow = position[0];
            lastCol = position[1];

            if (board.checkWin()) {
                System.out.println("Player " + currentPlayer + " wins!");
                System.out.println(board);
                break;
            }

            if (pieces.isEmpty()) {
                System.out.println("The game is a draw!");
                System.out.println(board);
                break;
            }

            currentPlayer = 3 - currentPlayer; // Switches between 1 and 2
        }

        System.out.println("Tutorial game ended.");
    }

    private void startRegularGame() {
        System.out.println("Starting a regular game of Quarto...");

        int currentPlayer = 1;
        while (true) {
            System.out.println(board);
            GamePiece selectedPiece = selectPieceForOpponent(currentPlayer);
            int[] position = selectPosition();
            board.placePiece(position[0], position[1], selectedPiece);
            lastRow = position[0];
            lastCol = position[1];

            if (board.checkWin()) {
                System.out.println("Player " + currentPlayer + " wins!");
                System.out.println(board);
                break;
            }

            if (pieces.isEmpty()) {
                System.out.println("The game is a draw!");
                System.out.println(board);
                break;
            }

            currentPlayer = 3 - currentPlayer; // Switches between 1 and 2
        }
    }

    private void chooseGameMode() {
        System.out.println("Choose game mode:");
        System.out.println("1. Regular Game");
        System.out.println("2. Tutorial Game");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                startRegularGame();
                break;
            case 2:
                startTutorialGame();
                break;
            default:
                System.out.println("Invalid choice. Please choose again.");
                chooseGameMode();
                break;
        }
    }

    public void startGame() {
        chooseGameMode();
    }

    public static void main(String[] args) {
        QuartoGame game = new QuartoGame(true); // Start in tutorial mode
        game.startGame();
    }
}
