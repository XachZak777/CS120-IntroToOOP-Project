package core;
public class IllegalArrangementException extends Exception {
    public IllegalArrangementException() {
        super("Invalid arrangement of pieces on the board.");
    }

    public IllegalArrangementException(String message) {
        super(message);
    }
}
