package adt.heap;

import java.util.Arrays;
import java.util.Comparator;

import util.Util;

/**
 * O comportamento de qualquer heap é definido pelo heapify. Neste caso o
 * heapify dessa heap deve comparar os elementos e colocar o maior sempre no
 * topo. Essa comparação não é feita diretamente com os elementos armazenados,
 * mas sim usando um comparator. Dessa forma, dependendo do comparator, a heap
 * pode funcionar como uma max-heap ou min-heap.
 */
public class HeapImpl<T extends Comparable<T>> implements Heap<T> {

	protected T[] heap;
	protected int index = -1;
	/**
	 * O comparador é utilizado para fazer as comparações da heap. O ideal é
	 * mudar apenas o comparator e mandar reordenar a heap usando esse
	 * comparator. Assim os metodos da heap não precisam saber se vai funcionar
	 * como max-heap ou min-heap.
	 */
	protected Comparator<T> comparator;

	private static final int INITIAL_SIZE = 20;
	private static final int INCREASING_FACTOR = 10;

	/**
	 * Construtor da classe. Note que de inicio a heap funciona como uma
	 * min-heap.
	 */
	@SuppressWarnings("unchecked")
	public HeapImpl(Comparator<T> comparator) {
		this.heap = (T[]) (new Comparable[INITIAL_SIZE]);
		this.comparator = comparator;
	}

	// /////////////////// METODOS IMPLEMENTADOS
	private int parent(int i) {
		return (i - 1) / 2;
	}

	/**
	 * Deve retornar o indice que representa o filho a esquerda do elemento
	 * indexado pela posicao i no vetor
	 */
	private int left(int i) {
		return (i * 2 + 1);
	}

	/**
	 * Deve retornar o indice que representa o filho a direita do elemento
	 * indexado pela posicao i no vetor
	 */
	private int right(int i) {
		return (i * 2 + 1) + 1;
	}

	@Override
	public boolean isEmpty() {
		return (index == -1);
	}

	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] resp = (T[]) new Comparable[index + 1];
		for (int i = 0; i <= index; i++) {
			resp[i] = this.heap[i];
		}
		return resp;
	}

	// ///////////// METODOS A IMPLEMENTAR
	/**
	 * Valida o invariante de uma heap a partir de determinada posicao, que pode
	 * ser a raiz da heap ou de uma sub-heap. O heapify deve colocar os maiores
	 * (comparados usando o comparator) elementos na parte de cima da heap.
	 */
	private void heapify(int position) {
		if(isEmpty()){
			return;
		}
		
		int left = left(position);
		int right = right(position);
		int largest;
		
		if(left <= index && comparator.compare(heap[left], heap[position]) > 0){
			largest = left;
		}else{
			largest = position;
		}
		
		if(right <= index && comparator.compare(heap[right], heap[largest]) > 0){
			largest = right;
		}
		
		if(largest != position){
			Util.swap(heap, position, largest);
			heapify(largest);
		}
	}
	
	@Override
	public void insert(T element) {
		if(element == null){
			return;
		}
		
		// ESSE CODIGO E PARA A HEAP CRESCER SE FOR PRECISO. NAO MODIFIQUE
		if (index == heap.length - 1) {
			heap = Arrays.copyOf(heap, heap.length + INCREASING_FACTOR);
		}
		// /////////////////////////////////////////////////////////////////
		
	
		index++;
		int i = index;
		
		while(i > 0 && comparator.compare(heap[parent(i)], element) < 0){
			heap[i] = heap[parent(i)];
			i = parent(i);
		}
		
		heap[i] = element;
		
	}

	@Override
	public void buildHeap(T[] array) {
		if(array == null)
			return;
		
		heap = array;
		index = array.length-1;
		
		for(int i = (size()-1)/2; i >= 0; i--){
			heapify(i);
		}
	}

	@Override
	public T extractRootElement() {
		if(isEmpty()){
			return null;
		}
		
		T max = heap[0];
		
		heap[0] = heap[index];
		index--;
		
		heapify(0);
		
		return max;
	}

	@Override
	public T rootElement() {
		if(isEmpty()){
			return null;
		}
		return heap[0];
	}

	@Override
	public T[] heapsort(T[] array) {
		if(array == null){
			return array;
		}
			
		buildHeap(array);
		
		T[] aux = (T[]) new Comparable[size()];
		
		if(isMaxHeap()){
			for (int i = array.length-1; i >= 0; i--) {
				aux[i] = this.extractRootElement();
			}
		}else{
			for (int i = 0; i < array.length; i++) {
				aux[i] = this.extractRootElement();
			}	
		}
		
		return aux;
		

	}
	
	private boolean isMaxHeap(){
		Comparator<Integer> comparator = (Comparator<Integer>) this.comparator;
		
		return (comparator.compare(1, 2) < 0);
	}

	@Override
	public int size() {
		if(isEmpty()){
			return 0;
		}
		
		return index + 1;
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public T[] getHeap() {
		return heap;
	}

}
