package me.joshuasheldon.sliding.state_lib;

import me.joshuasheldon.sliding.objs.State;

import java.util.Iterator;

/**
 * Maintains a tree structure of states so that
 * we can quickly check if we have been to a
 * state before. A little bit slower on the
 * state insertion end, where in the worst case
 * scenario we have to create a new node for
 * every space on the state. However, it is
 * much faster on the state checking end, where
 * we can quickly navigate to the state we are
 * looking for, and if a node doesn't exist, that
 * just makes the search that much faster.
 */
public class StateLibrary {

    /**
     * The size of the board, meaning the length
     * of each side of the board. This is used to
     * ensure that we are adding states of a
     * consistent size.
     */
    private final int boardSize;

    /**
     * The root of our tree of states. The children
     * of the root node distinguish inserted states
     * by the value of the first space in the state.
     */
    private final AbstractStateLibraryNode root;

    /**
     * Create a new library of states.
     *
     * @param boardSize The size of the boards that
     *                  will be stored in this library.
     * @param root      The root node of the library.
     *                  Allows the user to decide whether
     *                  they want to implement the tree
     *                  with arrays or HashMaps.
     */
    public StateLibrary(int boardSize, AbstractStateLibraryNode root) {

        this.boardSize = boardSize;
        this.root = root;

        if (root.isLeaf()) {
            throw new IllegalArgumentException("Root node of state library cannot be a leaf node!");
        }

    }

    /**
     * Add a new state to the library by iterating through
     * each of its values, and either navigating to an
     * existing "branch" node, or creating a new branch
     * node for this route in the tree.<br><br>
     * <p>
     * For the array implementation: Creates a maximum
     * of <code>(boardSize ^ 2) - 1</code> nodes, and each
     * new node that is not a "leaf" node creates an array of
     * <code>boardSize ^ 2</code> elements.<br><br>
     * <p>
     * For the HashMap implementation: Creates a maximum
     * of <code>(boardSize ^ 2) - 1</code> nodes, and each
     * new node that is not a "leaf" node creates a HashMap
     * with the initial capacity of the root node.<br><br>
     * <p>
     * For the TreeMap implementation: Creates a maximum
     * of <code>(boardSize ^ 2) - 1</code> nodes, and each
     * new node that is not a "leaf" node creates an empty
     * TreeMap. This is memory efficient, however insertion
     * time is O(log n) instead of O(1) or O(1) expected. So,
     * in the worst case where one is adding the final node to
     * every TreeMap along the way, the time complexity is
     * O((boardSize ^ 2) * log(boardSize ^ 2)).
     *
     * @param state The state to add to the library.
     * @return <code>true</code> if the state was added to
     * the library, <code>false</code> if the state was not
     * added to the library because its board size did not
     * match the board size of the library.
     */
    public boolean addStateToLibrary(State state) {

        // Ensure the state's board size matches the
        // board size of the library.
        if (state.getBoardSize() != this.boardSize) {
            return false;
        }

        // Prepare tracking variables for iterating
        Iterator<Integer> iterator = state.iterator();
        AbstractStateLibraryNode nav = this.root;
        int val = -1;

        // Loop through all spaces of the state
        while (iterator.hasNext()) {

            val = iterator.next();

            if (!iterator.hasNext()) {
                break;
            }

            // Navigate to the next branch in the
            // tree, and create one if it doesn't
            // exist.
            AbstractStateLibraryNode newNav = nav.getChild(val);

            if (newNav == null) {
                newNav = nav.addBranch(val);
            }

            nav = newNav;

        }

        // Add a leaf node at the end of the tree
        nav.addLeaf(val);

        return true;

    }

    /**
     * Checks if a state is present in the library
     * by navigating through the tree, and checking
     * if we ever reach a leaf node that matches
     * the given state. Makes <code>boardSize ^ 2</code>
     * hops through the tree at most.<br><br>
     * <p>
     * For the array implementation: Each hop is
     * O(1), guaranteeing a worst case scenario of
     * O(boardSize ^ 2).<br><br>
     * <p>
     * For the HashMap implementation: Each hop is
     * O(1) expected, depending on how the hash codes
     * of the values are distributed. In the worst
     * case scenario, the hash codes are all the same,
     * and the desired value is at the very end of
     * every bucket, giving a time complexity of
     * O(boardSize ^ 4), though I must stress the
     * odds of this are absolutely astronomical.
     * Expected worst case scenario is
     * O(boardSize ^ 2).<br><br>
     * <p>
     * For the TreeMap implementation: Each hop is
     * O(log(boardSize ^ 2)), guaranteeing a worst case
     * scenario of O((boardSize ^ 2) * log(boardSize ^ 2)).
     *
     * @param state The state to search for in the library.
     * @return <code>true</code> if the state is present
     * in the library, <code>false</code> if the state is
     * not present in the library, or if the state's board
     * size does not match the board size of the library.
     */
    public boolean isStateInLibrary(State state) {

        if (state.getBoardSize() != this.boardSize) {
            return false;
        }

        Iterator<Integer> iterator = state.iterator();
        AbstractStateLibraryNode nav = this.root;
        int val;

        while (iterator.hasNext()) {

            val = iterator.next();
            nav = nav.getChild(val);

            if (nav == null) {
                return false;
            }

        }

        return nav.isLeaf();

    }

}
