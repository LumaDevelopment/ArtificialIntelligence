package me.joshuasheldon.sliding.objs;

import me.joshuasheldon.sliding.Utilities;

import java.util.Iterator;

/**
 * Iterates through all the values in a
 * given state.
 */
public class StateIterator implements Iterator<Integer> {

    /**
     * The state to iterate through.
     */
    private final State state;

    /**
     * The number of values on the board.
     */
    private final int numOfVals;

    /**
     * Our current position on the board.
     */
    private int index = 0;

    /**
     * Creates a new StateIterator.
     *
     * @param state The state to iterate through.
     */
    public StateIterator(State state) {
        this.state = state;
        this.numOfVals = state.getBoardSize() * state.getBoardSize();
    }

    /**
     * @return <code>true</code>, if there are more values
     * to iterate through, <code>false</code> otherwise.
     */
    @Override
    public boolean hasNext() {
        return index < numOfVals;
    }

    /**
     * Returns the next value in the state,
     * read from left to right, top to bottom.
     *
     * @return The next value in the state.
     */
    @Override
    public Integer next() {

        Integer value = state.getValue(Utilities.matrixIndexToSpace(index, state.getBoardSize()));
        index++;

        return value;

    }

}
