package sorting.variationsOfBubblesort;

import sorting.AbstractSorting;

/**
 * The combsort algoritm. 
 */
public class CombSort<T extends Comparable<T>> extends AbstractSorting<T> {
	@Override
	public void sort(T[] array,int leftIndex, int rightIndex) {
		if(array == null || array.length == 0 || leftIndex > rightIndex || leftIndex < 0 || rightIndex < 0 || rightIndex > array.length-1){
			return;
		}
		
		int gap = rightIndex - leftIndex + 1;
	    boolean swapped = true;
	    while (gap > 1 || swapped) {
	        if (gap > 1)
	            gap = (int) (gap / 1.247330950103979);

	        int i = leftIndex;
	        swapped = false;
	        while (i + gap <= rightIndex) {
	            if (array[i].compareTo(array[i + gap]) > 0) {
	                T t = array[i];
	                array[i] = array[i + gap];
	                array[i + gap] = t;
	                swapped = true;
	            }
	            i++;
	        }
	    }
	}
}
