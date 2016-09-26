package adt.btree;

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
		// TODO Implement your code here
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public BNode<T>[] depthLeftOrder() {
		// TODO Implement your code here
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public int size() {
		// TODO Implement your code here
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public BNodePosition<T> search(T element) {
		if (element != null){
			return search(this.getRoot(), element);
		} else {
			return new BNodePosition<T>();
		}
	}
	
	private BNodePosition<T> search(BNode<T> node, T element){
		int i = 0;
		
		while (i <= node.size() && element.compareTo(node.getElementAt(i)) > 0){
			i++;
		}
		
		if (i <= node.size() && element.equals(node.getElementAt(i))){
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
