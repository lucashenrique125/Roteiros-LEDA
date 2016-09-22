package sorting.variationsOfBubblesort;

import sorting.AbstractSorting;
import util.Util;

/**
 * The implementation of the algorithm must be in-place! 
 */
public class GnomeSort<T extends Comparable<T>> extends AbstractSorting<T>{
	public void sort(T[] array,int leftIndex, int rightIndex){
		if(array == null || array.length == 0 || leftIndex > rightIndex || leftIndex < 0 || rightIndex < 0 || rightIndex > array.length-1){
			return;
		}
		
		int pivot = leftIndex;
		T aux;
		while(pivot<rightIndex){
			if(array[pivot].compareTo(array[pivot+1])>0){
			      Util.swap(array, pivot, pivot+1);
			      if(pivot>leftIndex){
			    	  pivot-=2;
			      }
			}
			pivot++;
		}
	}
}
