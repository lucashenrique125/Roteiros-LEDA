package adt.btree;

import java.util.LinkedList;
import java.util.List;

public class BTreeImpl<T extends Comparable<T>> implements BTree<T> {

	protected BNode<T> root;
	protected int order;

	public BTreeImpl(int order) {
		this.order = order;
		this.root = new BNode<T>(order);
	}

	@Override
	public BNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return this.root.isEmpty();
	}

	@Override
	public int height() {
		return height(this.root);
	}

	private int height(BNode<T> node) {
		if (node.isEmpty()) {
			return -1;
		} else {
			return 1 + height(node.children.get(0));
		}
	}

	@Override
	public BNode<T>[] depthLeftOrder() {
		List<BNode<T>> list = new LinkedList<BNode<T>>();

		addListDepthLeftOrder(root, list);

		BNode<T>[] aux = new BNode[list.size()];
		for (int i = 0; i < list.size(); i++) {
			aux[i] = list.get(i);
		}
		return aux;
	}

	private List<BNode<T>> addListDepthLeftOrder(BNode<T> node, List<BNode<T>> list) {
		if (!node.isEmpty()) {
			if (node.parent == null) {
				list.add(node);
			}

			for (int i = 0; i < node.children.size(); i++) {
				list.add(node.getChildren().get(i));

				addListDepthLeftOrder(node.getChildren().get(i), list);
			}
		}
		return list;
	}

	@Override
	public int size() {
		return size(root);
	}

	public int size(BNode<T> node) {
		if (node.isEmpty()) {
			return 0;
		} else {
			int size = node.size();

			for (BNode child : node.getChildren()) {
				size += size(child);
			}

			return size;
		}
	}

	@Override
	public BNodePosition<T> search(T element) {
		if (element != null) {
			return search(this.getRoot(), element);
		} else {
			return new BNodePosition<T>();
		}
	}

	private BNodePosition<T> search(BNode<T> node, T element) {
		if (element == null || node == null) {
			return new BNodePosition<T>();
		}
		
		int i = 0;

		while (i <= node.size() && element.compareTo(node.getElementAt(i)) > 0) {
			i++;
		}

		if (i <= node.size() && element.equals(node.getElementAt(i))) {
			return new BNodePosition<T>(node, i);
		}

		if (node.isLeaf()) {
			return new BNodePosition<T>();
		}

		return search(node.getChildren().get(i), element);
	}

	@Override
	public void insert(T element) {
		// TODO Implement your code here
		throw new UnsupportedOperationException("Not Implemented yet!");

	}

	private void split(BNode<T> node) {
		// TODO Implement your code here
		throw new UnsupportedOperationException("Not Implemented yet!");

	}

	private void promote(BNode<T> node) {
		// TODO Implement your code here
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	// NAO PRECISA IMPLEMENTAR OS METODOS ABAIXO
	@Override
	public BNode<T> maximum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public BNode<T> minimum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public void remove(T element) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

}
