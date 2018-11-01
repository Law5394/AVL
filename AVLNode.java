package AVL;

/**
 * @author James Law
 *
 */

public class AVLNode<Key extends Comparable<Key>> {
	private Key value;
	private AVLNode<Key> leftChild;
	private AVLNode<Key> rightChild;
	int height;

	// constructor
	AVLNode(Key value) {
		this.value = value;
	}

	// key value getter
	public Key getValue() {
		return value;
	}

	// key value setter
	public void setValue(Key value) {
		this.value = value;
	}

	// left child getter
	public AVLNode<Key> getLeftChild() {
		return leftChild;
	}

	// left child setter
	public void setLeftChild(AVLNode<Key> leftChild) {
		this.leftChild = leftChild;
	}

	// right child getter
	public AVLNode<Key> getRightChild() {
		return rightChild;
	}

	// right child setter
	public void setRightChild(AVLNode<Key> rightChild) {
		this.rightChild = rightChild;
	}

	// to string
	public String toString() {
		return (value.toString());
	}

}
