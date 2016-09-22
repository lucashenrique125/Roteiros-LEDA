package sorting.divideAndConquer;

import java.util.Arrays;

import sorting.AbstractSorting;
import util.Util;

/**
 * Merge sort is based on the divide-and-conquer paradigm.  
 * The algorithm consists of recursively dividing the unsorted list in the middle,
 * sorting each sublist, and then merging them into one single sorted list.
 * Notice that if the list has length == 1, it is already sorted.
 */
public class MergeSort2<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array,int leftIndex, int rightIndex) {
		if(leftIndex >= rightIndex || array == null || array.length == 0 || leftIndex < 0 || rightIndex < 0 || rightIndex >= array.length){
			return;
		}
		
		int middle = (leftIndex + rightIndex)/2;
		
		sort(array, leftIndex, middle);
		sort(array, middle+1, rightIndex);
		merge(array, leftIndex, middle, rightIndex);
	}
	
	public void merge(T[] array, int leftIndex, int middle, int rightIndex){
		T[] aux = Arrays.copyOf(array, array.length);
		
		int i = leftIndex;
		int j = middle + 1;
		int k = leftIndex;
		
		while(i <= middle && j <= rightIndex){
			if(aux[i].compareTo(aux[j]) < 0){
				array[k] = aux[i];
				i++;
			}else{
				array[k] = aux[j];
				j++;
			}
			k++;
		}
		
		while(i <= middle){
			array[k] = aux[i];
			i++;
			k++;
		}
		
		while(j <= rightIndex){
			array[k] = aux[j];
			j++;
			k++;
		}
		
	}
}
