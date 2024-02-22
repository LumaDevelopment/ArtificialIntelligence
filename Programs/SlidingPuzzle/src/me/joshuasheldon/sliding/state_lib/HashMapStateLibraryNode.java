package me.joshuasheldon.sliding.state_lib;

import java.util.HashMap;

/**
 * A node in the state library that uses a HashMap
 * to store its children. This is expected to have
 * speed equivalent to the array implementation, but
 * that may not always be the case. The memory
 * efficiency of this implementation is a middle ground
 * between the array and TreeMap implementations, as
 * the HashMap may allocate additional memory for
 * children that it might not or literally cannot have.
 */
public class HashMapStateLibraryNode extends AbstractStateLibraryNode {

    /**
     * Create a new state library node.
     *
     * @param isLeaf          Whether this node is a leaf
     * @param initialCapacity The initial capacity of the HashMap
     */
    public HashMapStateLibraryNode(boolean isLeaf, int initialCapacity) {
        super(isLeaf, new Object[]{initialCapacity});
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
        HashMap<Integer, HashMapStateLibraryNode> children = (HashMap<Integer, HashMapStateLibraryNode>) this.children;
        children.put(index, (HashMapStateLibraryNode) node);
    }

    /**
     * @param isLeaf     Whether the new node is a leaf or a branch.
     * @param parameters Should contain a single integer, representing
     *                   the initial capacity of the HashMap.
     * @return A new instance of <code>HashMapStateLibraryNode</code>.
     */
    @Override
    AbstractStateLibraryNode createNode(boolean isLeaf, Object[] parameters) {
        return new HashMapStateLibraryNode(isLeaf, (int) parameters[0]);
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
        HashMap<Integer, HashMapStateLibraryNode> children = (HashMap<Integer, HashMapStateLibraryNode>) this.children;
        return children.get(index);
    }

    /**
     * Instantiates the children variable as a new
     * HashMap with the initial capacity specified
     * in the constructor.
     */
    @Override
    void instantiateChildrenVariable() {
        int initialCapacity = (int) this.parameters[0];
        this.children = new HashMap<Integer, HashMapStateLibraryNode>(initialCapacity);
    }

}
