package sorting.variationsOfSelectionsort;


import java.util.Arrays;

import sorting.AbstractSorting;
import util.Util;

/**
 * This algorithm simulates a logical partitioning of the input array by considering 
 * different indexing, that is, the first sub-array is indexed by even elements and
 * the second sub-array is indexed by odd elements. Then, it applies a complete selectionsort
 * in the first sub-array considering neighbours (even). After that, 
 * it applies a complete selectionsort in the second sub-array considering
 * neighbours (odd).  After that, the algorithm performs a merge between elements indexed
 * by even and odd numbers.
 */
public class OddEvenSelectionSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array,int leftIndex, int rightIndex){
		if(array == null || array.length == 0 || leftIndex > rightIndex || leftIndex < 0 || rightIndex < 0 || rightIndex > array.length-1){
			return;
		}
		
		int menor = leftIndex;
		
		for(int i = leftIndex; i <= rightIndex; i++){
			menor = i;
			
			for(int j = i + 2; j <= rightIndex; j+= 2){
				if(array[j].compareTo(array[menor]) <= 0){
					menor = j;
				}
				
			}
			Util.swap(array, i, menor);
		
		}
		
		merge(array, leftIndex, rightIndex);
	}
	
	private void merge(T[] array, int leftIndex, int rightIndex) {
		int i = leftIndex;
		int j = leftIndex+1;
		int k = leftIndex;
		
		T[] aux = Arrays.copyOf(array, array.length);
		
		while(i <= rightIndex && j <= rightIndex){
			if(aux[i].compareTo(aux[j]) < 0){
				array[k] = aux[i];
				i += 2;
			}else{
				array[k] = aux[j];
				j += 2;
			}
			
			k++;
	
		}
		
		while(i <= rightIndex){
			array[k] = aux[i];
			k++;
			i += 2; 
		}
		
		while(j <= rightIndex){
			array[k] = aux[j];
			k++;
			j+= 2;
		}
	}
	

}
