package me.joshuasheldon.sliding.objs;

import me.joshuasheldon.sliding.Utilities;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A state of the sliding puzzle. A state is a 2D array
 * of integers, where each integer represents a tile
 * on the board. The state also keeps track of the
 * positions of the blank spaces on the board.
 */
public class State implements Comparable<State>, Iterable<Integer> {

    /**
     * The value of a blank space on the board.
     */
    public static final int BLANK_SPACE_VALUE = 0;

    /**
     * The 2D array representing the state of the board.
     */
    private final int[][] board;

    /**
     * All the blank spaces on the board.
     */
    private final LinkedList<Space> blankSpaces;

    /**
     * Create a new state object from a 2D array of integers.
     *
     * @param board The 2D array representing the state of the board
     * @throws IllegalArgumentException If the board is invalid
     *                                  (see Utilities.validateState for conditions that must be met)
     */
    public State(int[][] board) throws IllegalArgumentException {

        LinkedList<Space> blankSpaces = Utilities.validateState(board);

        if (blankSpaces == null) {
            throw new IllegalArgumentException("Invalid board passed into State constructor!");
        }

        this.board = board;
        this.blankSpaces = blankSpaces;

    }

    /**
     * Clone an existing state. Skips state validation,
     * since for a State object to exist, it must be valid.
     *
     * @param state The state to clone
     */
    public State(State state) {

        // Copy board
        int boardSize = state.getBoardSize();
        int[][] newBoard = new int[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++) {
            System.arraycopy(state.board[i], 0, newBoard[i], 0, boardSize);
        }

        this.board = newBoard;

        // Copy blank spaces
        this.blankSpaces = new LinkedList<>(state.blankSpaces);

    }

    /**
     * Applies a move to this state.
     *
     * @param move The move to be made.
     * @return The state produced as a result of the given
     * move, or <code>null</code> if the move is null or
     * otherwise invalid.
     */
    public State apply(Move move) {

        Space originalSpace = move.space();

        // Check validity of original space
        if (!isSpaceValid(originalSpace)) {
            return null;
        }

        Space newSpace = originalSpace.navigate(move.direction());

        // Check if new space is blank (inherently checks
        // for validity)
        if (getValue(newSpace) != BLANK_SPACE_VALUE) {
            return null;
        }

        // Create copy of board
        State newState = new State(this);

        // Move tile from original space to new space
        newState.getBoard()[newSpace.row()][newSpace.col()] = newState.getBoard()[originalSpace.row()][originalSpace.col()];
        newState.getBoard()[originalSpace.row()][originalSpace.col()] = BLANK_SPACE_VALUE;

        // Update blank spaces list
        newState.getBlankSpaces().remove(newSpace);
        newState.getBlankSpaces().push(originalSpace);

        return newState;

    }

    /**
     * Compare two states based on their distance from their goal state.
     *
     * @param o the object to be compared.
     * @return <code>0</code> if this state is equal to the given state.<br>
     * A value less than <code>0</code> if this state is closer to the goal state than the given state.<br>
     * A value greater than <code>0</code> if this state is farther from the goal state than the given state.
     */
    @Override
    public int compareTo(State o) {
        return Integer.compare(distanceFromGoal(), o.distanceFromGoal());
    }

    /**
     * The sum of the Manhattan distances from every
     * value in the state to its position on
     * the goal state.
     *
     * @return Collective distance of this state
     * from the goal state.
     */
    public int distanceFromGoal() {

        int totalDistance = 0;
        int boardSize = getBoardSize();
        int numOfBlankSpaces = this.blankSpaces.size();

        // Collect all the spaces that will
        // be blank in the goal state
        LinkedList<Space> allBlankSpaces = new LinkedList<>();

        for (int index = 0; index < numOfBlankSpaces; index++) {
            allBlankSpaces.push(Utilities.matrixIndexToSpace(index, boardSize));
        }

        // Calculate total distance
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {

                int val = this.board[row][col];

                if (val != 0) {

                    // Non-blank value
                    int goalMatrixIndex = numOfBlankSpaces + val - 1;
                    Space goalSpace = Utilities.matrixIndexToSpace(goalMatrixIndex, boardSize);
                    totalDistance += Utilities.manhattanDistance(goalSpace.col(), goalSpace.row(), col, row);

                } else {

                    // Blank value
                    // Since 0's can technically go anywhere, calculate
                    // the distance between this space and every
                    // blank space in the goal, then take the
                    // minimum of all calculated values.
                    int minDistance = Integer.MAX_VALUE;

                    for (Space blankSpace : allBlankSpaces) {
                        int distance = Utilities.manhattanDistance(blankSpace.col(), blankSpace.row(), col, row);
                        if (distance < minDistance) {
                            minDistance = distance;
                        }
                    }

                    totalDistance += minDistance;

                }

            }
        }

