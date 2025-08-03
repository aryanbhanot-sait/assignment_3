package implementations;

import utilities.BSTreeADT;
import utilities.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Implementation of a Binary Search Tree (BST) that implements BSTreeADT interface.
 * The tree maintains elements in sorted order and provides traversal methods.
 *
 * @param <E> The type of elements stored in the BST, which must be comparable.
 */

public class BSTree<E extends Comparable<? super E>> implements BSTreeADT<E>
{
    private BSTreeNode<E> root;
    private int size;

    // Default constructor initializes an empty BST
    public BSTree()
    {
        root = null;
        size = 0;
    }
    // Parameterized constructor initializes the BST with a single element
    public BSTree(E element)
    {
        root = new BSTreeNode<>(element);
        size = 1;
    }

    /* Interface Implementation */

    /** 
     * Returns the root node of the BST. 
     * @return the root node of the tree
     * @throws NullPointerException if the tree is empty.
    */
    @Override
    public BSTreeNode<E> getRoot() throws NoSuchElementException
    {
        if (isEmpty())
        {
            throw new NoSuchElementException("The tree is empty.");
        }
        return root;
    }

    /**
     * Returns the height of the BST.
     * @return the height of the tree
     */
    @Override
    public int getHeight()
    {
        return getHeight(root);
    }

    /**
     * Helper method to calculate the height of the tree recursively.
     * @param node the current node
     * @return the height of the subtree rooted at the given node
     */
    private int getHeight(BSTreeNode<E> node)
    {
        if (node == null)
        {
            return 0;
        }
        return 1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight()));
    }

    /**
     * Returns the number of elements in the BST.
     * @return the size of the tree
     */
    @Override
    public int size()
    {
        return size;
    }

    /**
     * Checks if the BST is empty.
     * @return true if the tree is empty, false otherwise
     */
    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Clears the BST, removing all elements.
     */
    @Override
    public void clear()
    {
        root = null;
        size = 0;
    }

    /**
     * Checks if the BST contains a specific element.
     * @param entry the element to search for
     * @return true if the element is found, false otherwise
     * @throws NullPointerException if the entry is null
     */
    @Override
    public boolean contains(E entry) throws NullPointerException
    {
        if (entry == null)
        {
            throw new NullPointerException("The entry cannot be null.");
        }
        return search(entry) != null;
    }

    /**
     * Searches for a specific element in the BST and returns the corresponding node.
     * @param entry the element to search for
     * @return the node containing the element, or null if not found
     * @throws NullPointerException if the entry is null
     */
    @Override
    public BSTreeNode<E> search(E entry) throws NullPointerException
    {
        if (entry == null)
        {
            throw new NullPointerException("The entry cannot be null.");
        }
        return search(root, entry);
    }
    /**
     * Helper method to search for an element starting from a given node recursively.
     * @param node the current node to search from
     * @param entry the element to search for
     * @return the node containing the element, or null if not found
     */
    private BSTreeNode<E> search(BSTreeNode<E> node, E entry)
    {
        if (node == null)
        {
            return null;
        }
        int comparison = entry.compareTo(node.getElement());
        if (comparison == 0)
        {
            return node;
        }
        else if (comparison < 0)
        {
            return search(node.getLeft(), entry);
        }
        else
        {
            return search(node.getRight(), entry);
        }
    }

    /**
     * Adds a new element to the BST.
     * @param newEntry the element to add
     * @return true if the element was added, false if it already exists
     * @throws NullPointerException if the new entry is null
     */
    @Override
    public boolean add(E newEntry) throws NullPointerException
    {
        if (newEntry == null)
        {
            throw new NullPointerException("The new entry cannot be null.");
        }
        if (isEmpty())
        {
            root = new BSTreeNode<>(newEntry);
            size++;
            return true;
        }
        return add(root, newEntry);
    }
    /**
     * Helper method to add a new element starting from a given node recursively.
     * @param node the current node to add the element to
     * @param newEntry the element to add
     * @return true if the element was added, false if it already exists
     */
    private boolean add(BSTreeNode<E> node, E newEntry)
    {
        int comparison = newEntry.compareTo(node.getElement());
        if (comparison == 0)
        {
            return false; 
        }
        else if (comparison < 0)
        {
            if (node.getLeft() == null)
            {
                node.setLeft(new BSTreeNode<>(newEntry));
                size++;
                return true;
            }
            else
            {
                return add(node.getLeft(), newEntry);
            }
        }
        else
        {
            if (node.getRight() == null)
            {
                node.setRight(new BSTreeNode<>(newEntry));
                size++;
                return true;
            }
            else
            {
                return add(node.getRight(), newEntry);
            }
        }
    }

    /**
     * Removes the smallest element from the BST.
     * @return the node containing the smallest element, or null if the tree is empty
     */
    @Override
    public BSTreeNode<E> removeMin()
    {
        if (isEmpty())
        {
            return null;
        }
        if (root.getLeft() == null)
        {
            BSTreeNode<E> min = root;
            root = root.getRight();
            size--;
            return min;
        }
        return removeMin(root);
    }
    /**
     * Helper method to remove the smallest element starting from a given node recursively.
     * @param node the current node to search for the smallest element
     * @return the node containing the smallest element
     */
    private BSTreeNode<E> removeMin(BSTreeNode<E> node)
    {
        if (node.getLeft().getLeft() == null)
        {
            BSTreeNode<E> min = node.getLeft();
            node.setLeft(node.getLeft().getRight());
            size--;
            return min;
        }
        return removeMin(node.getLeft());
    }

    /**
     * Removes the largest element from the BST.
     * @return the node containing the largest element, or null if the tree is empty
     */
    @Override
    public BSTreeNode<E> removeMax()
    {
        if (isEmpty())
        {
            return null;
        }
        if (root.getRight() == null)
        {
            BSTreeNode<E> max = root;
            root = root.getLeft();
            size--;
            return max;
        }
        return removeMax(root);
    }
    /**
     * Helper method to remove the largest element starting from a given node recursively.
     * @param node the current node to search for the largest element
     * @return the node containing the largest element
     */
    private BSTreeNode<E> removeMax(BSTreeNode<E> node)
    {
        if (node.getRight().getRight() == null)
        {
            BSTreeNode<E> max = node.getRight();
            node.setRight(node.getRight().getLeft());
            size--;
            return max;
        }
        return removeMax(node.getRight());
    }

    /* Iterator Implementations */

}
