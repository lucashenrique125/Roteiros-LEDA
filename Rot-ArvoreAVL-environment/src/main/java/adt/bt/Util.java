package adt.bt;

import adt.bst.BSTNode;

public class Util {

	/**
	 * A rotacao a esquerda em node deve subir o seu filho a direita e
	 * retorna-lo em seguida
	 * 
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> leftRotation(BSTNode<T> node) {
		BSTNode<T> right = (BSTNode<T>) node.getRight();
		BSTNode<T> parent = (BSTNode<T>) node.getParent();

		node.setRight(right.getLeft());
		right.getLeft().setParent(node);
		right.setLeft(node);

		right.setParent(parent);
			
		if (isLeftChild(node)) {
			parent.setLeft(right);
			right.setParent(parent);
		} else if (isRightChild(node)) {
			parent.setRight(right);
			right.setParent(parent);
		}

		node.setParent(right);
		
		return right;
	}

	/**
	 * A rotacao a direita em node deve subir seu filho a esquerda s retorna-lo
	 * em seguida
	 * 
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> rightRotation(BSTNode<T> node) {
		BSTNode<T> left = (BSTNode<T>) node.getLeft();
		BSTNode<T> parent = (BSTNode<T>) node.getParent();

		node.setLeft(left.getRight());
		left.getRight().setParent(node);
		left.setRight(node);

		left.setParent(parent);
		
		if (isLeftChild(node)) {
			parent.setLeft(left);
			left.setParent(parent);
		} else if (isRightChild(node)) {
			parent.setRight(left);
			left.setParent(parent);
		}

		node.setParent(left);
		
		return left;

	}
	
	public static boolean isRightChild(BSTNode node) {
		if (node == null || node.isEmpty()) {
			return false;
		}
		return node.equals(node.getParent().getRight());
	}

	public static boolean isLeftChild(BSTNode node) {
		if (node == null || node.isEmpty()) {
			return false;
		}
		return node.equals(node.getParent().getLeft());
	}

}
