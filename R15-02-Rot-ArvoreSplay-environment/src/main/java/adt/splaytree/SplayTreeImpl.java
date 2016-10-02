package adt.splaytree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

public class SplayTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements SplayTree<T> {

	private void splay(BSTNode<T> node) {
		if (node == null || node.isEmpty() || node.equals(root)) {
			return;

		} else if (node.getParent().equals(super.getRoot())) {
			if (isRightChild(node)) {
				leftRotation(root);
			} else if (isLeftChild(node)) {
				rightRotation(root);
			}

		} else if (isRightChild((BSTNode<T>) node.getParent())) {
			if (isLeftChild(node)) {
				rightRotation((BSTNode<T>) node.getParent());
				leftRotation((BSTNode<T>) node.getParent());
			} else if (isRightChild(node)) {
				leftRotation((BSTNode<T>) node.getParent().getParent());
				leftRotation((BSTNode<T>) node.getParent());
			}
		} else if (isLeftChild((BSTNode<T>) node.getParent())) {
			if (isRightChild(node)) {
				leftRotation((BSTNode<T>) node.getParent());
				rightRotation((BSTNode<T>) node.getParent());
			}
			if (isLeftChild(node)) {
				rightRotation((BSTNode<T>) node.getParent().getParent());
				rightRotation((BSTNode<T>) node.getParent());
			}
		}

		splay(node);
	}

	public void insert(T element) {
		super.insert(element);

		BSTNode<T> node = super.search(element);

		splay(node);

	}

	public BSTNode<T> search(T element) {
		BSTNode<T> node = super.search(element);

		if (!node.isEmpty()) {
			splay(node);
		} else {
			splay((BSTNode<T>) node.getParent());
		}

		return node;
	}

	public void remove(T element) {
		if (!isEmpty()) {

			BSTNode<T> node = super.search(element);

			if (!node.isEmpty()) {
				super.remove(node);
			}

			splay((BSTNode<T>) node.getParent());
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
