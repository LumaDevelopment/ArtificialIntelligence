package me.joshuasheldon.sliding.state_lib;

import java.util.TreeMap;

/**
 * A node in the state library that uses a TreeMap
 * to store its children. This implementation will
 * almost always be the slowest for insertion
 * and retrieval, but it is guaranteed to be the
 * most memory efficient, as it will only allocate
 * memory for children that it actually has.
 */
public class TreeMapStateLibraryNode extends AbstractStateLibraryNode {

    /**
     * Create a new state library node.
     *
     * @param isLeaf Whether this node is a leaf
     */
    public TreeMapStateLibraryNode(boolean isLeaf) {
        super(isLeaf, new Object[]{});
    }

    /**
     * Inserts the given parameters into the children
     * map as a pair, where the index is the key and
     * the node is the value.
     *
     * @param index The index of the new child.
     * @param node  The new child node.
     */
    @Override
    void addChild(int index, AbstractStateLibraryNode node) {
        TreeMap<Integer, TreeMapStateLibraryNode> children = (TreeMap<Integer, TreeMapStateLibraryNode>) this.children;
        children.put(index, (TreeMapStateLibraryNode) node);
    }

    /**
     * @param isLeaf     Whether the new node is a leaf or a branch.
     * @param parameters Should be null or empty.
     * @return A new instance of <code>TreeMapStateLibraryNode</code>.
     */
    @Override
    AbstractStateLibraryNode createNode(boolean isLeaf, Object[] parameters) {
        return new TreeMapStateLibraryNode(isLeaf);
    }

    /**
     * Retrieves the child node with the given index.
     *
     * @param index   The index of the child to get.
     * @param discard A dummy variable to allow for
     *                method overloading.
     * @return The child, or <code>null</code> if no
     * child exists at the given index.
     */
    @Override
    AbstractStateLibraryNode getChild(int index, Object discard) {
        TreeMap<Integer, TreeMapStateLibraryNode> children = (TreeMap<Integer, TreeMapStateLibraryNode>) this.children;
        return children.get(index);
    }

    /**
     * Instantiates the children variable as a TreeMap.
     */
    @Override
    void instantiateChildrenVariable() {
        this.children = new TreeMap<Integer, TreeMapStateLibraryNode>();
    }

}
