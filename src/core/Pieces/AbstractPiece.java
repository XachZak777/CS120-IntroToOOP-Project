package core.Pieces;

import core.Attributes.Color;
import core.Attributes.Form;
import core.Attributes.Fullness;
import core.Attributes.Height;

public abstract class AbstractPiece implements Piece {

    private final Height height;
    private final Color color;
    private final Fullness fullness;
    private final Form form;

    public AbstractPiece (Height height, Fullness fullness, Form form, Color color) {
        this.height = height;
        this.color = color;
        this.fullness = fullness;
        this.form = form;
    }

    @Override
    public Height getHeight () {
        return height;
    }

    @Override
    public Fullness getFullness () {
        return fullness;
    }

    @Override
    public Form getForm () {
        return form;
    }

    @Override
    public Color getColor () {
        return color;
    }

    @Override
    public String toString () {
        return String.format("%s %s %s %s", height, fullness, form, color);
    }

    public boolean matchesAttribute (AbstractPiece otherPiece) {
        return this.getForm() == otherPiece.getForm() &&
               this.getColor() == otherPiece.getColor() &&
               this.getFullness() == otherPiece.getFullness() &&
               this.getHeight() == otherPiece.getHeight();
    }
}
