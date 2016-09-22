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
public class MergeSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array,int leftIndex, int rightIndex) {
		if(leftIndex == rightIndex || array.length == 0){
			return;
		}
		
		int middle = (leftIndex + rightIndex)/2;
		
		sort(array, leftIndex, middle);
		sort(array, middle + 1, rightIndex);
		merge(array, leftIndex, middle, rightIndex);
		}
	
	public void merge(T[] array, int left, int middle, int right){
		T[] tempArray = Arrays.copyOfRange(array, 0, array.length);
		
	
		
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
