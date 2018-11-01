package AVL;

/**
 * @author James Law
 *
 */

import java.util.ArrayList;

public class AVLTree<Key extends Comparable<Key>> {

	private AVLNode<Key> root;

	// returns true if k is in the BST. false if not.
	public boolean contains(Key k) {
		AVLNode<Key> searchNode = root;
		while (searchNode != null) {

			// checks to see if k is less than current node value if yes it goes to left
			// child
			if (k.compareTo(searchNode.getValue()) == -1)
				searchNode = searchNode.getLeftChild();

			// checks to see if k is greater than current node value if yes goes to right
			// child
			else if (k.compareTo(searchNode.getValue()) == 1)
				searchNode = searchNode.getRightChild();
			// if the value does not get to null then the k must match the vlaue of the
			// current node
			else
				return true;
		}

		return false;
	}

	// if tree is empty inserts node else it calls insert with root as a parameter
	public void insert(Key k) {
		if (root == null)
			root = new AVLNode<Key>(k);
		else
			insert(root, k);
	}

	// recursively inserts node
	private AVLNode<Key> insert(AVLNode<Key> node, Key k) {

		// base case
		if (node == null)
			return new AVLNode<Key>(k);

		// node to be inserted is less than current node
		else if (k.compareTo(node.getValue()) == -1)
			node.setLeftChild(insert(node.getLeftChild(), k));
		// node to be inserted is greater than current node
		else
			node.setRightChild(insert(node.getRightChild(), k));

		// updates the heights
		int leftTreeHeight = findHeight(node.getLeftChild());
		int rightTreeHeight = findHeight(node.getRightChild());
		if (leftTreeHeight > rightTreeHeight)
			node.height = leftTreeHeight + 1;
		else
			node.height = rightTreeHeight + 1;

		// checks for the balance if its balance>1 or balance<-1 the tree is unbalanced
		int balance = getBalance(node);

		/*
		 *  left left case: single right rotate around subtree root
		 *  
		 */
		if (balance > 1 && k.compareTo(node.getLeftChild().getValue()) == -1)
			return rightRotate(node);

		/*
		 * left right case: left rotate around subtree root's left child then right
		 * rotate the subtree root
		 */
		else if (balance > 1 && k.compareTo(node.getLeftChild().getValue()) == 1) {
			node.setLeftChild(leftRotate(node.getLeftChild()));
			return rightRotate(node);
		}

		/*
		 * right right case: single left rotate around subtree root
		 * 
		*/
		else if (balance < -1 && k.compareTo(node.getRightChild().getValue()) == 1) {
			return leftRotate(node);
		}

		/*
		 * right left case: right rotate around subtree root's right child then left
		 * rotate the subtree root
		 */
		else if (balance < -1 && k.compareTo(node.getRightChild().getValue()) == -1) {
			node.setRightChild(rightRotate(node.getRightChild()));
			return leftRotate(node);
		}

		return node;

	}

	// calls delete with the root as the parameter
	public void delete(Key k) {
		delete(root, k);
	}

	// recursively deletes the node with key k
	private AVLNode<Key> delete(AVLNode<Key> node, Key k) {
		// base case
		if (node == null)
			return node;
		// node to be deleted is less than current node
		else if (k.compareTo(node.getValue()) == -1)
			node.setLeftChild(delete(node.getLeftChild(), k));
		// node to be deleted is greater than current node
		else if (k.compareTo(node.getValue()) == 1)
			node.setRightChild(delete(node.getRightChild(), k));
		// node to be deleted is current node
		else {
			// if it has no left child replaces itself with right child
			if (node.getLeftChild() == null)
				return node.getRightChild();
			// if it has no right child replaces itself with left child
			else if (node.getRightChild() == null)
				return node.getLeftChild();
			// else it has two nodes so it has to replace itself with the smallest node in
			// right subtree list
			else {
				AVLNode<Key> successor = node.getRightChild();
				Key value = successor.getValue();
				while (successor.getLeftChild() != null) {

					successor = successor.getLeftChild();
					value = successor.getValue();
				}
				delete(root, value);
				node.setValue(value);
			}
		}

		// updates heights
		int leftTreeHeight = findHeight(node.getLeftChild());
		int rightTreeHeight = findHeight(node.getRightChild());
		if (leftTreeHeight > rightTreeHeight)
			node.height = leftTreeHeight + 1;

		else
			node.height = rightTreeHeight + 1;

		// checks for the balance if its balance>1 or balance<-1 the tree is unbalanced
		int balance = getBalance(node);

		// left left case: single right rotate around subtree root
		if (balance > 1 && k.compareTo(node.getLeftChild().getValue()) == -1)
			return rightRotate(node);
		/*
		 * left right case: left rotate around subtree root's left child then right
		 * rotate the subtree root
		 */
		else if (balance > 1 && k.compareTo(node.getLeftChild().getValue()) == 1) {
			node.setLeftChild(leftRotate(node.getLeftChild()));
			return rightRotate(node);
		}
		// right right case: single left rotate around subtree root
		else if (balance < -1 && k.compareTo(node.getRightChild().getValue()) == 1) {
			return leftRotate(node);
		}
		/*
		 * right left case: right rotate around subtree root's right child then left
		 * rotate the subtree root
		 */
		else if (balance < -1 && k.compareTo(node.getRightChild().getValue()) == -1) {
			node.setRightChild(rightRotate(node.getRightChild()));
			return leftRotate(node);
		}
		return node;

	}

