package ui;

import core.Pieces.Piece;
import core.Game;
import core.Pieces.Pieces;

import javax.swing.*;

import java.awt.*;

public class QuartoGUI extends JFrame {

    private PieceShapeButton[][] boardButtons;

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 900;
    public static final int SIDE_BUTTON_SIZE = 100;
    public static final int BOARD_BUTTON_SIZE = 200;

    private static final String label = "%s, choose piece for %s";

    private final Game game;
    private Piece selectedPiece;
    private PieceShapeButton selectedPieceShapeButton;
    private final JLabel infoLabel = new JLabel();

    private final JPanel infoPanel;
    private final JPanel boardPanel;
    private final JPanel pieceWrapperPanel;
    private final JPanel controlPanel;

    public QuartoGUI() {

        boardButtons = new PieceShapeButton[4][4];
        setTitle("Quarto Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(WIDTH, HEIGHT);

        game = new Game();

        infoPanel = constructInfoPanel();
        boardPanel = constructBoardButtonsPanel();
        pieceWrapperPanel = constructPiecesPanel();
        controlPanel = constructControlPanel();

        add(boardPanel, BorderLayout.CENTER);
        add(pieceWrapperPanel, BorderLayout.WEST);
        add(infoPanel, BorderLayout.SOUTH);
        add(controlPanel, BorderLayout.NORTH);

        setLocationRelativeTo(null);
        setVisible(true);

        String firstInfo = String.format(label + " to start the game!", "Player 2", "Player 1");
        updateLabel(firstInfo);
    }

    private JPanel constructPiecesPanel() {
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));

        JPanel piecesPanel = new JPanel();
        piecesPanel.setLayout(new GridLayout(8, 2));

        wrapperPanel.add(piecesPanel);
        for (Piece piece : Pieces.getAllPieces()) {
            PieceShapeButton pieceButton = getPieceShapeButton(piece);
            piecesPanel.add(pieceButton);
        }

        return wrapperPanel;
    }

    private JPanel constructBoardButtonsPanel() {
        JPanel boardPanel = new JPanel(new GridLayout(game.BOARD_SIZE, game.BOARD_SIZE));
        boardButtons = new PieceShapeButton[game.BOARD_SIZE][game.BOARD_SIZE]; // Initialize boardButtons array
        for (int i = 0; i < game.BOARD_SIZE; i++) {
            for (int j = 0; j < game.BOARD_SIZE; j++) {
                boardButtons[i][j] = getButton(boardButtons, i, j); // Update argument to include the boardButtons array
                boardPanel.add(boardButtons[i][j]);
            }
        }
        return boardPanel;
    }

    private JPanel constructInfoPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.LIGHT_GRAY);

        bottomPanel.add(infoLabel);

        return bottomPanel;
    }

    private void updateLabel(String text) {
        infoLabel.setText(text);
        infoLabel.repaint();
    }

    private PieceShapeButton getButton(PieceShapeButton[][] buttons, int i, int j) {
        PieceShapeButton button = new PieceShapeButton();

        button.setPreferredSize(new Dimension(BOARD_BUTTON_SIZE, BOARD_BUTTON_SIZE));
        button.addActionListener((e) -> {
            if (selectedPiece == null)
                return;

            boolean placed = game.placePiece(i, j, selectedPiece);
            if (!placed)
                return;

            Game.Turn turn = game.getTurn();

            switch (turn) {
                case PLAYER_1 -> updateLabel(String.format(label, "Player 2", "Player 1"));
                case PLAYER_2 -> updateLabel(String.format(label, "Player 1", "Player 2"));
            }

            buttons[i][j].setPiece(selectedPiece);
            buttons[i][j].repaint();
            selectedPieceShapeButton.setEnabled(false);

            selectedPiece = null;
            selectedPieceShapeButton = null;

            if (game.checkWin()) {
                showWinDialog();
            } else if (game.checkDraw()) {
                showDrawDialog();
            }
        });

        return button;
    }

    private void showWinDialog() {
        Game.Turn turn = game.getTurn();
        String winner = switch (turn) {
            case PLAYER_1 -> "Player 1";
            case PLAYER_2 -> "Player 2";
        };
        JOptionPane.showMessageDialog(this, "Congratulations! " + winner + " wins!", "Winner",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showDrawDialog() {
        JOptionPane.showMessageDialog(this, "It's a draw!", "Draw", JOptionPane.INFORMATION_MESSAGE);
    }

    private PieceShapeButton getPieceShapeButton(Piece piece) {
        PieceShapeButton pieceButton = new PieceShapeButton(piece);
        pieceButton.setPreferredSize(new Dimension(SIDE_BUTTON_SIZE, SIDE_BUTTON_SIZE));
        pieceButton.addActionListener((e) -> {
            selectedPiece = piece;
            selectedPieceShapeButton = pieceButton;

            Game.Turn turn = game.getTurn();

            switch (turn) {
                case PLAYER_1 -> updateLabel("Player 1's turn. Piece to play: " + piece);
                case PLAYER_2 -> updateLabel("Player 2's turn. Piece to play: " + piece);
            }
        });
        return pieceButton;
    }

    private JPanel constructControlPanel() {
        JPanel controlPanel = new JPanel();
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener((e) -> restartGame());
        controlPanel.add(restartButton);
        return controlPanel;
    }

    private void resetGame() {
        game.reset();
    }

    private void restartGame() {
        resetGame();
        resetLeftSidePiecePanel();
        resetBoardButtons();
    }

    private void resetLeftSidePiecePanel() {
        JPanel piecePanel = (JPanel) pieceWrapperPanel.getComponents()[0];
        Component[] components = piecePanel.getComponents();
        for (Component component : components) {
            if (component instanceof PieceShapeButton button) {
                button.setEnabled(true);
            }
        }
    }

    private void resetBoardButtons() {
        Component[] components = boardPanel.getComponents();
        for (Component component : components) {
            if (component instanceof PieceShapeButton button) {
                button.setPiece(null);
                button.setEnabled(true);
                button.repaint();
            }
        }
    }
}
