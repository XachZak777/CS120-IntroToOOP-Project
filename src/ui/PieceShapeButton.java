package ui;

import core.Attributes.Form;
import core.Attributes.Fullness;
import core.Pieces.AbstractPiece;

import javax.swing.*;
import java.awt.*;

public class PieceShapeButton extends JButton {
    private AbstractPiece _piece;

    public PieceShapeButton(AbstractPiece piece) {
        _piece = piece;
    }

    public PieceShapeButton() {
        _piece = null;
    }

    public void setPiece(AbstractPiece piece) {
        _piece = piece;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (_piece == null) return;

        Graphics2D g2d = (Graphics2D) g;

        switch (_piece.getColor()) {
            case DARK -> g2d.setColor(java.awt.Color.RED);
            case LIGHT -> g2d.setColor(java.awt.Color.ORANGE);
        }

        int[] sizes = getPieceSize();
        setFullnessAndForm(g2d, sizes);

        g2d.dispose();
    }

    protected int[] getPieceSize() {
        int amount = switch (_piece.getHeight()) {
            case TALL -> getWidth() / 5;
            case SHORT -> getWidth() / 2;
        };

        int height = getHeight() - amount, width = getWidth() - amount, x = amount / 2, y = amount / 2;
        return new int[]{x, y, width, height};
    }

    protected void setFullnessAndForm(Graphics2D g2d, int[] sizes) {
        g2d.setStroke(new BasicStroke(3));
        if (_piece.getForm() == Form.ROUND && _piece.getFullness() == Fullness.SOLID) {
            g2d.fillOval(sizes[0], sizes[1], sizes[2], sizes[3]);
        }
        if (_piece.getForm() == Form.SQUARE && _piece.getFullness() == Fullness.SOLID) {
            g2d.fillRect(sizes[0], sizes[1], sizes[2], sizes[3]);
        }
        if (_piece.getForm() == Form.SQUARE && _piece.getFullness() == Fullness.HOLLOW) {
            g2d.drawRect(sizes[0], sizes[1], sizes[2], sizes[3]);
        }
        if (_piece.getForm() == Form.ROUND && _piece.getFullness() == Fullness.HOLLOW) {
            g2d.drawOval(sizes[0], sizes[1], sizes[2], sizes[3]);
        }
    }
}
