package core.Pieces;

import core.Attributes.*;

import java.util.ArrayList;
import java.util.List;

public class Pieces {
    public static List<AbstractPiece> getAllPieces() {
        return pieces;
    }

    private static final List<AbstractPiece> pieces = initializePieces();

    private static List<AbstractPiece> initializePieces() {
        List<AbstractPiece> pieces = new ArrayList<>();
        for (Height height : Height.values()) {
            for (Fullness fullness : Fullness.values()) {
                for (Color color : Color.values()) {
                    for (Form form : Form.values()) {
                        pieces.add(new AbstractPiece(height, fullness, form, color) {});
                    }
                }
            }
        }
        return pieces;
    }
}
