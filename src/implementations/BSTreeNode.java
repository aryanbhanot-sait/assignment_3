package implementations;

import java.io.Serializable;

/**
 * Represents a node in a Binary Search Tree (BST).
 * Each node contains an element of generic type E, and references to its left and right children.
 */

public class BSTreeNode<E> implements Serializable
{
    private static final long serialVersionUID = 1L;
    private E element;
    private BSTreeNode<E> left;
    private BSTreeNode<E> right;

    // Parameterized constructor to create a node with a specific element
    public BSTreeNode(E element)
    {
        this.element = element;
        this.left = null;
        this.right = null;
    }

    // Getter
    public E getElement()
    {
        return element;
    }
    // Setter
    public void setElement(E element)
    {
        this.element = element;
    }
    
    // Getters and setters for left and right children
    public BSTreeNode<E> getLeft()
    {
        return left;
    }

    public void setLeft(BSTreeNode<E> left)
    {
        this.left = left;
    }

    public BSTreeNode<E> getRight()
    {
        return right;
    }

    public void setRight(BSTreeNode<E> right)
    {
        this.right = right;
    }
}
