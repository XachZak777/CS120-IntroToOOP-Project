package core.Pieces;

import core.Attributes.Color;
import core.Attributes.Form;
import core.Attributes.Fullness;
import core.Attributes.Height;

public interface Piece {

    Form getForm ();

    Color getColor ();

    Fullness getFullness ();

    Height getHeight ();

    String toString ();

    boolean matchesAttribute (AbstractPiece otherPiece);
    
}