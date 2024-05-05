package ui;

import core.Attributes.Form;
import core.Attributes.Fullness;
import core.Pieces.Piece;

import javax.swing.*;
import java.awt.*;

/**
 * The PieceShapeButton class represents a custom button in the Quarto game UI that visually displays a game piece.
 * It extends the JButton class.
 */
public class PieceShapeButton extends JButton {
    private Piece _piece;

    /**
     * Constructs a new PieceShapeButton with the specified game piece.
     * @param piece The game piece to be displayed
     */
    public PieceShapeButton(Piece piece) {
        _piece = piece;
    }

    /**
     * Constructs a new PieceShapeButton without a game piece.
     */
    public PieceShapeButton() {
        _piece = null;
    }

    /**
     * Sets the game piece associated with the button.
     * @param piece The game piece to be displayed
     */
    public void setPiece(Piece piece) {
        _piece = piece;
    }

    /**
     * Overrides the paint method to customize the appearance of the button based on the associated game piece.
     * @param g The graphics context
     */
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

    /**
     * Calculates the size of the piece based on its height attribute and the button's size.
     * @return An array containing the x-coordinate, y-coordinate, width, and height of the piece
     */
    protected int[] getPieceSize() {
        int amount = switch (_piece.getHeight()) {
            case TALL -> getWidth() / 5;
            case SHORT -> getWidth() / 2;
        };

        int height = getHeight() - amount, width = getWidth() - amount, x = amount / 2, y = amount / 2;
        return new int[]{x, y, width, height};
    }

    /**
     * Draws the piece based on its form and fullness attributes.
     * @param g2d The 2D graphics context
     * @param sizes An array containing the size of the piece
     */
    protected void setFullnessAndForm(Graphics2D g2d, int[] sizes) {
        g2d.setStroke(new BasicStroke(5));
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