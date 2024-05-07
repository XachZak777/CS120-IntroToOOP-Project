package core.pieces;

import java.util.ArrayList;
import java.util.List;

import core.attributes.*;

/**
 * The Pieces class provides a utility for generating all possible Quarto game pieces.
 */
public class Pieces {

    /**
     * Retrieves a list containing all possible Quarto game pieces.
     * @return A list of all possible game pieces
     */
    public static List<Piece> getAllPieces() {
        return pieces;
    }

    /**
     * List to store all possible game pieces
     */
    private static final List<Piece> pieces = initializePieces();

    /**
     * Initializes the list of game pieces
     * @return The list of generated game pieces
     */
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
