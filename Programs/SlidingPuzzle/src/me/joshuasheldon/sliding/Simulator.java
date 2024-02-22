package me.joshuasheldon.sliding;

import me.joshuasheldon.sliding.objs.Move;
import me.joshuasheldon.sliding.objs.State;
import me.joshuasheldon.sliding.state_lib.HashMapStateLibraryNode;
import me.joshuasheldon.sliding.state_lib.StateLibrary;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Simulator {

    /**
     * Get to the goal state from the given state using
     * best first search, with the Manhattan distance
     * as the heuristic.
     *
     * @param state The state to start from.
     */
    public static void solveWithBestFirstSearch(State state) {

        // Establish library
        HashMapStateLibraryNode root = new HashMapStateLibraryNode(false, 2);
        StateLibrary library = new StateLibrary(state.getBoardSize(), root);
        library.addStateToLibrary(state);

        // Set up queue
        PriorityQueue<State> queue = new PriorityQueue<>();
        queue.add(state);

        // Calculate the goal so we can
        // verify our moves
        State goal = state.goalState();

        // Sanity check
        if (state.equals(goal)) {
            System.out.println("Given state is already at the goal!");
            return;
        }

        // Record statistics
        int moves = -1;   // number of moves made to get to the goal
        int explored = 0; // number of states added to the queue
        int denied = 0;   // number of states that weren't added to the queue because they were already visited

        // Keep going until we've explored all states
        while (!queue.isEmpty()) {

            // Go to next state, update statistics,
            // and print position
            moves++;
            State current = queue.poll();

            // Investigate all valid moves that could be
            // made from the current state
            for (Move move : current.validMoves()) {

                // Create new state object by applying
                // this move to this state
                State next = current.apply(move);

                // Check if the neighbor is the goal
                if (next.equals(goal)) {

                    // Record memory statistics
                    long memoryInUseBeforeGC = Utilities.getMemoryInUse();
                    Runtime.getRuntime().gc();
                    long memoryInUseAfterGC = Utilities.getMemoryInUse();

                    // Report all statistics
                    System.out.println("Successfully found solution in " + moves + " moves! Added " + explored +
                            " states to queue and denied " + denied + " previously explored states! Using " +
                            memoryInUseBeforeGC + " bytes of memory before GC, and " + memoryInUseAfterGC +
                            " bytes of memory after GC!");
                    return;

                }

                // Check if we've encountered this state before
                if (!library.isStateInLibrary(next)) {
                    // Mark state as visited, add it to
                    // the queue, and update statistics
                    library.addStateToLibrary(next);
                    queue.add(next);
                    explored++;
                } else {
                    // We've seen this state before, its either
                    // already in the queue or we've already
                    // processed it, so nothing to do except
                    // update statistics
                    denied++;
                }

            }

        }

        // If we escape the loop, we didn't find a solution
        System.out.println("No solution found!");

    }

    /**
     * Get to the goal state from the given state using
     * breadth first search.
     *
     * @param state The state to start from.
     */
    public static void solveWithBreadthFirstSearch(State state) {

        HashMapStateLibraryNode root = new HashMapStateLibraryNode(false, 2);
        StateLibrary library = new StateLibrary(state.getBoardSize(), root);

        State goal = state.goalState();

        Queue<State> queue = new LinkedList<>();
        queue.add(state);

        int runs = 0;

        while (!queue.isEmpty()) {

            runs++;
            State current = queue.poll();

            if (current.equals(goal)) {
                System.out.println("Successfully found solution in " + runs + " runs!");
                return;
            }

            library.addStateToLibrary(current);

            for (Move move : current.validMoves()) {
                State next = current.apply(move);
                if (!library.isStateInLibrary(next)) {
                    System.out.println("Applying " + move + " to " + current.toCompactString());
                    queue.add(next);
                }
            }

        }

        System.out.println("No solution found!");

    }

}
