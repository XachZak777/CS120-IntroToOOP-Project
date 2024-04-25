public interface GamePiece {
    boolean matches(GamePiece other);
    Attribute[] getAttributes();
    
    default boolean sharesAttribute(GamePiece other) {
        Attribute[] myAttributes = getAttributes();
        Attribute[] otherAttributes = other.getAttributes();

        for (Attribute myAttr : myAttributes) {
            for (Attribute otherAttr : otherAttributes) {
                if (myAttr == otherAttr) {
                    return true;
                }
            }
        }
        return false;
    }
}