        return totalDistance;

    }

    /**
     * Checks equality between two states by ensuring
     * equivalent size and comparing all values.
     *
     * @param o The object to compare to.
     * @return Whether the two states are equal.
     */
    @Override
    public boolean equals(Object o) {

        if (!(o instanceof State other)) {
            return false;
        }

        if (this.getBoardSize() != other.getBoardSize()) {
            return false;
        }

        for (int row = 0; row < this.getBoardSize(); row++) {
            for (int col = 0; col < this.getBoardSize(); col++) {
                if (this.board[row][col] != other.board[row][col]) {
                    return false;
                }
            }
        }

        return true;

    }

    /**
     * @return The underlying 2D array representing the state of the board.
     */
    public int[][] getBoard() {
        return this.board;
    }

    /**
     * @return The blank spaces on the board.
     */
    public LinkedList<Space> getBlankSpaces() {
        return this.blankSpaces;
    }

    /**
     * @return The board size, meaning how long
     * the board is on each side.
     */
    public int getBoardSize() {
        return this.board.length;
    }

    /**
     * @param space The location of the space on the board.
     * @return The actual value of the space on the board,
     * or <code>-1</code> if the space is invalid.
     */
    public int getValue(Space space) {

        if (!isSpaceValid(space)) {
            return -1;
        }

        return this.board[space.row()][space.col()];

    }

    /**
     * Returns the goal state. In this implementation,
     * the goal state is that all blank spaces are
     * at the start of the board, and then all
     * non-blank spaces are in ascending order
     * from left to right, top to bottom.
     *
     * @return The goal state for this state.
     */
    public State goalState() {

        int blankSpacesRemaining = this.blankSpaces.size();
        int nextValToSet = 1;

        // Create 2D array for goal state
        int[][] goalBoard = new int[getBoardSize()][getBoardSize()];

        // Create goal state from values
        for (int row = 0; row < getBoardSize(); row++) {
            for (int col = 0; col < getBoardSize(); col++) {

                if (blankSpacesRemaining > 0) {
                    goalBoard[row][col] = BLANK_SPACE_VALUE;
                    blankSpacesRemaining--;
                } else {
                    goalBoard[row][col] = nextValToSet++;
                }

            }
        }

        // Attempt to create state object from
        // constructed board
        State goalState;

        try {
            goalState = new State(goalBoard);
        } catch (IllegalArgumentException ex) {
            System.err.println("Could not create goal state from following board:");
            System.err.println(this);
            return null;
        }

        return goalState;

    }

    /**
     * @param space The space to check.
     * @return Whether the given space is a
     * real location on the board (within bounds).
     */
    public boolean isSpaceValid(Space space) {

        return space != null &&
                space.col() >= 0 &&
                space.row() >= 0 &&
                space.col() < getBoardSize() &&
                space.row() < getBoardSize();

    }

    /**
     * Retrieves an iterator to iterate through
     * all values on the board, left to right,
     * top to bottom.
     *
     * @return A value iterator.
     */
    @Override
    public Iterator<Integer> iterator() {
        return new StateIterator(this);
    }

    /**
     * Represents the state as a string, except
     * in a more compact fashion, using only
     * one line.
     *
     * @return {{row1}, {row2}, ... {rowBoardSize}}
     */
    public String toCompactString() {

        StringBuilder builder = new StringBuilder();

        builder.append("{");

        for (int row = 0; row < getBoardSize(); row++) {

            builder.append("{");

            for (int col = 0; col < getBoardSize(); col++) {

                builder.append(this.board[row][col]);

                if (col < getBoardSize() - 1) {
                    builder.append(", ");
                }

            }

            builder.append("}");

            if (row < getBoardSize() - 1) {
                builder.append(", ");
            }

        }

        builder.append("}");
        return builder.toString();

    }

    /**
     * A string representation of the state,
     * where each row is separated by a newline
     * and each value in a row is separated by a space.
     *
     * @return A string representation of the state.
     */
    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        for (int row = 0; row < getBoardSize(); row++) {
            for (int col = 0; col < getBoardSize(); col++) {

                builder.append(this.board[row][col]);

                if (col < getBoardSize() - 1) {
                    builder.append(" ");
                }

            }

            if (row < getBoardSize() - 1) {
                builder.append("\n");
            }

        }

        return builder.toString();

    }

    /**
     * @return All valid moves that can be performed
     * on this state.
     */
    public List<Move> validMoves() {

        // Loop through all blank spaces
        LinkedList<Move> validMoves = new LinkedList<>();

        for (Space space : this.blankSpaces) {
            for (Direction direction : Direction.values()) {

                // Check if the space in this direction
                // of the blank space is valid and not blank
                Space newSpace = space.navigate(direction);
                int val = getValue(newSpace);

                if (val == BLANK_SPACE_VALUE || val == -1) {
                    continue;
                }

                // If there's a tile in this new space,
                // then generate a move to push it into
                // the blank space
                validMoves.push(new Move(newSpace, Direction.opposite(direction)));

            }
        }

        return validMoves;

    }

}
