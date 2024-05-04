package ui;

import core.Pieces.Piece;
import core.Board;
import core.Pieces.Pieces;

import javax.swing.*;
import java.awt.*;

public class QuartoGUI extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;
    public static final int SIDE_BUTTON_SIZE = 100;
    public static final int BOARD_BUTTON_SIZE = 200;


    private final Board board;
    private Piece selectedPiece;
    private PieceShapeButton selectedPieceShapeButton;

    public QuartoGUI() {
        setTitle("Quarto Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(WIDTH, HEIGHT);

        board = new Board();

        JPanel boardPanel = getBoardButtonsPanel();
        JPanel piecePanel = getPiecesPanel();

        add(boardPanel, BorderLayout.CENTER);
        add(piecePanel, BorderLayout.WEST);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel getPiecesPanel() {
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));

        JPanel piecesPanel = new JPanel();
        piecesPanel.setLayout(new GridLayout(8,2));

        wrapperPanel.add(piecesPanel);
        for (Piece piece : Pieces.getAllPieces()) {
            PieceShapeButton pieceButton = new PieceShapeButton(piece);
            pieceButton.setPreferredSize(new Dimension(SIDE_BUTTON_SIZE, SIDE_BUTTON_SIZE));
            pieceButton.addActionListener((e) -> {
                selectedPiece = piece;
                selectedPieceShapeButton = pieceButton;
            });
            piecesPanel.add(pieceButton);
        }

        return wrapperPanel;
    }

    private JPanel getBoardButtonsPanel() {
        JPanel boardPanel = new JPanel(new GridLayout(4, 4));

        PieceShapeButton[][] buttons = new PieceShapeButton[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j] = getButton(buttons, i, j);
                boardPanel.add(buttons[i][j]);
            }
        }

        return boardPanel;
    }

    private PieceShapeButton getButton(PieceShapeButton[][] buttons, int i, int j) {
        PieceShapeButton button = new PieceShapeButton();

        button.setPreferredSize(new Dimension(BOARD_BUTTON_SIZE, BOARD_BUTTON_SIZE));
        button.addActionListener((e) -> {
            if (selectedPiece == null) return;

            boolean placed = board.placePiece(i, j, selectedPiece);
            if (!placed) return;

            buttons[i][j].setPiece(selectedPiece);
            buttons[i][j].repaint();
            selectedPieceShapeButton.setEnabled(false);

            selectedPiece = null;
            selectedPieceShapeButton = null;
        });

        return button;
    }
}