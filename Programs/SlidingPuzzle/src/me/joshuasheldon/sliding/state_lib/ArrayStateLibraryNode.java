package me.joshuasheldon.sliding.state_lib;

/**
 * A node in the state library that uses an array
 * to store its children. This is the fastest
 * implementation of the state library, but it
 * is the least memory efficient, because it
 * allocates memory for every possible child
 * it could have, even if that state has not
 * been and will not be explored.
 */
public class ArrayStateLibraryNode extends AbstractStateLibraryNode {

    /**
     * Create a new state library node.
     *
     * @param isLeaf    Whether this node is a leaf
     * @param boardSize The size of the board, used to determine the
     *                  number of children this node can support.
     */
    public ArrayStateLibraryNode(boolean isLeaf, int boardSize) {
        super(isLeaf, new Object[]{boardSize});
    }

    /**
     * Adds the given node to this node's array of children,
     * at the given index.
     *
     * @param index The index of the new child.
     * @param node  The new child node.
     */
    @Override
    void addChild(int index, AbstractStateLibraryNode node) {

        ArrayStateLibraryNode[] children = (ArrayStateLibraryNode[]) this.children;

        if (index >= children.length) {
            return;
        }

        children[index] = (ArrayStateLibraryNode) node;

    }

    /**
     * @param isLeaf     Whether the new node is a leaf or a branch.
     * @param parameters Should contain a single integer, representing
     *                   the size of the board.
     * @return A new instance of <code>ArrayStateLibraryNode</code>.
     */
    @Override
    AbstractStateLibraryNode createNode(boolean isLeaf, Object[] parameters) {
        return new ArrayStateLibraryNode(isLeaf, (int) parameters[0]);
    }

    /**
     * @param index   The index of the child to get.
     * @param discard A dummy variable to allow for
     *                method overloading.
     * @return The child at the given index, or
     * <code>null</code> if no child exists at the
     * given index.
     */
    @Override
    public AbstractStateLibraryNode getChild(int index, Object discard) {

        ArrayStateLibraryNode[] children = (ArrayStateLibraryNode[]) this.children;

        if (index >= children.length) {
            return null;
        }

        return children[index];

    }

    /**
     * Creates a new array for the children of this node,
     * with a size equal to <code>boardSize</code>^2.
     */
    @Override
    void instantiateChildrenVariable() {
        int boardSize = (int) this.parameters[0];
        this.children = new ArrayStateLibraryNode[boardSize * boardSize];
    }

}
