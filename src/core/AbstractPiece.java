package core;

public abstract class AbstractPiece implements Piece{

    private final Height height;
    private final Color color;
    private final Fullness fullness;
    private final Form form;

    protected AbstractPiece (Height height, Color color, Fullness fullness, Form form) {
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
}
