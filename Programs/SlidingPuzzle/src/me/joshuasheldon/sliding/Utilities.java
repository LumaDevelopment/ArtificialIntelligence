package me.joshuasheldon.sliding;

import me.joshuasheldon.sliding.objs.Space;
import me.joshuasheldon.sliding.objs.State;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Utilities {

    /**
     * @return The amount of memory currently in use.
     */
    public static long getMemoryInUse() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    /**
     * @return The Manhattan distance between two
     * (x, y) pairs.
     */
    public static int manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x2 - x1) + Math.abs(y2 - y1);
    }

    /**
     * Using the index of the traversal within a matrix
     * and the size of the matrix, create a Space object
     * that represents the position of the index within
     * the matrix.
     *
     * @param index     The traversal index (i.e. in a
     *                  3x3 matrix, if this value was
     *                  5 it would be the third element
     *                  on the second row (matrix[1][2])).
     * @param boardSize The length of one side of the
     *                  matrix.
     * @return The Space object that represents the
     * position of the index within the matrix.
     */
    public static Space matrixIndexToSpace(int index, int boardSize) {
        return new Space(index % boardSize, index / boardSize);
    }

    /**
     * Generates a random state with the given
     * board size and number of blank spaces.
     *
     * @param boardSize   The length of each side of the board.
     * @param blankSpaces The number of spaces without a tile.
     * @return A random state.
     */
    public static State getRandomState(int boardSize, int blankSpaces) {

        // Define board
        int[][] board = new int[boardSize][boardSize];

        ArrayList<Integer> numbers = new ArrayList<>();

        // Add blank spaces first
        for (int i = 0; i < blankSpaces; i++) {
            numbers.add(0);
        }

        // Add all other numbers
        int val = 1;
        while (numbers.size() < (boardSize * boardSize)) {
            numbers.add(val);
            val++;
        }

        // Shuffle
        Collections.shuffle(numbers);

        // Fill board
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                board[row][col] = numbers.get((row * boardSize) + col);
            }
        }

        // Create State object
        return new State(board);

    }

    /**
     * Validates whether the given 2D array is valid to be
     * a state. For a state to be valid:<br>
     * 1) The 2D array representing the state must be square.<br>
     * 2) Each value must be unique, except for 0.<br>
     * 3) There must be at least one 0 in the state.<br>
     * 4) The values must be in the range [0, n^2),
     * where n is the length of one side of the
     * state.<br>
     * 5) The non-zero values must be consecutive
     * (i.e. no {{1, 2, 3}, {4, 5, 6}, {7, 9, 0}}).<br>
     *
     * @param potentialState The 2D array to validate
     * @return The blank spaces if the state
     * is valid, or <code>null</code> if the state
     * is invalid.
     */
    public static LinkedList<Space> validateState(int[][] potentialState) {

        if (potentialState == null) {
            return null;
        }

        LinkedList<Space> blankSpaces = new LinkedList<>();
        boolean[] foundValues = new boolean[potentialState.length * potentialState.length];

        for (int row = 0; row < potentialState.length; row++) {

            // Validate condition #1
            if (potentialState[row] == null || potentialState[row].length != potentialState.length) {
                return null;
            }

            for (int col = 0; col < potentialState[row].length; col++) {

                int value = potentialState[row][col];

                // Validate condition #4
                if (value < 0 || value >= foundValues.length) {
                    return null;
                }

                // Validate condition #2
                if (value == 0) {
                    blankSpaces.push(new Space(col, row));
                } else if (foundValues[value]) {
                    return null;
                } else {
                    foundValues[value] = true;
                }

            }

        }

        // Validate condition #3
        if (blankSpaces.isEmpty()) {
            return null;
        }

        // Validate condition #5
        // In a 3x3 matrix with two blank spaces,
        // then [foundValues[1], foundValues[7]]
        // should be true
        // So, the iterator variable's interval
        // should be [1, (boardLength ^ 2) - blankSpaces]

        // The length of foundValues is boardLength ^ 2,
        // so simply reuse in interval end calculation
        int intervalEnd = foundValues.length - blankSpaces.size();

        for (int i = 1; i <= intervalEnd; i++) {
            if (!foundValues[i]) {
                return null;
            }
        }

        return blankSpaces;

    }

}
