package core.Pieces;

import core.Attributes.*;

import java.util.ArrayList;
import java.util.List;

public class Pieces {
    public static List<Piece> getAllPieces() {
        return pieces;
    }

    private static final List<Piece> pieces = initializePieces();

    private static List<Piece> initializePieces() {
        List<Piece> pieces = new ArrayList<>();
        for (Height height : Height.values()) {
            for (Fullness fullness : Fullness.values()) {
                for (Color color : Color.values()) {
                    for (Form form : Form.values()) {
                        pieces.add(new Piece(height, fullness, form, color) {});
                    }
                }
            }
        }
        return pieces;
    }
}
