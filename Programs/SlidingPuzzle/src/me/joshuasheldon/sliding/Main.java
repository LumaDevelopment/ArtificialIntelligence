package me.joshuasheldon.sliding;

import me.joshuasheldon.sliding.objs.State;

public class Main {

    public static void main(String[] args) {

        int[][] boardOne = new int[][]{
                {1, 2, 0},
                {4, 5, 3},
                {7, 8, 6}
        };

        int[][] boardTwo = new int[][]{
                {1, 4, 5, 6},
                {10, 2, 7, 8},
                {11, 12, 3, 9},
                {13, 14, 0, 15}
        };

        State stateOne = new State(boardOne);
        State stateTwo = new State(boardTwo);

        State randomState = Utilities.getRandomState(5, 2);
        System.out.println("Random State\n" + randomState + "\n");

        System.out.println("Running Best First Search...");
        trial(() -> Simulator.solveWithBestFirstSearch(stateTwo));

    }

    /**
     * Runs the given task, recording how long it takes to
     * complete, and printing it to the console.
     *
     * @param runnable The task to run.
     */
    public static void trial(Runnable runnable) {
        long startTime = System.currentTimeMillis();
        runnable.run();
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + "ms");
    }

}