package core.Pieces;

import core.Attributes.Color;
import core.Attributes.Form;
import core.Attributes.Fullness;
import core.Attributes.Height;

public class Piece {
    private final Height height;
    private final Color color;
    private final Fullness fullness;
    private final Form form;

    public Piece(Height height, Fullness fullness, Form form, Color color) {
        this.height = height;
        this.color = color;
        this.fullness = fullness;
        this.form = form;
    }

    public Height getHeight () {
        return height;
    }

    public Fullness getFullness () {
        return fullness;
    }

    public Form getForm () {
        return form;
    }

    public Color getColor () {
        return color;
    }

    public String toString () {
        return String.format("%s %s %s %s", height, fullness, form, color);
    }

    public boolean matchesAttribute (Piece otherPiece) {
        return this.getForm() == otherPiece.getForm() &&
               this.getColor() == otherPiece.getColor() &&
               this.getFullness() == otherPiece.getFullness() &&
               this.getHeight() == otherPiece.getHeight();
    }
}