	// right rotates the sub tree root
	private AVLNode<Key> rightRotate(AVLNode<Key> subRoot) {
		AVLNode<Key> leftChild = subRoot.getLeftChild();
		AVLNode<Key> subTree = leftChild.getRightChild();
		System.out.println("left rotate around " + subRoot);

		// checks to see if the sub tree root is the root of the avl tree and updates
		if (root == subRoot)
			root = leftChild;

		// sets the sub tree root to the right child of the sub tree roots left child
		// i.e. right rotates sub tree root
		leftChild.setRightChild(subRoot);

		// sets the sub tree roots left child's right sub tree to the left child of the
		// sub tree root
		subRoot.setLeftChild(subTree);

		// updates the heights
		int leftTreeHeight = findHeight(subRoot.getLeftChild());
		int rightTreeHeight = findHeight(subRoot.getRightChild());
		if (leftTreeHeight > rightTreeHeight)
			subRoot.height = leftTreeHeight + 1;
		else
			subRoot.height = rightTreeHeight + 1;

		leftTreeHeight = findHeight(leftChild.getLeftChild());
		rightTreeHeight = findHeight(leftChild.getRightChild());
		if (leftTreeHeight > rightTreeHeight)
			leftChild.height = leftTreeHeight + 1;
		else
			leftChild.height = rightTreeHeight + 1;

		return leftChild;
	}

	// left rotates the sub tree root
	private AVLNode<Key> leftRotate(AVLNode<Key> subRoot) {
		AVLNode<Key> rightChild = subRoot.getRightChild();
		AVLNode<Key> subTree = rightChild.getLeftChild();
		System.out.println("left rotate around " + subRoot);

		// checks to see if the sub tree root is the root of the avl tree and updates
		if (root == subRoot)
			root = rightChild;

		// sets the sub tree root to the left child of the sub tree roots right child
		// i.e. left rotates sub tree root
		rightChild.setLeftChild(subRoot);

		// sets the sub tree roots right child's left sub tree to the right child of the
		// sub tree root
		subRoot.setRightChild(subTree);

		// updates heights
		int leftTreeHeight = findHeight(subRoot.getLeftChild());
		int rightTreeHeight = findHeight(subRoot.getRightChild());
		if (leftTreeHeight > rightTreeHeight)
			subRoot.height = leftTreeHeight + 1;
		else
			subRoot.height = rightTreeHeight + 1;

		leftTreeHeight = findHeight(rightChild.getLeftChild());
		rightTreeHeight = findHeight(rightChild.getRightChild());
		if (leftTreeHeight > rightTreeHeight)
			rightChild.height = leftTreeHeight + 1;
		else
			rightChild.height = rightTreeHeight + 1;

		return rightChild;
	}

	// finds the height if its null it returns -1
	private int findHeight(AVLNode<Key> node) {
		if (node == null)
			return -1;

		return node.height;
	}

	// finds the balance of a node if its null it returns 0
	private int getBalance(AVLNode<Key> node) {
		if (node == null)
			return 0;

		return findHeight(node.getLeftChild()) - findHeight(node.getRightChild());
	}

	// preoder traversal calls preoder with root as parameter
	public void preorder() {
		preorder(root);
	}

	// recursive pre order traversal
	private void preorder(AVLNode<Key> searchNode) {
		if (searchNode != null) {
			System.out.println(searchNode.toString());

			preorder(searchNode.getLeftChild());
			preorder(searchNode.getRightChild());
		}
	}

	// prints a well formatted in order visit of every node in the BST.
	public void inorder() {
		inorder(root);

	}

	// recursive inorder traversal
	private void inorder(AVLNode<Key> searchNode) {
		if (searchNode != null) {
			inorder(searchNode.getLeftChild());
			System.out.println(searchNode.toString());
			inorder(searchNode.getRightChild());
		}

	}

	// prints a well formatted post order visit of every node in the BST.
	public void postorder() {
		postorder(root);
	}

	// recursive postorder traversal
	private void postorder(AVLNode<Key> searchNode) {
		if (searchNode != null) {
			postorder(searchNode.getLeftChild());
			postorder(searchNode.getRightChild());
			System.out.println(searchNode);
		}
	}

	// creates a string representation of the tree with levels
	public String toString() {
		String treeString = "";
		ArrayList<AVLNode<Key>> currentDepth = new ArrayList<AVLNode<Key>>();
		ArrayList<AVLNode<Key>> nextDepth = new ArrayList<AVLNode<Key>>();
		AVLNode<Key> node = root;
		AVLNode<Key> child;
		currentDepth.add(root);
		// loops for the height of the tree
		for (int i = 0; i < root.height + 1; i++) {

			treeString = treeString + "Level " + i + ": ";
			// loops while there are still nodes at the current level
			while (!currentDepth.isEmpty()) {
				node = currentDepth.get(0);

				treeString = treeString + node + " ";

				// removes node after printing and adds its children to the next depth list as
				// long as its not null
				currentDepth.remove(node);
				child = node.getLeftChild();
				if (child != null)
					nextDepth.add(child);
				child = node.getRightChild();
				if (child != null)
					nextDepth.add(child);

			}

			// adds all of the next depths nodes to the current depths and clears next depth
			currentDepth.addAll(nextDepth);
			nextDepth.clear();

			treeString = treeString + "\n";
		}
		return treeString;

	}

}