package me.joshuasheldon.sliding.objs;

/**
 * An object representing the sliding of a tile
 * that is currently on a space in a certain direction.
 *
 * @param space     The space the tile is currently on
 * @param direction The direction the tile is being slid
 */
public record Move(Space space, Direction direction) {

    public Move {

        // Make sure objects are valid
        if (space == null || direction == null) {
            throw new IllegalArgumentException("Null space or direction passed into Move constructor!");
        }

        // Make sure space is valid
        if (space.col() < 0 || space.row() < 0) {
            throw new IllegalArgumentException("Invalid space passed into Move constructor!");
        }

    }

    @Override
    public String toString() {
        return "{" + space + " -> " + direction + "}";
    }

}
