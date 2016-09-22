package adt.linkedList;

public class DoubleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected DoubleLinkedListNode<T> last;

	public DoubleLinkedListImpl() {
		super();
		setHead(new DoubleLinkedListNode<T>());
		this.last = getHead();
	}

	@Override
	public void insert(T element) {
		if (element == null)
			return;

		if (!isEmpty()) {
			DoubleLinkedListNode<T> node = new DoubleLinkedListNode<>(element, new DoubleLinkedListNode<T>(),
					getLast());
			getLast().setNext(node);
			setLast(node);
		} else {
			DoubleLinkedListNode<T> node = new DoubleLinkedListNode<>(element, new DoubleLinkedListNode<T>(),
					new DoubleLinkedListNode<T>());

			this.setHead(node);
			this.setLast(node);
		}
	}

	@Override
	public void remove(T element) {
		if (element == null)
			return;

		if (!isEmpty()) {
			if (head.getData().equals(element)) {
				((DoubleLinkedListNode<T>) head.getNext()).setPrevious(new DoubleLinkedListNode<T>());

				if (this.size() == 1) {
					last = (DoubleLinkedListNode<T>) head.getNext();
				}

				head = head.getNext();
			} else {
				DoubleLinkedListNode<T> node = getHead();

				while (!node.isNIL()) {
					if (node.getData().equals(element)) {
						node.getPrevious().setNext(node.getNext());
						((DoubleLinkedListNode<T>) node.getNext()).setPrevious(node.getPrevious());
						if (node.getNext().isNIL()) {
							last = node.getPrevious();
						}
						break;
					}
					node = (DoubleLinkedListNode<T>) node.getNext();
				}
			}
		}
	}

	@Override
	public void insertFirst(T element) {
		if (element == null)
			return;

		if (isEmpty()) {
			DoubleLinkedListNode<T> node = new DoubleLinkedListNode<>(element, new DoubleLinkedListNode<T>(),
					new DoubleLinkedListNode<T>());

			this.setHead(node);
			this.setLast(node);
		} else {
			DoubleLinkedListNode<T> node = new DoubleLinkedListNode<>(element, getHead(),
					new DoubleLinkedListNode<T>());

			getHead().setPrevious(node);
			setHead(node);
		}
	}

	@Override
	public void removeFirst() {
		if (isEmpty())
			return;

		((DoubleLinkedListNode<T>) getHead().getNext()).setPrevious(new DoubleLinkedListNode<T>());

		if (this.size() == 1) {
			this.last = (DoubleLinkedListNode<T>) head.getNext();
		}

		this.head = head.getNext();
	}

	@Override
	public void removeLast() {
		if (isEmpty())
			return;

		DoubleLinkedListNode<T> lastPrev = getLast().getPrevious();
		lastPrev.setNext(new DoubleLinkedListNode<T>());

		if (this.size() == 1) {
			this.head = lastPrev;
		}

		this.last = lastPrev;
	}

	@Override
	@SuppressWarnings("unchecked")
	public DoubleLinkedListNode<T> getHead() {
		return (DoubleLinkedListNode) super.getHead();
	}

	@SuppressWarnings("unchecked")
	public void setHead(DoubleLinkedListNode<T> head) {
		this.head = (SingleLinkedListNode) head;
	}

	public DoubleLinkedListNode<T> getLast() {
		return last;
	}

	public void setLast(DoubleLinkedListNode<T> last) {
		this.last = last;
	}

}
