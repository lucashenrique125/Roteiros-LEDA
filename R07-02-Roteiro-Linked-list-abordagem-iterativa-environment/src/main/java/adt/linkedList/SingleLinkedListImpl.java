package adt.linkedList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;

	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
		return head.isNIL();
	}

	@Override
	public int size() {
		SingleLinkedListNode<T> node = head;

		int size = 0;

		while (!node.isNIL()) {
			node = node.getNext();
			size++;
		}

		return size;
	}

	@Override
	public T search(T element) {
		SingleLinkedListNode<T> node = head;
		T found = null;

		while (!node.isNIL()) {
			if (node.getData().equals(element)) {
				found = node.getData();
				break;
			}
			node = node.getNext();
		}

		return found;
	}

	@Override
	public void insert(T element) {
		if (element == null)
			return;

		SingleLinkedListNode<T> node = head;

		while (!node.isNIL()) {
			node = node.getNext();
		}

		node.setData(element);
		node.setNext(new SingleLinkedListNode<T>());
	}

	@Override
	public void remove(T element) {
		if (element == null)
			return;

		if (!isEmpty()) {
			if (head.getData().equals(element)) {
				head = head.getNext();
			} else {
				SingleLinkedListNode<T> prev = new SingleLinkedListNode<>();
				SingleLinkedListNode<T> node = head;

				while (!node.isNIL()) {
					if (node.getData().equals(element)) {
						prev.setNext(node.getNext());
						node.setNext(new SingleLinkedListNode<T>());
						break;
					}
					prev = node;
					node = node.getNext();
				}
			}
		}
	}

	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Object[this.size()];

		SingleLinkedListNode<T> node = head;
		int i = 0;

		while (!node.isNIL()) {
			array[i] = node.getData();

			node = node.getNext();
			i++;
		}

		return array;
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}
}