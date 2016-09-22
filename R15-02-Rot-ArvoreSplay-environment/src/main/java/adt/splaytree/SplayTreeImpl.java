package adt.splaytree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

public class SplayTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements SplayTree<T> {

	private void splay(BSTNode<T> node) {
		if (node == null || node.isEmpty() || node == this.root)
			return ;

		BSTNode<T> parent = (BSTNode<T>) node.getParent();
		
		if(node.getParent() == super.getRoot()){
			if(isLeftChild(node, parent)){
				rightRotation(parent);
			}else if(isRightChild(node, parent)){
				leftRotation(parent);
			}
		}else{
			if(isLeftChild(parent, (BSTNode<T>) parent.getParent()) && isLeftChild(node, parent)){
				rightRotation((BSTNode<T>) parent.getParent());
				rightRotation(parent);
			}else if (isRightChild(parent, (BSTNode<T>) parent.getParent()) && isRightChild(node, parent)){
				leftRotation((BSTNode<T>) parent.getParent());
				leftRotation(parent);
			}else if (isLeftChild(parent, (BSTNode<T>) parent.getParent()) && isRightChild(node, parent)){
				leftRotation(parent);
				rightRotation((BSTNode<T>) parent.getParent());
			}else if (isRightChild(parent, (BSTNode<T>) parent.getParent()) && isLeftChild(node, parent)){
				rightRotation(parent);
				leftRotation((BSTNode<T>) parent.getParent());
			}
		
			splay(node);
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

	public BSTNode<T> search(T element) {
		BSTNode<T> node = super.search(element);

		if (!node.isEmpty()) {
			splay(node);
		} else {
			splay((BSTNode<T>) node.getParent());
		}

		return node;
	}

	public void insert(T element) {
		BSTNode<T> node = insert(element, super.getRoot(), new BSTNode<T>());

		splay(node);
	}

	public void remove(T element) {
		BSTNode<T> node = super.search(element);

		if (!node.isEmpty()) {
			super.remove(node);
			
			splay((BSTNode<T>) node.getParent());
		} else {
			splay((BSTNode<T>) node.getParent());
		}

		

	}

	private BSTNode<T> insert(T element, BSTNode<T> node, BSTNode<T> parent) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			node.setParent(parent);

			return node;
		} else if (element.compareTo(node.getData()) < 0) {
			return insert(element, (BSTNode<T>) node.getLeft(), node);
		} else if (element.compareTo(node.getData()) > 0) {
			return insert(element, (BSTNode<T>) node.getRight(), node);
		}

		return new BSTNode<T>();
	}
}
