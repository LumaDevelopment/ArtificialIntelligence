package me.joshuasheldon.sliding.state_lib;

/**
 * Node object used for the tree structure of the StateLibrary.
 * Nodes can either be "branches" or "leaves". Branches
 * have children, whereas leaves do not. A leaf represents
 * a state in the tree, and a branch represents a route to
 * multiple other states in the tree. The tree is navigated
 * by the values of the spaces in the state.
 */
public abstract class AbstractStateLibraryNode {

    /**
     * The children of this node. <code>null</code>
     * if this node is a leaf.
     */
    protected Object children = null;

    /**
     * Any parameters that are used by the specific
     * implementation of the node. For instance,
     * the HashMap implementation needs an
     * initial capacity and the array implementation
     * needs the board size.
     */
    protected Object[] parameters;

    /**
     * Create a new node for the state library.
     *
     * @param isLeaf     Whether this node is a leaf or a branch.
     * @param parameters Any implementation-specific parameters.
     */
    protected AbstractStateLibraryNode(boolean isLeaf, Object[] parameters) {

        if (parameters == null) {
            throw new IllegalArgumentException("Null parameters passed into state library node constructor!");
        }

        this.parameters = parameters;

        // If we are a branch, initialize our children.
        if (!isLeaf) {
            instantiateChildrenVariable();
        }

    }

    /**
     * Adds a new branch as a child of this node
     * with the given index.
     *
     * @param index The index of the new branch.
     * @return The new branch node.
     */
    public AbstractStateLibraryNode addBranch(int index) {
        return addChild(index, false);
    }

    /**
     * Adds a new branch or leaf as a child of
     * this node with the given index.
     *
     * @param index  The index of the new child.
     * @param isLeaf Whether the new child is a leaf
     *               or a branch.
     * @return The new child node.
     */
    private AbstractStateLibraryNode addChild(int index, boolean isLeaf) {

        if (isLeaf() || index < 0) {
            return null;
        }

        AbstractStateLibraryNode newNode = createNode(isLeaf, this.parameters);
        addChild(index, newNode);
        return newNode;

    }

    /**
     * Implementation-specific method for the subclass
     * to add the given node into the data structure
     * that <code>children</code> is implemented with.
     *
     * @param index The index of the new child.
     * @param node  The new child node.
     */
    abstract void addChild(int index, AbstractStateLibraryNode node);

    /**
     * Adds a new leaf as a child of this node with
     * the given index.
     *
     * @param index The index of the new leaf.
     * @return The new leaf node.
     */
    public AbstractStateLibraryNode addLeaf(int index) {
        return addChild(index, true);
    }

    /**
     * Because we don't know the parameters of the
     * constructor of the subclass, we leave the
     * creation of new library node objects
     * to the subclass.
     *
     * @param isLeaf     Whether the new node is a leaf or a branch.
     * @param parameters Any implementation-specific parameters.
     * @return The new node.
     */
    abstract AbstractStateLibraryNode createNode(boolean isLeaf, Object[] parameters);

    /**
     * Gets the child of this node with the given index.
     *
     * @param index The index of the child to get.
     * @return The child node, or <code>null</code>
     * if this node is a leaf or the index is invalid.
     */
    public AbstractStateLibraryNode getChild(int index) {

        if (isLeaf() || index < 0) {
            return null;
        }

        return getChild(index, null);

    }

    /**
     * Implementation-specific method for the subclass
     * to get the child of this node with the given index
     * from the data structure that <code>children</code>
     * is implemented with.
     *
     * @param index   The index of the child to get.
     * @param discard A dummy variable to allow for
     *                method overloading.
     * @return The child node, or <code>null</code>
     * if the index is invalid or the child does not exist.
     */
    abstract AbstractStateLibraryNode getChild(int index, Object discard);

    /**
     * Implementation-specific method for the subclass
     * to initialize the <code>children</code> variable.
     * This method is called in the constructor of this class.
     */
    abstract void instantiateChildrenVariable();

    /**
     * Checks if this node is a leaf.
     *
     * @return <code>true</code> if this node is a leaf,
     * <code>false</code> if this node is a branch.
     */
    public boolean isLeaf() {
        return this.children == null;
    }

}
