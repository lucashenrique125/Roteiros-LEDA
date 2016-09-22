package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

/**
 * 
 * Performs consistency validations within a AVL Tree instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements AVLTree<T> {

	// TODO Do not forget: you must override the methods insert and remove
	// conveniently.

	public void insert(T element) {
		insert(element, super.getRoot(), new BSTNode<T>());
	}

	private void insert(T element, BSTNode<T> node, BSTNode<T> parent) {
		if (element == null) {
			return;
		}

		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			node.setParent(parent);
		} else if (element.compareTo(node.getData()) < 0) {
			insert(element, (BSTNode<T>) node.getLeft(), node);
		} else if (element.compareTo(node.getData()) > 0) {
			insert(element, (BSTNode<T>) node.getRight(), node);
		}

		rebalance(node);
	}

	public void remove(T element) {
		if(element == null){
			return;
		}
		
		BSTNode<T> found = search(element);

		remove(found);
	}

	public void remove(BSTNode<T> node) {
		if (!node.isEmpty()) {
			if (node.isLeaf()) {
				node.setData(null);
				rebalanceUp(node);
			} else if (hasOneChild(node)) {
				if (super.getRoot() != node) {
					if (isLeftChild(node, (BSTNode<T>) node.getParent())) {
						if (!node.getLeft().isEmpty()) {
							node.getParent().setLeft(node.getLeft());
						} else {
							node.getParent().setLeft(node.getRight());
						}
					} else { // node is right child
						if (!node.getLeft().isEmpty()) {
							node.getParent().setRight(node.getLeft());
						} else {
							node.getParent().setRight(node.getRight());
						}
					}
				} else {
					super.root = getNotNILChildRoot(node);
				}

				rebalanceUp(node);
			} else {
				BSTNode<T> sucessor = sucessor(node.getData());

				node.setData(sucessor.getData());

				remove(sucessor);
			}
		}
	}

	private BSTNode<T> getNotNILChildRoot(BSTNode<T> node) {
		if (!node.getLeft().isEmpty()) {
			return (BSTNode<T>) node.getLeft();
		} else if (!node.getRight().isEmpty()) {
			return (BSTNode<T>) node.getRight();
		} else {
			return new BSTNode<T>();
		}
	}

	private boolean hasOneChild(BSTNode<T> node) {
		return !node.isEmpty() && (hasLeftChild(node) && !hasRightChild(node))
				|| (!hasLeftChild(node) && hasRightChild(node));
	}

	private boolean hasLeftChild(BSTNode<T> node) {
		return !node.getLeft().isEmpty();
	}

	private boolean hasRightChild(BSTNode<T> node) {
		return !node.getLeft().isEmpty();
	}

	// AUXILIARY
	protected int calculateBalance(BSTNode<T> node) {
		if (!node.isEmpty()) {
			return height((BSTNode<T>) node.getLeft()) - super.height((BSTNode<T>) node.getRight());
		}

		return 0;
	}

	// AUXILIARY
	protected void rebalance(BSTNode<T> node) {
		if (node == null || node.isEmpty()) {
			return;
		}

		int balance = calculateBalance(node);

		if (Math.abs(balance) <= 1) {
			return;
		}

		if (balance > 1) { 
			int sonBalance = calculateBalance((BSTNode<T>) node.getLeft());

			if (sonBalance < 0) { 
				this.leftRotation((BSTNode<T>) node.getLeft());
			}

			this.rightRotation(node);
		} else { 
			int sonBalance = calculateBalance((BSTNode<T>) node.getRight());

			if (sonBalance > 0) {
				this.rightRotation((BSTNode<T>) node.getRight());
			}

			this.leftRotation(node);
		}
	}

	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		BSTNode<T> parent = (BSTNode<T>) node.getParent();

		while (!parent.isEmpty()) {
			rebalance(parent);

			parent = (BSTNode<T>) parent.getParent();
		}
	}

	// AUXILIARY
	protected void leftRotation(BSTNode<T> node) {
		if (node == null || node.isEmpty()) {
			return;
		}
		
		BSTNode<T> nodeReturn = Util.leftRotation(node);
		
		if (node == this.root) {
			this.root = nodeReturn;
		}
	}

	// AUXILIARY
	protected void rightRotation(BSTNode<T> node) {
		if (node == null || node.isEmpty()) {
			return;
		}
		
		BSTNode<T> nodeReturn = Util.rightRotation(node);
		
		if (node == this.root) {
			this.root = nodeReturn;
		}
	}

}
