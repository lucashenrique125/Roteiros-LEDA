package adt.rbtree;

import java.util.ArrayList;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;
import adt.rbtree.RBNode.Colour;

public class RBTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements RBTree<T> {

	public RBTreeImpl() {
		this.root = new RBNode<T>();
	}

	protected int blackHeight() {
		return blackHeight((RBNode<T>) super.getRoot());
	}

	protected int blackHeight(RBNode<T> node) {
		if (node.isEmpty()) {
			return 0;
		}

		RBNode<T> aux = (RBNode<T>) node;
		int height = 0;
		while (!aux.isEmpty()) {
			if (aux.getColour().equals(Colour.BLACK)) {
				height++;
			}

			aux = (RBNode<T>) aux.getLeft();
		}
		return height;
	}

	protected boolean verifyProperties() {
		boolean resp = verifyNodesColour() && verifyNILNodeColour() && verifyRootColour() && verifyChildrenOfRedNodes()
				&& verifyBlackHeight();

		return resp;
	}

	/**
	 * The colour of each node of a RB tree is black or red. This is guaranteed
	 * by the type Colour.
	 */
	private boolean verifyNodesColour() {
		return true; // already implemented
	}

	/**
	 * The colour of the root must be black.
	 */
	private boolean verifyRootColour() {
		return ((RBNode<T>) root).getColour() == Colour.BLACK; // already
																// implemented
	}

	/**
	 * This is guaranteed by the constructor.
	 */
	private boolean verifyNILNodeColour() {
		return true; // already implemented
	}

	/**
	 * Verifies the property for all RED nodes: the children of a red node must
	 * be BLACK.
	 */
	private boolean verifyChildrenOfRedNodes() {
		return verifyChildrenOfRedNodes((RBNode<T>) super.getRoot());
	}

	private boolean verifyChildrenOfRedNodes(RBNode<T> node) {
		if (!node.isEmpty()) {
			if (node.getColour() == Colour.RED) {
				RBNode<T> left = (RBNode<T>) node.getLeft();
				RBNode<T> right = (RBNode<T>) node.getRight();

				if (left.getColour() == Colour.RED || right.getColour() == Colour.RED) {
					return false;
				}
			}

			return this.verifyChildrenOfRedNodes((RBNode<T>) node.getLeft())
					&& this.verifyChildrenOfRedNodes((RBNode<T>) node.getRight());
		}

		return true;
	}

	/**
	 * Verifies the black-height property from the root. The method blackHeight
	 * returns an exception if the black heights are different.
	 */
	private boolean verifyBlackHeight() {
		if (this.root.isEmpty()) {
			return true;
		}

		int leftBlackHeight = blackHeight((RBNode<T>) super.getRoot().getLeft());
		int rightBlackHeight = blackHeight((RBNode<T>) super.getRoot().getRight());

		if (leftBlackHeight != rightBlackHeight || leftBlackHeight < 0 || rightBlackHeight < 0) {
			throw new RuntimeException("black heights are different");
		}

		return true;
	}

	@Override
	public void insert(T element) {
		if (element == null) {
			return;
		}

		insert(element, (RBNode<T>) root, new RBNode<T>());
	}

	private void insert(T element, RBNode<T> node, RBNode<T> parent) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setColour(Colour.RED);
			node.setLeft(new RBNode<T>());
			node.setRight(new RBNode<T>());
			node.setParent(parent);

			fixUpCase1(node);
		} else if (element.compareTo(node.getData()) < 0) {
			insert(element, (RBNode<T>) node.getLeft(), node);
		} else if (element.compareTo(node.getData()) > 0) {
			insert(element, (RBNode<T>) node.getRight(), node);
		}
	}

	@Override
	public RBNode<T>[] rbPreOrder() {
		ArrayList<RBNode<T>> nodes = new ArrayList<RBNode<T>>();

		preOrder((RBNode<T>) super.getRoot(), nodes);

		return makeArray(nodes);
	}

	private RBNode<T>[] makeArray(ArrayList<RBNode<T>> list) {
		RBNode<T>[] array = new RBNode[list.size()];

		for (int i = 0; i < array.length; i++) {
			array[i] = list.get(i);
		}

		return array;
	}

	private void preOrder(RBNode<T> node, ArrayList<RBNode<T>> list) {
		if (!node.isEmpty()) {
			list.add(node);
			preOrder((RBNode<T>) node.getLeft(), list);
			preOrder((RBNode<T>) node.getRight(), list);
		}
	}

	// FIXUP methods
	protected void fixUpCase1(RBNode<T> node) {
		if (node == super.getRoot()) {
			node.setColour(Colour.BLACK);
		} else {
			fixUpCase2(node);
		}
	}

	protected void fixUpCase2(RBNode<T> node) {
		if (!(((RBNode<T>) node.getParent()).getColour() == Colour.BLACK)) {
			fixUpCase3(node);
		}
	}

	protected void fixUpCase3(RBNode<T> node) {
		RBNode<T> p = (RBNode<T>) node.getParent();
		RBNode<T> g = (RBNode<T>) node.getParent().getParent();

		RBNode<T> u;

		if (p == g.getLeft()) {
			u = (RBNode<T>) g.getRight();
		} else {
			u = (RBNode<T>) g.getLeft();
		}

		if (u.getColour() == Colour.RED) {
			p.setColour(Colour.BLACK);
			u.setColour(Colour.BLACK);
			g.setColour(Colour.RED);

			fixUpCase1(g);
		} else {
			fixUpCase4(node);
		}

	}

	protected void fixUpCase4(RBNode<T> node) {
		RBNode<T> next = node;
		RBNode<T> p = (RBNode<T>) node.getParent();
		RBNode<T> g = (RBNode<T>) node.getParent().getParent();

		if (isRightChild(node, p) && isLeftChild((RBNode<T>) p, (RBNode<T>) g)) {
			leftRotation(p);

			next = (RBNode<T>) node.getLeft();
		} else if (isLeftChild(node, p) && isRightChild((RBNode<T>) p, (RBNode<T>) g)) {
			rightRotation(p);

			next = (RBNode<T>) node.getRight();
		}

		fixUpCase5(next);
	}

	protected void fixUpCase5(RBNode<T> node) {
		RBNode<T> p = (RBNode<T>) node.getParent();
		RBNode<T> g = (RBNode<T>) node.getParent().getParent();

		p.setColour(Colour.BLACK);
		g.setColour(Colour.RED);

		if (isLeftChild(node, p)) {
			rightRotation(g);
		} else {
			leftRotation(g);
		}
	}

	// AUXILIARY
	protected void leftRotation(BSTNode<T> node) {
		if (node == null || node.isEmpty()) {
			return;
		}

		RBNode<T> nodeReturn = (RBNode<T>) Util.leftRotation(node);

		if (node == this.root) {
			this.root = nodeReturn;
		}
	}

	// AUXILIARY
	protected void rightRotation(BSTNode<T> node) {
		if (node == null || node.isEmpty()) {
			return;
		}

		RBNode<T> nodeReturn = (RBNode<T>) Util.rightRotation(node);

		if (node == this.root) {
			this.root = nodeReturn;
		}
	}
}
