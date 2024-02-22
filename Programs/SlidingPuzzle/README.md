# Sliding Puzzle Solver

Intakes *n*-puzzle problems and provides a framework to represent the different components of the problem as objects, as
well as a couple of rudimentary algorithms for solving them.

## To-Do

This program uses a "state library" system to keep track of visited states. It is a tree-based structure where each
layer is a slot in the sliding puzzle.

There are three provided implementations used to keep track of the children of these nodes: array, HashMap, and TreeMap.
Arrays are fast but memory-intensive, HashMaps are expected to be fast but may use more memory than is necessary, and
TreeMaps are (relatively) slow but don't use more memory than they need.

One consequence of the tree structure is that the layers will get less dense as we descend the tree. Therefore, it may
be a good idea to have the first third of the tree use the array implementation, the second third of the tree use the
Hashmap implementation, and the last third of the tree use the TreeMap implementation.