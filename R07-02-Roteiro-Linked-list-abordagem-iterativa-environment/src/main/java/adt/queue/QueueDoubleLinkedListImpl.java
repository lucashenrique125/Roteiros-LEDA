package adt.queue;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.DoubleLinkedListImpl;

public class QueueDoubleLinkedListImpl<T> implements Queue<T> {

	protected DoubleLinkedList<T> list;
	protected int size;

	public QueueDoubleLinkedListImpl(int size) {
		this.size = size;
		this.list = new DoubleLinkedListImpl<T>();
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (isFull())
			throw new QueueOverflowException();
		if (element == null)
			return;

		list.insert(element);
		size--;
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if (list.isEmpty())
			throw new QueueUnderflowException();

		@SuppressWarnings("unchecked")
		T element = (T) ((DoubleLinkedListImpl) list).getHead().getData();
		list.removeFirst();

		size++;

		return element;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T head() {
		return (T) ((DoubleLinkedListImpl) list).getHead().getData();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean isFull() {
		return size == 0;
	}

}