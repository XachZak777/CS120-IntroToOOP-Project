package core.pieces;

import core.attributes.Color;
import core.attributes.Form;
import core.attributes.Fullness;
import core.attributes.Height;

/**
 * The Piece class represents a Quarto game piece with attributes such as height, color, fullness, and form.
 */
public class Piece {
    private final Height height;
    private final Color color;
    private final Fullness fullness;
    private final Form form;

    /**
     * Constructs a new Piece with the specified attributes.
     * @param height The height attribute of the piece
     * @param fullness The fullness attribute of the piece
     * @param form The form attribute of the piece
     * @param color The color attribute of the piece
     */
    public Piece(Height height, Fullness fullness, Form form, Color color) {
        this.height = height;
        this.color = color;
        this.fullness = fullness;
        this.form = form;
    }

    /**
     * Gets the height attribute of the piece.
     * @return The height attribute
     */
    public Height getHeight () {
        return height;
    }

    /**
     * Gets the fullness attribute of the piece.
     * @return The fullness attribute
     */
    public Fullness getFullness () {
        return fullness;
    }

    /**
     * Gets the form attribute of the piece.
     * @return The form attribute
     */
    public Form getForm () {
        return form;
    }

    /**
     * Gets the color attribute of the piece.
     * @return The color attribute
     */
    public Color getColor () {
        return color;
    }

    /**
     * Returns a string representation of the piece.
     * @return A string representing the piece's attributes
     */
    public String toString () {
        return String.format("%s %s %s %s", height, fullness, form, color);
    }

    /**
     * Checks if the piece matches all attributes with another piece.
     * @param otherPiece The other piece to compare attributes with
     * @return True if all attributes match, otherwise false
     */
    public boolean matchesAttribute (Piece otherPiece) {
        return this.getForm() == otherPiece.getForm() &&
               this.getColor() == otherPiece.getColor() &&
               this.getFullness() == otherPiece.getFullness() &&
               this.getHeight() == otherPiece.getHeight();
    }
}
