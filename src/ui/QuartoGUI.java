package ui;

import core.AbstractPiece;
import core.Board;
import core.Form;
import core.Fullness;
import core.Height;
import core.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class QuartoGUI extends JFrame {

    private Board board;
    private List<AbstractPiece> pieces;
    private JButton[][] buttons;
    private JPanel boardPanel;
    private JPanel piecesPanel;

    public QuartoGUI() {
        setTitle("Quarto Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        board = new Board();
        pieces = initializePieces();

        boardPanel = new JPanel(new GridLayout(4, 4));
        piecesPanel = new JPanel(new FlowLayout());
        buttons = new JButton[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(60, 60));
                boardPanel.add(buttons[i][j]);
            }
        }
        for (AbstractPiece piece : pieces) {
            JButton pieceButton = new JButton("Piece");
            pieceButton.setPreferredSize(new Dimension(100, 30));
            pieceButton.addActionListener(new PieceButtonListener(piece));
            piecesPanel.add(pieceButton);
        }
        add(boardPanel, BorderLayout.CENTER);
        add(piecesPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private List<AbstractPiece> initializePieces() {
        List<AbstractPiece> pieces = new ArrayList<>();
        for (Height height : Height.values()) {
            for (Fullness fullness : Fullness.values()) {
                for (core.Color color : Color.values()) {
                    for (Form form : Form.values()) {
                        pieces.add(new AbstractPiece(height, fullness, form, color) {});
                    }
                }
            }
        }
        return pieces;
    }

    class PieceButtonListener implements ActionListener {
        private AbstractPiece piece;

        public PieceButtonListener(AbstractPiece piece) {
            this.piece = piece;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Selected piece: " + piece);
        }
    }
}
