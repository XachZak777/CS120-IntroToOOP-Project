public class Piece implements GamePiece {
    private final Attribute[] attributes;

    public Piece(Attribute... attributes) {
        this.attributes = attributes;
    }

    @Override
    public boolean matches(GamePiece other) {
        for (Attribute attr : attributes) {
            for (Attribute otherAttr : other.getAttributes()) {
                if (attr == otherAttr) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Attribute[] getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (Attribute attr : attributes) {
            builder.append(attr).append(" ");
        }
        builder.append("]");
        return builder.toString();
    }
}

