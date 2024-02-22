package me.joshuasheldon.sliding.objs;

/**
 * A simple enum that represents the four cardinal directions
 * that a tile can move in.
 */
public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    /**
     * @return The direction opposite of the given
     * direction.
     */
    public static Direction opposite(Direction dir) {
        return switch (dir) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }

}
