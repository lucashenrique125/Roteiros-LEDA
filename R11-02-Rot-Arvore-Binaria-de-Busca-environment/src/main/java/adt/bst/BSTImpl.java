package adt.bst;

import java.util.ArrayList;
import java.util.List;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		if (isEmpty()) {
			return -1;
		} else {
			return height(this.root);
		}
	}

	private int height(BSTNode<T> node) {
		if (node.isEmpty()) {
			return -1;
		} else {
			return 1 + Math.max(height((BSTNode<T>) node.getLeft()), height((BSTNode<T>) node.getRight()));
		}
	}

	@Override
	public BSTNode<T> search(T element) {
		if (element == null) {
			return new BSTNode<T>();
		}
		
		return search(this.root, element);
	}

	private BSTNode<T> search(BSTNode<T> node, T element) {
		if (node.isEmpty()) {
			return node;
		}

		if (node.getData().equals(element)) {
			return node;
		} else {
			if (element.compareTo(node.getData()) > 0) {
				return search((BSTNode<T>) node.getRight(), element);
			} else {
				return search((BSTNode<T>) node.getLeft(), element);
			}
		}
	}

	@Override
	public void insert(T element) {
		if(element == null){
			return;
		}
		
		insert(element, root, new BSTNode<T>());
	}

	private void insert(T element, BSTNode<T> node, BSTNode<T> parent){
		if(node.isEmpty()){
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			node.setParent(parent);
		}else if(element.compareTo(node.getData()) < 0){
			insert(element, (BSTNode<T>) node.getLeft(), node);
		}else if(element.compareTo(node.getData()) > 0){
			insert(element, (BSTNode<T>) node.getRight(), node);
		}
	}

	@Override
	public BSTNode<T> maximum() {
		return maximum(this.root);
	}

	private BSTNode<T> maximum(BSTNode<T> node) {
		if (node.isEmpty()) {
			return null;
		}else if (node.getRight().isEmpty()) {
			return node;
		} else {
			return maximum((BSTNode<T>) node.getRight());
		}
	}

	@Override
	public BSTNode<T> minimum() {
		return minimum(this.root);
	}

	private BSTNode<T> minimum(BSTNode<T> node) {
		if (node.isEmpty()) {
			return null;
		}else if (node.getLeft().isEmpty()) {
			return node;
		} else {
			return minimum((BSTNode<T>) node.getLeft());
		}
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		if (element == null) {
			return null;
		}
		
		BSTNode<T> node = search(element);
		
		if (node.isEmpty()) {
			return null;
		}else if (!node.getRight().isEmpty()) {
			return minimum((BSTNode<T>) node.getRight());
		}
		
		while (!node.isEmpty() && isRightSon(node, (BSTNode<T>) node.getParent())) {
			node = (BSTNode<T>) node.getParent();
		}
		
		if (node.equals(root)) {
			return null;
		}
		
		return (BSTNode<T>) node.getParent();

	}
	
	private boolean isRightSon(BSTNode<T> node, BSTNode<T> parent) {
		if (node == null || parent == null) {
			return false;
		}
		
		return parent.getRight().equals(node);
	}
	
	

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> node = search(element);
		if (node.equals(root) || node.isEmpty()){
			return null;
		}else if (!node.getLeft().isEmpty()) {
			return maximum((BSTNode<T>) node.getLeft());
		}
		
		while (isLeftSon(node, (BSTNode<T>) node.getParent()) && !node.isEmpty()) {
			node = (BSTNode<T>) node.getParent();
		}
		
		if (node.getParent().isEmpty()) {
			return null;
		}
		
		return (BSTNode<T>) node.getParent();
	}
	
	private boolean isLeftSon(BSTNode<T> node, BSTNode<T> parent) {
		if (node == null || parent.getLeft() == null) {
			return false;
		} else {
			return parent.getLeft().equals(node);
		}
	}

	@Override
	public void remove(T element) {
		if (element != null) {
			BSTNode<T> node = search(element);
			if (node != null) {
				remove(node);
			}
		}
	}
	
	private void remove(BSTNode<T> node) {

		if (node.isLeaf()) {
			if (node.equals(this.root)) {
				this.root = new BSTNode<T>();
			} else {
				if (node.getParent().getLeft().equals(node)) {
					node.getParent().setLeft(new BSTNode<T>());
				} else {
					node.getParent().setRight(new BSTNode<T>());
				}
			}
		
		} else if (!node.getRight().isEmpty() && node.getLeft().isEmpty()) {
			if (node.equals(this.root)) {
				this.root = (BSTNode<T>) node.getRight();
			} else {
		
				if (node.getParent().getLeft().equals(node)) {
					node.getParent().setLeft(node.getRight());
				} else {
					node.getParent().setRight(node.getRight());
				}
				node.getRight().setParent(node.getParent());
			}
		} else if (node.getRight().isEmpty() && !node.getLeft().isEmpty()) {
			if (node.equals(this.root)) {
				this.root = (BSTNode<T>) node.getLeft();
			} else {
				if (node.getParent().getLeft().equals(node)) {
					node.getParent().setLeft(node.getLeft());
				} else {
					node.getParent().setRight(node.getLeft());
				}
				node.getLeft().setParent(node.getParent());
			}
		} else {
			BSTNode<T> help = sucessor(node.getData());
			if (help == null) {
				help = predecessor(node.getData());
			}
			T auxData = node.getData();
			node.setData(help.getData());
			help.setData(auxData);
			remove(help);
		}
	}

	
	@Override
	public T[] preOrder() {
		List<T> preOrder = new ArrayList<T>(this.size());
		preOrder(preOrder, this.root);
		return preOrder.toArray((T[]) new Comparable[size()]);
	}

	private void preOrder(List list, BSTNode<T> node) {
		if (node.isEmpty()) {
			return;
		} else {
			list.add(node.getData());
			preOrder(list, (BSTNode<T>) node.getLeft());
			preOrder(list, (BSTNode<T>) node.getRight());
		}
	}

	@Override
	public T[] order() {
		List<T> order = new ArrayList<T>();
		order(order, this.root);
		return order.toArray((T[]) new Comparable[size()]);
	}

	private void order(List list, BSTNode<T> node) {
		if (node.isEmpty()) {
			return;
		} else {
			order(list, (BSTNode<T>) node.getLeft());
			list.add(node.getData());
			order(list, (BSTNode<T>) node.getRight());
		}
	}

	@Override
	public T[] postOrder() {
		List<T> postOrder = new ArrayList<T>(this.size());
		postOrder(postOrder, this.root);
		return postOrder.toArray((T[]) new Comparable[size()]);
	}

	private void postOrder(List list, BSTNode<T> node) {
		if (node.isEmpty()) {
			return;
		} else {
			postOrder(list, (BSTNode<T>) node.getLeft());
			postOrder(list, (BSTNode<T>) node.getRight());
			list.add(node.getData());
		}
	}


	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft())
					+ size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}
