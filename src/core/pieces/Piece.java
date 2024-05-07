package core.pieces;

import core.attributes.*;

/**
 * The Piece class represents a Quarto game piece with attributes such as height, color, fullness, and form.
 */
public class Piece {
    public static <TAttribute extends Attribute> boolean allPiecesMatch(Piece[] pieces, TAttribute attribute) {
        for (Piece piece : pieces) {
            if (piece == null || !piece.matchesAttribute(attribute)) {
                return false;
            }
        }
        return true;
    }

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
     * Checks if the piece has the specific attribute.
     * @param attribute The attribute to check
     * @return True if attribute matches, otherwise false
     */
    public  <TAttribute extends Attribute> boolean matchesAttribute(TAttribute attribute) {
        return switch (attribute) {
            case Height _height -> getHeight() == _height;
            case Fullness _fullness -> getFullness() == _fullness;
            case Form _form -> getForm() == _form;
            case Color _color -> getColor() == _color;
            case null, default -> false;
        };
    }
}
