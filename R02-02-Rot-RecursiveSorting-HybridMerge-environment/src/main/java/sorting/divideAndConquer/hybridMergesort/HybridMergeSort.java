package sorting.divideAndConquer.hybridMergesort;

import java.util.Arrays;

import sorting.AbstractSorting;
import sorting.divideAndConquer.MergeSort;
import util.Util;

/**
 * A classe HybridMergeSort representa a implementação de uma variação do MergeSort 
 * que pode fazer uso do InsertionSort (um algoritmo híbrido) da seguinte forma: 
 * o MergeSort é aplicado a entradas maiores a um determinado limite. Caso a entrada 
 * tenha tamanho menor ou igual ao limite o algoritmo usa o InsertionSort. 
 * 
 * A implementação híbrida deve considerar os seguintes detalhes:
 * - Ter contadores das quantidades de MergeSorts e InsertionSorts aplicados, de 
 *   forma que essa informação possa ser capturada pelo teste.
 * - A cada chamado do método de sort(T[] array) esses contadores são resetados. E a cada
 *   chamada interna de um merge ou insertion, os contadores MERGESORT_APPLICATIONS e 
 *   INSERTIONSORT_APPLICATIONS são incrementados.
 *  - O InsertionSort utilizado no algoritmo híbrido deve ser in-place.
 */
public class HybridMergeSort<T extends Comparable<T>> extends AbstractSorting<T> {
    
	/**
	 * For inputs with size less or equal to this value, the insertionsort
	 * algorithm will be used instead of the mergesort.
	 */
	public static final int SIZE_LIMIT = 4;
	
	protected static int MERGESORT_APPLICATIONS = 0;
	protected static int INSERTIONSORT_APPLICATIONS = 0;
	
	public void sort(T[] array, int leftIndex, int rightIndex) {
		MERGESORT_APPLICATIONS = 0;
		INSERTIONSORT_APPLICATIONS = 0;
		
		if((rightIndex - leftIndex) <= 4){
			insertionSort(array, leftIndex, rightIndex);
			INSERTIONSORT_APPLICATIONS++;
		}else{
			mergeSort(array, leftIndex, rightIndex);
			MERGESORT_APPLICATIONS++;
		}
	}
	
	public void insertionSort(T[] array, int leftIndex, int rightIndex){
		T eleito;
		int j;
		
		for (int i = leftIndex+1; i <= rightIndex; i++) {
			
			eleito = array[i];
			j = i - 1;
			
			while (j >= 0 && array[j].compareTo(eleito) > 0) {
				Util.swap(array, j, j+1);
				j--;
			}
			array[j+1] = eleito;
		}
		
	}
	
	public void mergeSort(T[] array,int leftIndex, int rightIndex) {
		if(leftIndex == rightIndex || array.length == 0){
			return;
		}
		
		int middle = (leftIndex + rightIndex)/2;
		
		mergeSort(array, leftIndex, middle);
		mergeSort(array, middle + 1, rightIndex);
		merge(array, leftIndex, middle, rightIndex);
		
		
		}
	
	public void merge(T[] array, int left, int middle, int right){
		T[] tempArray = Arrays.copyOfRange(array, 0, array.length);
		
		for(int i = left; i <= right; i++){
			tempArray[i] = array[i];
		}
		
		int i = left;
		int j = middle + 1;
		int k = left;
		
		while(i <= middle && j <= right){
			if(tempArray[i].compareTo(tempArray[j]) <= 0){
				array[k] = tempArray[i];
				i++;
			}else{
				array[k] = tempArray[j];
				j++;
			}
			
			k++;
		}
		
		while(i <= middle){
			array[k] = tempArray[i];
			i++;
			k++;
		}
		
		while(j <= right){
			array[k] = tempArray[j];
			j++;
			k++;
		}
		
	
	}
}
