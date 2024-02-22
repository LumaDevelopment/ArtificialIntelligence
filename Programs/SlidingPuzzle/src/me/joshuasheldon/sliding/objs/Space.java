package me.joshuasheldon.sliding.objs;

/**
 * @param col Column index, also the index of the
 *            second array in the 2D array
 * @param row Row index, also the index of the first
 *            array in the 2D array
 */
public record Space(int col, int row) {

    /**
     * Ensures the equality of Space objects depends only
     * on the equality of their {@code col} and {@code row} fields.
     *
     * @param other the reference object with which to compare.
     * @return {@code true} if this object is the same as the
     * {@code other} argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {

        if (!(other instanceof Space otherSpace)) {
            return false;
        }

        return col == otherSpace.col() && row == otherSpace.row();

    }

    /**
     * Returns the space that would be navigated to if
     * the given direction is applied to this space.
     *
     * @param direction the direction to navigate
     * @return the space that would be navigated to
     */
    public Space navigate(Direction direction) {
        return switch (direction) {
            case UP -> new Space(col, row - 1);
            case DOWN -> new Space(col, row + 1);
            case LEFT -> new Space(col - 1, row);
            case RIGHT -> new Space(col + 1, row);
        };
    }

    /**
     * Returns a string representation of the Space object.
     *
     * @return [<code>row</code>][<code>col</code>]
     */
    @Override
    public String toString() {
        return "[" + row + "][" + col + "]";
    }

}
